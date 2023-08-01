package builderb0y.scripting.bytecode.tree.flow;

import builderb0y.scripting.bytecode.MethodCompileContext;
import builderb0y.scripting.bytecode.ScopeContext.Scope;
import builderb0y.scripting.bytecode.TypeInfo;
import builderb0y.scripting.bytecode.tree.InsnTree;
import builderb0y.scripting.bytecode.tree.conditions.ConditionTree;
import builderb0y.scripting.bytecode.tree.conditions.ConstantConditionTree;
import builderb0y.scripting.parsing.ExpressionParser;
import builderb0y.scripting.parsing.ScriptParsingException;
import builderb0y.scripting.util.TypeInfos;
import builderb0y.scripting.util.TypeMerger;

public class IfElseInsnTree implements InsnTree {

	public final ConditionTree condition;
	/**
	runtime bodies are guaranteed to have the same {@link InsnTree#getTypeInfo()}.
	compile bodies are not. the runtime bodies will be used for emitting bytecode,
	and the compile bodies will be used for casting {@link #cast(ExpressionParser, TypeInfo, CastMode)}.
	*/
	public final InsnTree compileTrueBody, compileFalseBody, runtimeTrueBody, runtimeFalseBody;
	public final TypeInfo type;

	public IfElseInsnTree(
		ConditionTree condition,
		InsnTree compileTrueBody,
		InsnTree compileFalseBody,
		InsnTree runtimeTrueBody,
		InsnTree runtimeFalseBody,
		TypeInfo type
	) {
		this.condition        = condition;
		this.compileTrueBody  = compileTrueBody;
		this.compileFalseBody = compileFalseBody;
		this.runtimeTrueBody  = runtimeTrueBody;
		this.runtimeFalseBody = runtimeFalseBody;
		this.type             = type;
	}

	public static InsnTree create(ExpressionParser parser, ConditionTree condition, InsnTree trueBody, InsnTree falseBody) throws ScriptParsingException {
		Operands operands = Operands.of(parser, trueBody, falseBody);
		return new IfElseInsnTree(condition, operands.compileTrue, operands.compileFalse, operands.runtimeTrue, operands.runtimeFalse, operands.type);
	}

	public static record Operands(
		InsnTree compileTrue,
		InsnTree compileFalse,
		InsnTree runtimeTrue,
		InsnTree runtimeFalse,
		TypeInfo type
	) {

		public static Operands of(ExpressionParser parser, InsnTree trueBody, InsnTree falseBody) {
			TypeInfo type;
			if (trueBody.jumpsUnconditionally()) {
				if (falseBody.jumpsUnconditionally()) {
					type = TypeInfos.VOID;
				}
				else {
					type = falseBody.getTypeInfo();
				}
			}
			else {
				if (falseBody.jumpsUnconditionally()) {
					type = trueBody.getTypeInfo();
				}
				else {
					type = TypeMerger.computeMostSpecificType(trueBody.getTypeInfo(), falseBody.getTypeInfo());
				}
			}
			InsnTree runtimeTrueBody = trueBody.cast(parser, type, CastMode.IMPLICIT_THROW);
			InsnTree runtimeFalseBody = falseBody.cast(parser, type, CastMode.IMPLICIT_THROW);
			return new Operands(trueBody, falseBody, runtimeTrueBody, runtimeFalseBody, type);
		}
	}

	@Override
	public void emitBytecode(MethodCompileContext method) {
		Scope scope = method.scopes.pushScope();
		this.condition.emitBytecode(method, null, scope.end.getLabel());
		this.runtimeTrueBody.emitBytecode(method);
		scope.cycle();
		method.node.visitJumpInsn(GOTO, scope.end.getLabel());
		method.node.instructions.add(scope.start);
		this.runtimeFalseBody.emitBytecode(method);
		method.scopes.popLoop();
	}

	@Override
	public TypeInfo getTypeInfo() {
		return this.type;
	}

	@Override
	public boolean jumpsUnconditionally() {
		if (this.condition instanceof ConstantConditionTree constant) {
			return (constant.value ? this.compileTrueBody : this.compileFalseBody).jumpsUnconditionally();
		}
		else {
			return this.compileTrueBody.jumpsUnconditionally() && this.compileFalseBody.jumpsUnconditionally();
		}
	}

	@Override
	public boolean canBeStatement() {
		return this.compileTrueBody.canBeStatement() && this.compileFalseBody.canBeStatement();
	}

	@Override
	public InsnTree asStatement() {
		return new IfElseInsnTree(this.condition, this.compileTrueBody, this.compileFalseBody, this.runtimeTrueBody.asStatement(), this.runtimeFalseBody.asStatement(), TypeInfos.VOID);
	}

	@Override
	public InsnTree doCast(ExpressionParser parser, TypeInfo type, CastMode mode) {
		InsnTree trueBody = this.compileTrueBody.cast(parser, type, mode);
		if (trueBody == null) return null;
		InsnTree falseBody = this.compileFalseBody.cast(parser, type, mode);
		if (falseBody == null) return null;
		return new IfElseInsnTree(this.condition, trueBody, falseBody, trueBody, falseBody, type);
	}
}