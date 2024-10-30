package builderb0y.bigglobe.scripting.wrappers.tags;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.function.Consumer;

import builderb0y.bigglobe.scripting.ScriptLogger;
import builderb0y.scripting.bytecode.MethodInfo;
import builderb0y.scripting.bytecode.TypeInfo;
import builderb0y.scripting.bytecode.tree.ConstantValue;
import builderb0y.scripting.bytecode.tree.InsnTree;
import builderb0y.scripting.bytecode.tree.InsnTree.CastMode;
import builderb0y.scripting.environments.MutableScriptEnvironment;
import builderb0y.scripting.environments.MutableScriptEnvironment.CastHandler;
import builderb0y.scripting.environments.MutableScriptEnvironment.KeywordHandler;
import builderb0y.scripting.parsing.ExpressionParser;
import builderb0y.scripting.parsing.ScriptParsingException;
import builderb0y.scripting.parsing.special.CommaSeparatedExpressions;
import builderb0y.scripting.util.TypeInfos;

import static builderb0y.scripting.bytecode.InsnTrees.*;

public class TagParser implements Consumer<MutableScriptEnvironment> {

	public final String typeName;
	public final TypeInfo tagType;
	public final MethodInfo bootstrapConstant, nonConstant;

	public TagParser(String typeName, Class<?> tagClass) {
		this.typeName = typeName;
		this.tagType = type(tagClass);
		this.bootstrapConstant = MethodInfo.findMethod(tagClass, "of", tagClass, MethodHandles.Lookup.class, String.class, Class.class, String[].class);
		this.nonConstant = MethodInfo.findMethod(tagClass, "of", tagClass, String[].class);
	}

	@Override
	public void accept(MutableScriptEnvironment environment) {
		environment
		.addCast(type(String.class), this.tagType, true, this.makeCaster())
		.addKeyword(this.typeName, this.makeKeyword());
	}

	public CastHandler.Named makeCaster() {
		return new CastHandler.Named(
			"String -> " + this.typeName,
			(ExpressionParser parser, InsnTree value, TypeInfo to, boolean implicit) -> {
				if (value.getConstantValue().isConstant()) {
					return ldc(
						this.bootstrapConstant,
						value.getConstantValue()
					);
				}
				else {
					if (implicit) {
						ScriptLogger.LOGGER.warn("Non-constant tag; this will be worse on performance. Use an explicit cast to suppress this warning. " + ScriptParsingException.appendContext(parser.input));
					}
					return invokeStatic(
						this.nonConstant,
						newArrayWithContents(parser, type(String[].class), value)
					);
				}
			}
		);
	}

	public KeywordHandler.Named makeKeyword() {
		return new KeywordHandler.Named(
			this.typeName + "(element1 [, element2, ...])",
			(ExpressionParser parser, String name) -> {
				if (parser.input.peekAfterWhitespace() != '(') return null;
				CommaSeparatedExpressions expressions = CommaSeparatedExpressions.parse(parser);
				return switch (expressions.arguments().length) {
					case 0 -> throw new ScriptParsingException("At least one element is required", parser.input);
					case 1 -> expressions.maybeWrap(expressions.arguments()[0].cast(parser, this.tagType, CastMode.EXPLICIT_THROW));
					default -> {
						InsnTree[] strings = Arrays.stream(expressions.arguments()).map((InsnTree tree) -> tree.cast(parser, TypeInfos.STRING, CastMode.IMPLICIT_THROW)).toArray(InsnTree[]::new);
						if (Arrays.stream(strings).map(InsnTree::getConstantValue).allMatch(ConstantValue::isConstant)) {
							yield ldc(
								this.bootstrapConstant,
								Arrays.stream(strings).map(InsnTree::getConstantValue).toArray(ConstantValue[]::new)
							);
						}
						else {
							yield invokeStatic(
								this.nonConstant,
								newArrayWithContents(parser, type(String[].class), strings)
							);
						}
					}
				};
			}
		);
	}
}