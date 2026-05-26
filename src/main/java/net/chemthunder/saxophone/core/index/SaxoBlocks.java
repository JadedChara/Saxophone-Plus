package net.chemthunder.saxophone.core.index;

import net.acoyt.acornlib.api.registrants.BlockRegistrant;
import net.chemthunder.saxophone.core.Saxophone;
import net.chemthunder.saxophone.core.block.AsphodelTerrainBlock;
import net.chemthunder.saxophone.core.block.CovetousMonolithBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

/**
 * @author Chemthunder
 */
public interface SaxoBlocks {
    BlockRegistrant BLOCKS = new BlockRegistrant(Saxophone.MOD_ID);

    Block CLOUDED_THOUGHT = BLOCKS.register("clouded_thought", AsphodelTerrainBlock::new, AbstractBlock.Settings.copy(Blocks.BEDROCK));
    Block COVETOUS_MONOLITH = BLOCKS.register("covetous_monolith", CovetousMonolithBlock::new, AbstractBlock.Settings.copy(Blocks.BEDROCK));

    static void init() {}
    
    static void clientInit() {}
}