package builderb0y.bigglobe.scripting;

import java.util.Set;
import java.util.random.RandomGenerator;

import builderb0y.autocodec.annotations.Wrapper;
import builderb0y.bigglobe.columns.ColumnValue;
import builderb0y.bigglobe.columns.WorldColumn;
import builderb0y.scripting.environments.MathScriptEnvironment;
import builderb0y.scripting.parsing.Script;
import builderb0y.scripting.parsing.ScriptInputs.SerializableScriptInputs;
import builderb0y.scripting.parsing.ScriptParsingException;
import builderb0y.scripting.parsing.TemplateScriptParser;
import builderb0y.scripting.util.TypeInfos;

import static builderb0y.scripting.bytecode.InsnTrees.*;

public interface ColumnYRandomToDoubleScript extends Script {

	public abstract double evaluate(WorldColumn column, double y, RandomGenerator random);

	@Wrapper
	public static class Holder extends ScriptHolder<ColumnYRandomToDoubleScript> implements ColumnYRandomToDoubleScript {

		public final SerializableScriptInputs inputs;
		public final transient Set<ColumnValue<?>> usedValues;

		public Holder(ColumnYRandomToDoubleScript script, SerializableScriptInputs inputs, Set<ColumnValue<?>> usedValues) {
			super(script);
			this.inputs = inputs;
			this.usedValues = usedValues;
		}

		public static Holder create(SerializableScriptInputs inputs) throws ScriptParsingException {
			ColumnScriptEnvironmentBuilder columnYScriptEnvironment = ColumnScriptEnvironmentBuilder.createFixedXYZ(
				ColumnValue.REGISTRY,
				load("column", 1, type(WorldColumn.class)),
				load("y", 2, TypeInfos.DOUBLE)
			)
			.addXZ("x", "z")
			.addY("y")
			.addSeed("worldSeed");
			ColumnYRandomToDoubleScript actualScript = (
				new TemplateScriptParser<>(ColumnYRandomToDoubleScript.class, inputs.buildScriptInputs())
				.addEnvironment(MathScriptEnvironment.INSTANCE)
				.addEnvironment(columnYScriptEnvironment.build())
				.addEnvironment(RandomScriptEnvironment.create(
					load("random", 4, type(RandomGenerator.class))
				))
				.addEnvironment(StatelessRandomScriptEnvironment.INSTANCE)
				.parse()
			);
			return new Holder(actualScript, inputs, columnYScriptEnvironment.usedValues);
		}

		@Override
		public double evaluate(WorldColumn column, double y, RandomGenerator random) {
			try {
				return this.script.evaluate(column, y, random);
			}
			catch (Throwable throwable) {
				this.onError(throwable);
				return Double.NaN;
			}
		}
	}
}