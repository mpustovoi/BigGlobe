package builderb0y.bigglobe.entities;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public abstract class BigGlobeEntityRenderer<E extends Entity, S extends BigGlobeEntityRenderer.State> extends EntityRenderer<E #if MC_VERSION >= MC_1_21_2 , S #endif> {

	public BigGlobeEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
	}

	public abstract S createState();

	public abstract void updateState(E entity, S state, float partialTicks);

	public abstract void doRender(S state, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int light);

	#if MC_VERSION >= MC_1_21_2

		@Override
		public void render(S state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
			super.render(state, matrices, vertexConsumers, light);
			this.doRender(state, matrices, vertexConsumers, light);
		}

		@Override
		public void updateRenderState(E entity, S state, float tickDelta) {
			super.updateRenderState(entity, state, tickDelta);
			this.updateState(entity, state, tickDelta);
		}

		@Override
		public S createRenderState() {
			return this.createState();
		}

		public static class State extends net.minecraft.client.render.entity.state.EntityRenderState {

		}

	#else

		public final S state = this.createState();

		@Override
		public void render(E entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
			this.state.x = entity.getX();
			this.state.y = entity.getY();
			this.state.z = entity.getZ();
			this.updateState(entity, this.state, tickDelta);
			super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
			this.doRender(this.state, matrices, vertexConsumers, light);
		}

		public static class State {

			public double x, y, z;
		}

	#endif
}