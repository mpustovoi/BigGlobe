package builderb0y.bigglobe.columns.scripted.types;

import builderb0y.autocodec.annotations.RecordLike;
import builderb0y.scripting.bytecode.TypeInfo;
import builderb0y.scripting.util.TypeInfos;

@RecordLike({})
public class ShortColumnValueType extends AbstractColumnValueType {

	@Override
	public TypeInfo getTypeInfo() {
		return TypeInfos.SHORT;
	}

	@Override
	public String toString() {
		return "short";
	}
}