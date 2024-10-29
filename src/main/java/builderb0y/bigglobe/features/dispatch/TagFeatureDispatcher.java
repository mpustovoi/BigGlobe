package builderb0y.bigglobe.features.dispatch;

import java.util.stream.Stream;

import net.minecraft.registry.entry.RegistryEntry;

import builderb0y.bigglobe.columns.scripted.dependencies.DependencyView;
import builderb0y.bigglobe.noise.Permuter;
import builderb0y.bigglobe.scripting.wrappers.WorldWrapper;
import builderb0y.bigglobe.util.DelayedEntryList;

public class TagFeatureDispatcher implements FeatureDispatcher {

	public final DelayedEntryList<FeatureDispatcher> tag;

	public TagFeatureDispatcher(DelayedEntryList<FeatureDispatcher> tag) {
		this.tag = tag;
	}

	@Override
	public Stream<? extends RegistryEntry<? extends DependencyView>> streamDirectDependencies() {
		return this.tag.entryStream();
	}

	@Override
	public void generate(WorldWrapper world, Permuter random, long chunkSeed, RegistryEntry<FeatureDispatcher> selfEntry) {
		for (RegistryEntry<FeatureDispatcher> entry : this.tag.entryList()) {
			entry.value().generate(world, random, chunkSeed, entry);
		}
	}
}