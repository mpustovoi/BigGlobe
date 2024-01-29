package builderb0y.bigglobe.commands;

import java.util.Map;
import java.util.Set;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import org.jetbrains.annotations.Nullable;

import builderb0y.bigglobe.columns.ColumnValue;
import builderb0y.bigglobe.columns.WorldColumn;
import builderb0y.bigglobe.scripting.interfaces.ColumnPredicate;
import builderb0y.bigglobe.scripting.environments.ColumnScriptEnvironmentBuilder;
import builderb0y.scripting.environments.MathScriptEnvironment;
import builderb0y.scripting.parsing.ScriptParser;
import builderb0y.scripting.parsing.ScriptParsingException;

import static builderb0y.scripting.bytecode.InsnTrees.*;

public class LocateAreaLazyScript implements ColumnPredicate {

	public @Nullable ScriptParser<ColumnPredicate> parser;
	public Set<ColumnValue<?>> usedValues;
	public @Nullable ColumnPredicate script;

	public LocateAreaLazyScript(String script) throws ScriptParsingException {
		ColumnScriptEnvironmentBuilder builder = (
			ColumnScriptEnvironmentBuilder
			.createFixedXZVariableY(ColumnValue.REGISTRY, load("column", type(WorldColumn.class)), null)
			.trackUsedValues()
			.addXZ("x", "z")
		);
		this.parser = (
			new ScriptParser<>(ColumnPredicate.class, script, null)
			.addEnvironment(MathScriptEnvironment.INSTANCE)
			.addEnvironment(builder.build())
		);
		this.parser.toBytecode();
		this.usedValues = builder.usedValues;
	}

	public ColumnPredicate getScript() {
		if (this.script == null) try {
			this.script = this.parser.toScript();
			this.parser = null; //free for GC.
		}
		catch (ScriptParsingException exception) {
			throw new RuntimeException(exception);
		}
		return this.script;
	}

	@Override
	public boolean test(WorldColumn column) {
		return this.getScript().test(column);
	}

	@Override
	public String getSource() {
		return this.getScript().getSource();
	}

	@Override
	public @Nullable String getDebugName() {
		return this.getScript().getDebugName();
	}

	public static class Argument implements ArgumentType<LocateAreaLazyScript> {

		@Override
		public LocateAreaLazyScript parse(StringReader reader) throws CommandSyntaxException {
			try {
				String script = reader.getRemaining();
				reader.setCursor(reader.getTotalLength());
				return new LocateAreaLazyScript(script);
			}
			catch (ScriptParsingException exception) {
				throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherParseException().create(exception.getMessage());
			}
		}
	}
}