package net.chemthunder.saxophone.impl.client.screens;

import net.chemthunder.saxophone.impl.cca.entity.ArchitectComponent;
import net.chemthunder.saxophone.impl.client.screens.widgets.IndicatorWidget;
import net.chemthunder.saxophone.impl.client.screens.widgets.SelectorWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.text.Text;

public class ArchitectScreen extends Screen {
    public ArchitectScreen() {
        super(Text.translatable("screen.saxophone.architect.controls"));
    }

    public ArchitectComponent ac;

    public SelectorWidget toggleWavering;
    public SelectorWidget toggleFX;
    public SelectorWidget toggleFlair;

    public IndicatorWidget indicatorWavering;
    public IndicatorWidget indicatorFX;
    public IndicatorWidget indicatorFlair;

    @Override
    protected void init() {
        ac = ArchitectComponent.KEY.get(MinecraftClient.getInstance().player);
        //TOGGLES

        toggleWavering = SelectorWidget.factory(Text.translatable("button.saxophone.toggle.wavering"),
                        button -> {
                            ac.c2sWavering(
                                    !ac.hasWavering()
                            );
                            indicatorWavering.updateIcon(ac.hasWavering());
                        })
                .dimensions(width / 2 - 70, 40, 200, 20)
                .tooltip(Tooltip.of(Text.translatable("button.saxophone.toggle.wavering.tooltip")))
                .build();
        toggleFX = SelectorWidget.factory(Text.translatable("button.saxophone.toggle.fx"),
                        button -> {
                            ac.c2sFX(
                                    !ac.hasFX()
                            );
                            indicatorFX.updateIcon(ac.hasFX());
                        })
                .dimensions(width / 2 - 70, 70, 200, 20)
                .tooltip(Tooltip.of(Text.translatable("button.saxophone.toggle.fx.tooltip")))
                .build();
        toggleFlair = SelectorWidget.factory(Text.translatable("button.saxophone.toggle.flair"),
                        button -> {
                            ac.c2sFlair(
                                    !ac.hasFlair()
                            );
                            indicatorFlair.updateIcon(ac.hasFlair());
                        })
                .dimensions(width / 2 - 70, 100, 200, 20)
                .tooltip(Tooltip.of(Text.translatable("button.saxophone.toggle.flair.tooltip")))
                .build();

        //
        indicatorWavering = new IndicatorWidget(
                width/2-96,
                42,
                ac.hasWavering()
        );
        indicatorFX = new IndicatorWidget(
                width/2-96,
                72,
                ac.hasFX()
        );
        indicatorFlair = new IndicatorWidget(
                width/2-96,
                102,
                ac.hasFlair()
        );


        //Init controls
        addDrawableChild(toggleWavering);
        addDrawableChild(toggleFX);
        addDrawableChild(toggleFlair);


        //Init visual indicators
        addDrawable(indicatorWavering);
        addDrawable(indicatorFX);
        addDrawable(indicatorFlair);
    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(
                this.textRenderer,
                Text.translatable("screen.saxophone.architect.controls"),
                width / 2,
                20,
                0xffffff);

    }

}