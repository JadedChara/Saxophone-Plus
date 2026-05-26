package net.chemthunder.saxophone.core.client.render.entity;

import net.chemthunder.saxophone.core.entity.ForsakenCharterEntity;
import net.chemthunder.saxophone.core.index.SaxoItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

/**
 * @author Chemthunder
 */
public class ForsakenCharterEntityRenderer extends EntityRenderer<ForsakenCharterEntity> {
    public ForsakenCharterEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    public void render(ForsakenCharterEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        MinecraftClient client = MinecraftClient.getInstance();
        ItemRenderer renderer = client.getItemRenderer();
        int rotationSpeed = 2;

        matrices.push();
        if (renderer != null) {
            ItemStack stack = new ItemStack(SaxoItems.FORSAKEN_CHARTER);

        //    matrices.translate(1.2f, 1.2f, 1.2f);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((entity.age + tickDelta) * -rotationSpeed));
            renderer.renderItem(stack,
                    ModelTransformationMode.GROUND,
                    light,
                    OverlayTexture.DEFAULT_UV,
                    matrices,
                    vertexConsumers,
                    entity.getWorld(),
                    entity.getId()
            );
        }
        matrices.pop();

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    public Identifier getTexture(ForsakenCharterEntity entity) {
        return null;
    }
}