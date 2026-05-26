package net.chemthunder.saxophone.core.client.screens;

import net.chemthunder.saxophone.core.cca.deity.AvariceComponent;
import net.chemthunder.saxophone.core.client.screens.widgets.IndicatorWidget;
import net.chemthunder.saxophone.core.client.screens.widgets.SelectorWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.text.Text;

public class AvariceScreen extends Screen {
    public AvariceScreen() {
        super(Text.translatable("screen.saxophone.avarice.controls"));
    }
    public AvariceComponent ac;

    public SelectorWidget toggleAvarice;
    public SelectorWidget toggleInvincibility;
    public SelectorWidget toggleInvisibility;
    public SelectorWidget toggleWavering;
    public SelectorWidget toggleTransparency;

    public IndicatorWidget indicatorAvarice;
    public IndicatorWidget indicatorInvincibility;
    public IndicatorWidget indicatorInvisibility;
    public IndicatorWidget indicatorWavering;
    public IndicatorWidget indicatorTransparency;

    @Override
    protected void init() {
        ac = AvariceComponent.KEY.get(MinecraftClient.getInstance().player);
        //TOGGLES
        toggleAvarice = SelectorWidget.factory(Text.translatable("button.saxophone.toggle.avarice"),
                        button -> {
            ac.c2sAvarice(!ac.isAvarice());
            indicatorAvarice.updateIcon(ac.isAvarice());
            toggleInvincibility.active = ac.isAvarice();
            toggleInvisibility.active = ac.isAvarice();
            toggleWavering.active = ac.isAvarice();
            toggleTransparency.active = ac.isAvarice();
            indicatorInvincibility.updateIcon(ac.isInvincible());
            indicatorInvisibility.updateIcon(ac.isInvisible());
            indicatorWavering.updateIcon(ac.isWavering());
            indicatorTransparency.updateIcon(ac.isTransparent());
        })
                .dimensions(width / 2 - 70, 40, 200, 20)
                .tooltip(Tooltip.of(Text.translatable("button.saxophone.toggle.avarice.tooltip")))
                .build();
        toggleInvincibility = SelectorWidget.factory(Text.translatable("button.saxophone.toggle.invincibility"),
                        button -> {
            ac.c2sInvincibility(
                    !ac.isInvincible()
            );
            indicatorInvincibility.updateIcon(ac.isInvincible());
        })
                .dimensions(width / 2 - 70, 70, 200, 20)
                .tooltip(Tooltip.of(Text.translatable("button.saxophone.toggle.invincibility.tooltip")))
                .build();
        toggleInvisibility = SelectorWidget.factory(Text.translatable("button.saxophone.toggle.invisibility"),
                        button -> {
            ac.c2sInvisibility(
                    !ac.isInvisible()
            );
            indicatorInvisibility.updateIcon(ac.isInvisible());
        })
                .dimensions(width / 2 - 70, 100, 200, 20)
                .tooltip(Tooltip.of(Text.translatable("button.saxophone.toggle.invisibility.tooltip")))
                .build();
        toggleWavering = SelectorWidget.factory(Text.translatable("button.saxophone.toggle.wavering"),
                        button -> {
            ac.c2sWavering(
                    !ac.isWavering()
            );
            indicatorWavering.updateIcon(ac.isWavering());
        })
                .dimensions(width / 2 - 70, 130, 200, 20)
                .tooltip(Tooltip.of(Text.translatable("button.saxophone.toggle.wavering.tooltip")))
                .build();
        toggleTransparency = SelectorWidget.factory(Text.translatable("button.saxophone.toggle.transparency"),
                        button -> {
            ac.c2sTransparency(
                    !ac.isTransparent()
            );
            indicatorTransparency.updateIcon(ac.isTransparent());
        })
                .dimensions(width / 2 - 70, 160, 200, 20)
                .tooltip(Tooltip.of(Text.translatable("button.saxophone.toggle.transparency.tooltip")))
                .build();

        //
        indicatorAvarice = new IndicatorWidget(
                width/2-96,
                42,
                ac.isAvarice()
        );
        indicatorInvincibility = new IndicatorWidget(
                width/2-96,
                72,
                ac.isInvincible()
        );
        indicatorInvisibility = new IndicatorWidget(
                width/2-96,
                102,
                ac.isInvisible()
        );
        indicatorWavering = new IndicatorWidget(
                width/2-96,
                132,
                ac.isWavering()
        );
        indicatorTransparency = new IndicatorWidget(
                width/2-96,
                162,
                ac.isTransparent()
        );

        //Init controls
        addDrawableChild(toggleAvarice);
        addDrawableChild(toggleInvincibility);
        addDrawableChild(toggleInvisibility);
        addDrawableChild(toggleWavering);
        addDrawableChild(toggleTransparency);

        //Init limits based on whether Avarice is enabled
        toggleInvincibility.active = ac.isAvarice();
        toggleInvisibility.active = ac.isAvarice();
        toggleWavering.active = ac.isAvarice();
        toggleTransparency.active = ac.isAvarice();

        //Init visual indicators
        addDrawable(indicatorAvarice);
        addDrawable(indicatorInvincibility);
        addDrawable(indicatorInvisibility);
        addDrawable(indicatorWavering);
        addDrawable(indicatorTransparency);
    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(
                this.textRenderer,
                Text.translatable("screen.saxophone.avarice.controls"),
                width / 2,
                20,
                0xffffff);

    }

}