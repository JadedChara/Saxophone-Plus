package net.chemthunder.saxophone.core.index;

import net.chemthunder.saxophone.core.Saxophone;
import net.chemthunder.saxophone.core.client.particle.ShockwaveParticle;
import net.chemthunder.saxophone.core.client.particle.ShockwaveParticleEffect;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.client.particle.SoulParticle;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

/**
 * @author Chemthunder
 */
public interface SaxoParticles {
    ParticleType<ShockwaveParticleEffect> SHOCKWAVE = create("shockwave", FabricParticleTypes.complex(true, ShockwaveParticleEffect.CODEC, ShockwaveParticleEffect.PACKET_CODEC));

    SimpleParticleType AVARICE_SOUL = create("avarice_soul", FabricParticleTypes.simple());
    SimpleParticleType AVARICE_GLIMMER = create("avarice_glimmer", FabricParticleTypes.simple());
    SimpleParticleType AVARICE_LIGHT = create("avarice_light", FabricParticleTypes.simple());

    private static <T extends ParticleType<?>> T create(String name, T particle) {
        return Registry.register(Registries.PARTICLE_TYPE, Saxophone.id(name), particle);
    }

    static void init() {}

    static void clientInit() {
        ParticleFactoryRegistry.getInstance().register(SHOCKWAVE, ShockwaveParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(AVARICE_SOUL, SoulParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(AVARICE_GLIMMER, EndRodParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(AVARICE_LIGHT, EndRodParticle.Factory::new);
    }
}