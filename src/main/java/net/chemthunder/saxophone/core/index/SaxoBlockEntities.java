package net.chemthunder.saxophone.core.index;

import net.acoyt.acornlib.api.registrants.BlockEntityTypeRegistrant;
import net.chemthunder.saxophone.core.Saxophone;
import net.chemthunder.saxophone.core.block.entity.CovetousMonolithBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;

/**
 * @author Chemthunder
 */
@SuppressWarnings("deprecation")
public interface SaxoBlockEntities {
    BlockEntityTypeRegistrant BLOCK_ENTITIES = new BlockEntityTypeRegistrant(Saxophone.MOD_ID);

    BlockEntityType<CovetousMonolithBlockEntity> COVETOUS_MONOLITH = BLOCK_ENTITIES.register(
            "covetous_monolith",
            FabricBlockEntityTypeBuilder.create(CovetousMonolithBlockEntity::new, SaxoBlocks.COVETOUS_MONOLITH)
    );

    static void init() {}
}