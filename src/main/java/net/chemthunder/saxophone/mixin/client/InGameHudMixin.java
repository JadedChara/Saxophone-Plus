package net.chemthunder.saxophone.mixin.client;

import net.chemthunder.saxophone.core.Saxophone;
import net.chemthunder.saxophone.core.cca.entity.ScreenflashComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Chemthunder
 */
@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);

    @Unique private static final Identifier SCREENFLASH = Saxophone.id("textures/gui/screenflash.png");

    @Inject(method = "renderMiscOverlays", at = @At("TAIL"))
    private void extraOverlays(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (MinecraftClient.getInstance().getCameraEntity() instanceof PlayerEntity player) {
            int ticks = ScreenflashComponent.KEY.get(player).getFlashTicks();
            if (ticks > 0) {
                float opacity = ticks > 50 ? 1f : ticks / 50.0f;
                this.renderOverlay(context, SCREENFLASH, opacity);
            }
        }
    }
}
