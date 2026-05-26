package net.chemthunder.saxophone.core.client.render.entity;

import com.nitron.nitrogen.render.RenderUtils;
import net.chemthunder.saxophone.core.Saxophone;
import net.chemthunder.saxophone.core.entity.TestBeamEntity;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

/**
 * @author Chemthunder
 */
public class TestBeamEntityRenderer extends EntityRenderer<TestBeamEntity> {
    private static final Identifier TEXTURE = Saxophone.id("textures/render/chain_outline.png");

    public TestBeamEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    public void render(TestBeamEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        matrices.translate(-entity.getPos().getX(), -entity.getPos().getY(), -entity.getPos().getZ());

        RenderUtils.renderSkyBeam(
                matrices,
                vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(TEXTURE)),
                entity.getBlockPos(),
                entity.beamRadius,
                256,
                0
        );

        matrices.pop();

        matrices.push();

        matrices.translate(-entity.getPos().getX(), -entity.getPos().getY(), -entity.getPos().getZ());

        RenderUtils.renderTexturedSphere(
                matrices,
                vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(TEXTURE)),
                new BlockPos(entity.getBlockPos().getX(), entity.getBlockPos().getY() /* - 10 */, entity.getBlockPos().getZ()),
                15,
                90,
                0
        );

        matrices.pop();
    }

    public Identifier getTexture(TestBeamEntity entity) {
        return null;
    }

    public boolean shouldRender(TestBeamEntity entity, Frustum frustum, double x, double y, double z) {
        return true;
    }
}
