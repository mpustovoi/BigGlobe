package builderb0y.bigglobe.versions;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;

public class TagKeyVersions {

	@SuppressWarnings("unchecked")
	public static <T> RegistryKey<Registry<T>> registry(TagKey<T> key) {
		#if MC_VERSION >= MC_1_21_2
			return (RegistryKey<Registry<T>>)(key.registryRef());
		#else
			return (RegistryKey<Registry<T>>)(key.registry());
		#endif
	}
}