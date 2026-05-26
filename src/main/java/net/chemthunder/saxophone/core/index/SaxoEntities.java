package net.chemthunder.saxophone.core.index;

import net.acoyt.acornlib.api.registrants.EntityTypeRegistrant;
import net.chemthunder.saxophone.core.Saxophone;
import net.chemthunder.saxophone.core.client.render.entity.ForsakenCharterEntityRenderer;
import net.chemthunder.saxophone.core.client.render.entity.HopefulSkyEntityRenderer;
import net.chemthunder.saxophone.core.client.render.entity.TestBeamEntityRenderer;
import net.chemthunder.saxophone.core.entity.ForsakenCharterEntity;
import net.chemthunder.saxophone.core.entity.HopefulSkyEntity;
import net.chemthunder.saxophone.core.entity.TestBeamEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;

/**
 * @author Chemthunder
 */
@SuppressWarnings({"deprecation", "rawtypes"})
public interface SaxoEntities {
    EntityTypeRegistrant ENTITIES = new EntityTypeRegistrant<>(Saxophone.MOD_ID);

    EntityType<ForsakenCharterEntity> FORSAKEN_CHARTER = ENTITIES.register("forsaken_charter",
            EntityType.Builder.create(
                    ForsakenCharterEntity::new,
                    SpawnGroup.MISC
            ).dimensions(0.4f, 0.9f).disableSummon()
    );

    EntityType<HopefulSkyEntity> HOPEFUL_SKY = ENTITIES.register("hopeful_sky",
            EntityType.Builder.create(
                    HopefulSkyEntity::new,
                    SpawnGroup.MISC
            ).dimensions(0.4f, 0.4f)
    );

    EntityType<TestBeamEntity> TEST_BEAM = ENTITIES.register("test_beam",
            EntityType.Builder.create(
                    TestBeamEntity::new,
                    SpawnGroup.MISC
            ).dimensions(0.4f, 0.4f)
    );

    static void init() {}

    @Environment(EnvType.CLIENT)
    static void clientInit() {
        EntityRendererRegistry.register(FORSAKEN_CHARTER, ForsakenCharterEntityRenderer::new);
        EntityRendererRegistry.register(HOPEFUL_SKY, HopefulSkyEntityRenderer::new);
        EntityRendererRegistry.register(TEST_BEAM, TestBeamEntityRenderer::new);
    }
}