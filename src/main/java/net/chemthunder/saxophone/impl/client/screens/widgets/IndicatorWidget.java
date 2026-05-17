package net.chemthunder.saxophone.impl.client.screens.widgets;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.chemthunder.saxophone.impl.Saxophone;
import net.chemthunder.saxophone.impl.cca.deity.AvariceComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.AbstractTextWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.IconWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.component.Component;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class IndicatorWidget extends ClickableWidget {

    public Identifier STATUS = Saxophone.id("textures/gui/sprites/widgets/select/inactive.png");

    public IndicatorWidget(int x, int y, boolean active) {
        super(x,y,16,16, Text.of(""));
        this.setWidth(16);
        this.setHeight(16);
        if(active){
            STATUS = Saxophone.id("textures/gui/sprites/widget/select/active.png");
        }else{
            this.STATUS = Saxophone.id("textures/gui/sprites/widget/select/inactive.png");
        }
    }

    public void updateIcon(boolean active){
        if (active) {
            STATUS = Saxophone.id("textures/gui/sprites/widget/select/active.png");
        }else{
            STATUS = Saxophone.id("textures/gui/sprites/widget/select/inactive.png");
        }
    }
    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        RenderSystem.setShaderTexture(0,STATUS);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,this.alpha);
        context.drawTexture(STATUS,getX(),getY(),0,0,getWidth(),getHeight(),getWidth(),getHeight());
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
}
