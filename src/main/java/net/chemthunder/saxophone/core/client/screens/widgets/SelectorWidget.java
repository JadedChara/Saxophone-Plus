package net.chemthunder.saxophone.impl.client.screens.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import net.chemthunder.saxophone.impl.Saxophone;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

public class SelectorWidget extends ButtonWidget {
    private static final ButtonTextures FOLLYFIED_TEXTURES = new ButtonTextures(
            Saxophone.id("widget/follyfied_button"),
            Saxophone.id("widget/follyfied_button_disabled"),
            Saxophone.id("widget/follyfied_button_highlighted")
    );

    protected SelectorWidget(int x, int y, int width, int height, Text message, PressAction onPress, NarrationSupplier narrationSupplier) {
        super(x, y, width, height, message, onPress, narrationSupplier);
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        context.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();

        context.drawGuiTexture(FOLLYFIED_TEXTURES.get(this.active, this.isSelected()), this.getX(), this.getY(), this.getWidth(),
                this.getHeight());

        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = this.active ? 16777215 : 10526880;
        this.drawMessage(context, minecraftClient.textRenderer, i | MathHelper.ceil(this.alpha * 255.0F) << 24);
    }

    public static Factory factory(Text message, PressAction onPress) {
        return new Factory(message, onPress);
    }
    @Environment(EnvType.CLIENT)
    public static class Factory {
        private final Text message;
        private final PressAction onPress;
        @Nullable
        private Tooltip tooltip;
        private int x;
        private int y;
        private int width = 150;
        private int height = 20;
        private NarrationSupplier narrationSupplier;

        public Factory(Text message, PressAction onPress) {
            this.narrationSupplier = ButtonWidget.DEFAULT_NARRATION_SUPPLIER;
            this.message = message;
            this.onPress = onPress;
        }

        public Factory position(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public Factory width(int width) {
            this.width = width;
            return this;
        }

        public Factory size(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Factory dimensions(int x, int y, int width, int height) {
            return this.position(x, y).size(width, height);
        }

        public Factory tooltip(@Nullable Tooltip tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        public Factory narrationSupplier(NarrationSupplier narrationSupplier) {
            this.narrationSupplier = narrationSupplier;
            return this;
        }

        public SelectorWidget build() {
            SelectorWidget bw = new SelectorWidget(this.x, this.y, this.width, this.height, this.message, this.onPress,
                    this.narrationSupplier);
            bw.setTooltip(this.tooltip);
            return bw;
        }
    }
}
