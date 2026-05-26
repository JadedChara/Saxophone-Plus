package net.chemthunder.saxophone.impl.client.screens;

import net.chemthunder.saxophone.impl.cca.deity.AvariceComponent;
import net.chemthunder.saxophone.impl.cca.deity.EosComponent;
import net.chemthunder.saxophone.impl.client.screens.widgets.IndicatorWidget;
import net.chemthunder.saxophone.impl.client.screens.widgets.SelectorWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.text.Text;

public class EosScreen extends Screen {
    public EosScreen() {
        super(Text.translatable("screen.saxophone.eos.controls"));
    }

    public EosComponent ec;

    public SelectorWidget toggleEos;
    public SelectorWidget toggleFlight;
    public SelectorWidget shieldAvarice;

    public IndicatorWidget indicatorEos;
    public IndicatorWidget indicatorFlight;
    public IndicatorWidget indicatorShieldAvarice;

    public boolean isAvariceShielded;
    public boolean isServer = false;

    @Override
    protected void init() {
        ec = EosComponent.KEY.get(MinecraftClient.getInstance().player);
        if(MinecraftClient.getInstance().player.getServer() != null){
            this.isServer = true;
            MinecraftClient.getInstance().player.getServer().getPlayerManager().getPlayerList().forEach(player -> {
                if(AvariceComponent.KEY.get(player).isAvarice()){
                    isAvariceShielded = AvariceComponent.KEY.get(player).isInvincible();
                }
            });
        }
        //TOGGLES

        toggleEos = SelectorWidget.factory(Text.translatable("button.saxophone.toggle.eos"),
                        button -> {
                            ec.c2sEos(
                                    !ec.isEos()
                            );
                            indicatorEos.updateIcon(ec.isEos());
                        })
                .dimensions(width / 2 - 70, 40, 200, 20)
                .tooltip(Tooltip.of(Text.translatable("button.saxophone.toggle.eos.tooltip")))
                .build();
        toggleFlight = SelectorWidget.factory(Text.translatable("button.saxophone.toggle.flight"),
                        button -> {
                            ec.c2sFlight(
                                    !ec.canFly()
                            );
                            indicatorFlight.updateIcon(ec.canFly());
                        })
                .dimensions(width / 2 - 70, 70, 200, 20)
                .tooltip(Tooltip.of(Text.translatable("button.saxophone.toggle.flight.tooltip")))
                .build();
        shieldAvarice = SelectorWidget.factory(Text.translatable("button.saxophone.toggle.shield_avarice"),
                        button -> {
                            if(isServer){
                                MinecraftClient.getInstance().getServer().getPlayerManager().getPlayerList().forEach(player -> {
                                    AvariceComponent ac = AvariceComponent.KEY.get(player);
                                    if(ac.isAvarice()){
                                        this.isAvariceShielded = ac.isInvincible();
                                        if(this.isAvariceShielded){
                                            ac.c2sInvincibility(false);
                                            ac.c2sInvisibility(false);
                                            ac.c2sTransparency(false);
                                            ac.c2sWavering(false);
                                        }else{
                                            ac.c2sInvincibility(true);
                                        }
                                    }
                                });
                            }

                            indicatorShieldAvarice.updateIcon(this.isAvariceShielded);
                        })
                .dimensions(width / 2 - 70, 100, 200, 20)
                .tooltip(Tooltip.of(Text.translatable("button.saxophone.toggle.shield_avarice.tooltip")))
                .build();

        //
        indicatorEos = new IndicatorWidget(
                width/2-96,
                42,
                ec.isEos()
        );
        indicatorFlight = new IndicatorWidget(
                width/2-96,
                72,
                ec.canFly()
        );
        indicatorShieldAvarice = new IndicatorWidget(
                width/2-96,
                102,
                this.isAvariceShielded
        );


        //Init controls
        addDrawableChild(toggleEos);
        addDrawableChild(toggleFlight);
        addDrawableChild(shieldAvarice);

        shieldAvarice.active = this.isServer;

        //Init visual indicators
        addDrawable(indicatorEos);
        addDrawable(indicatorFlight);
        addDrawable(indicatorShieldAvarice);
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