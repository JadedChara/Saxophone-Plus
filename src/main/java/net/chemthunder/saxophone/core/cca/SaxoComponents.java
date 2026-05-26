package net.chemthunder.saxophone.core.cca;

import net.chemthunder.saxophone.core.cca.deity.AvariceComponent;
import net.chemthunder.saxophone.core.cca.deity.EosComponent;
import net.chemthunder.saxophone.core.cca.entity.ArchitectComponent;
import net.chemthunder.saxophone.core.cca.entity.InsistenceComponent;
import net.chemthunder.saxophone.core.cca.entity.RevenantDeathAnimationComponent;
import net.chemthunder.saxophone.core.cca.entity.ScreenflashComponent;
import net.chemthunder.saxophone.core.cca.world.AvariceEventComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;
import org.ladysnake.cca.api.v3.world.WorldComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.world.WorldComponentInitializer;

/**
 * @author Chemthunder
 */
public class SaxoComponents implements EntityComponentInitializer, WorldComponentInitializer {
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.beginRegistration(PlayerEntity.class, AvariceComponent.KEY).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(AvariceComponent::new);
        registry.beginRegistration(PlayerEntity.class, EosComponent.KEY).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(EosComponent::new);
        registry.beginRegistration(PlayerEntity.class, ArchitectComponent.KEY).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(ArchitectComponent::new);

        registry.beginRegistration(PlayerEntity.class, RevenantDeathAnimationComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(RevenantDeathAnimationComponent::new);
        registry.beginRegistration(LivingEntity.class, InsistenceComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(InsistenceComponent::new);

        registry.beginRegistration(PlayerEntity.class, ScreenflashComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(ScreenflashComponent::new);
    }

    public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {
        registry.register(AvariceEventComponent.KEY, AvariceEventComponent::new);
    }
}