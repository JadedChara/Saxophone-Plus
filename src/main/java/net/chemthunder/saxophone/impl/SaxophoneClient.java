package net.chemthunder.saxophone.impl;

import net.chemthunder.saxophone.api.event.WindowTitleEvent;
import net.chemthunder.saxophone.impl.client.render.block.CovetousMonolithBlockEntityRenderer;
import net.chemthunder.saxophone.impl.index.SaxoBlockEntities;
import net.chemthunder.saxophone.impl.index.SaxoEntities;
import net.chemthunder.saxophone.impl.index.SaxoNetworking;
import net.chemthunder.saxophone.impl.index.SaxoParticles;
import net.chemthunder.saxophone.impl.util.ModUtils;
import net.chemthunder.saxophone.impl.util.keybinds.SaxophoneKeybindings;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.util.Identifier;
import org.ladysnake.satin.api.event.EntitiesPreRenderCallback;
import org.ladysnake.satin.api.event.ShaderEffectRenderCallback;
import org.ladysnake.satin.api.managed.ManagedCoreShader;
import org.ladysnake.satin.api.managed.ShaderEffectManager;
import org.ladysnake.satin.api.managed.uniform.Uniform1f;
import org.ladysnake.satin.api.managed.uniform.Uniform3f;
import org.ladysnake.satin.api.managed.uniform.Uniform4f;

/**
 * @author Chemthunder
 */
public class SaxophoneClient implements ClientModInitializer {

    //Shader management c/o Nightstrike
    public static final ManagedCoreShader transientEffect = ShaderEffectManager
            .getInstance()
            .manageCoreShader(
                    Identifier.of(Saxophone.MOD_ID, "transient")
            );
    public static final ManagedCoreShader glitchingEffect = ShaderEffectManager
            .getInstance()
            .manageCoreShader(
                    Identifier.of(Saxophone.MOD_ID, "glitch")
            );
    public Uniform1f tTransient = transientEffect.findUniform1f("STime");
    public Uniform1f rTransient = transientEffect.findUniform1f("Randomizer");

    public Uniform1f gtime = glitchingEffect.findUniform1f("STime");
    public Uniform1f grandomizer = glitchingEffect.findUniform1f("Randomizer");
    public Uniform3f grgb = glitchingEffect.findUniform3f("RandomRGB");
    public Uniform4f gmask = glitchingEffect.findUniform4f("RandomMasking");

    public int tick;

    //
    public void onInitializeClient() {
        ShaderEffectRenderCallback.EVENT.register(td -> {
            //TO-DO: Client screen effects
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> tick++);
        EntitiesPreRenderCallback.EVENT.register(
                (c, f, td) -> {
                    //transient
                    tTransient.set((tick + td) * 0.05f);
                    rTransient.set((float) (Math.random()));
                    //glitching
                    gtime.set((tick + td) * 0.05f);
                    grandomizer.set((float) (Math.random()));
                    grgb.set(
                            ((float)(Math.random())),
                            ((float)(Math.random())),
                            ((float)(Math.random()))
                    );
                    gmask.set(
                            ((float)(Math.random())),
                            ((float)(Math.random())),
                            ((float)(Math.random())),
                            ((float)(Math.random()))
                    );
                }
        );
        //------------------------

        SaxoEntities.clientInit();
        SaxoParticles.clientInit();
        SaxophoneKeybindings.register();
        SaxoNetworking.registerS2CPackets();
        BlockEntityRendererFactories.register(SaxoBlockEntities.COVETOUS_MONOLITH, CovetousMonolithBlockEntityRenderer::new);

        // window titles
        WindowTitleEvent.register(
                "let's play a game, friend.",
                client -> ModUtils.isInAsphodel(client.player)
        );
        WindowTitleEvent.init();
    }
}
