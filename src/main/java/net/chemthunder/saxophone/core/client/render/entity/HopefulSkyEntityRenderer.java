package net.chemthunder.saxophone.core.client.render.entity;

import com.nitron.nitrogen.render.RenderUtils;
import net.chemthunder.saxophone.core.Saxophone;
import net.chemthunder.saxophone.core.entity.HopefulSkyEntity;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class HopefulSkyEntityRenderer extends EntityRenderer<HopefulSkyEntity> {
    public HopefulSkyEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    public void render(HopefulSkyEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        matrices.translate(-entity.getX(), -entity.getY(), -entity.getZ());
        RenderUtils.renderTexturedSphere(
                matrices,
                vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(Saxophone.id("textures/render/orb.png"))),
                entity.getBlockPos(),
                entity.getFinalized() ? 43 : entity.getOrbRadius() - 1,
                entity.getOrbQuality(),
                0
        );

        matrices.pop();

        matrices.push();

        matrices.translate(-entity.getX(), -entity.getY(), -entity.getZ());
        RenderUtils.renderTexturedSphere(
                matrices,
                vertexConsumers.getBuffer(RenderLayer.getEntitySolid(Saxophone.id("textures/render/chain_outline.png"))),
                entity.getBlockPos(),
                entity.getFinalized() ? 44 : entity.getOrbRadius(),
                entity.getOrbQuality(),
                0
        );

        matrices.pop();
    }

    public boolean shouldRender(HopefulSkyEntity entity, Frustum frustum, double x, double y, double z) {
        return true;
    }

    public Identifier getTexture(HopefulSkyEntity entity) {
        return null;
    }
}
