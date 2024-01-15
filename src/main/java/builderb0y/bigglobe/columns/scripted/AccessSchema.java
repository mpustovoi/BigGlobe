package builderb0y.bigglobe.columns.scripted;

import builderb0y.autocodec.annotations.MemberUsage;
import builderb0y.autocodec.annotations.UseCoder;
import builderb0y.bigglobe.BigGlobeMod;
import builderb0y.bigglobe.codecs.CoderRegistry;
import builderb0y.bigglobe.codecs.CoderRegistryTyped;
import builderb0y.bigglobe.columns.scripted.AccessSchemas.*;
import builderb0y.bigglobe.columns.scripted.DataCompileContext.ColumnCompileContext;
import builderb0y.bigglobe.columns.scripted.entries.ColumnEntry.TypeContext;
import builderb0y.scripting.bytecode.MethodInfo;
import builderb0y.scripting.bytecode.TypeInfo;
import builderb0y.scripting.util.TypeInfos;

@UseCoder(name = "REGISTRY", in = AccessSchema.class, usage = MemberUsage.FIELD_CONTAINS_HANDLER)
public interface AccessSchema extends CoderRegistryTyped<AccessSchema> {

	public static final CoderRegistry<AccessSchema> REGISTRY = new CoderRegistry<>(BigGlobeMod.modID("column_entry_access_schema"));
	public static final Object INITIALIZER = new Object() {{
		REGISTRY.registerAuto(BigGlobeMod.modID("int_2d"), Int2DAccessSchema.class);
		REGISTRY.registerAuto(BigGlobeMod.modID("long_2d"), Long2DAccessSchema.class);
		REGISTRY.registerAuto(BigGlobeMod.modID("float_2d"), Float2DAccessSchema.class);
		REGISTRY.registerAuto(BigGlobeMod.modID("double_2d"), Double2DAccessSchema.class);
		REGISTRY.registerAuto(BigGlobeMod.modID("boolean_2d"), Boolean2DAccessSchema.class);
		REGISTRY.registerAuto(BigGlobeMod.modID("int_3d"), Int3DAccessSchema.class);
		REGISTRY.registerAuto(BigGlobeMod.modID("long_3d"), Long3DAccessSchema.class);
		REGISTRY.registerAuto(BigGlobeMod.modID("float_3d"), Float3DAccessSchema.class);
		REGISTRY.registerAuto(BigGlobeMod.modID("double_3d"), Double3DAccessSchema.class);
		REGISTRY.registerAuto(BigGlobeMod.modID("boolean_3d"), Boolean3DAccessSchema.class);
	}};

	public abstract TypeContext createType(ColumnCompileContext context);

	public abstract boolean requiresYLevel();

	public default MethodInfo getterDescriptor(int flags, String name, DataCompileContext context) {
		return new MethodInfo(
			flags,
			context.selfType(),
			name,
			context.getSchemaType(this).exposedType(),
			this.requiresYLevel()
			? new TypeInfo[] { TypeInfos.INT }
			: TypeInfo.ARRAY_FACTORY.empty()
		);
	}

	public default MethodInfo setterDescriptor(int flags, String name, DataCompileContext context) {
		return new MethodInfo(
			flags,
			context.selfType(),
			name,
			TypeInfos.VOID,
			this.requiresYLevel()
			? new TypeInfo[] { TypeInfos.INT, context.getSchemaType(this).exposedType() }
			: new TypeInfo[] { context.getSchemaType(this).exposedType() }
		);
	}

	@Override
	public abstract boolean equals(Object other);

	@Override
	public abstract int hashCode();
}