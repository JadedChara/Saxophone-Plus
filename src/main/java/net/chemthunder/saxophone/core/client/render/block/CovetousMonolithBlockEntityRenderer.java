package net.chemthunder.saxophone.core.client.render.block;

import com.nitron.nitrogen.render.RenderUtils;
import net.chemthunder.saxophone.core.Saxophone;
import net.chemthunder.saxophone.core.block.entity.CovetousMonolithBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class CovetousMonolithBlockEntityRenderer implements BlockEntityRenderer<CovetousMonolithBlockEntity> {
    public CovetousMonolithBlockEntityRenderer(BlockEntityRendererFactory.Context context) {}

    public void render(CovetousMonolithBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        matrices.translate(-entity.getPos().getX(), -entity.getPos().getY(), -entity.getPos().getZ());
        RenderUtils.renderTexturedCube(
                matrices,
                vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(
                        Saxophone.id("textures/render/monolith_border.png"),
                        false
                )),
                entity.getPos(),
                entity.radius,
                Vec2f.ZERO,
                9
        );

        matrices.pop();
    }

    public boolean isInRenderDistance(CovetousMonolithBlockEntity blockEntity, Vec3d pos) {
        return true;
    }

    public boolean rendersOutsideBoundingBox(CovetousMonolithBlockEntity blockEntity) {
        return true;
    }
}