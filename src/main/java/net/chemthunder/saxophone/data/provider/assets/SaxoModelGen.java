package net.chemthunder.saxophone.data.provider.assets;

import net.chemthunder.saxophone.core.index.SaxoBlocks;
import net.chemthunder.saxophone.core.index.SaxoItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

/**
 * @author Chemthunder
 */
public class SaxoModelGen extends FabricModelProvider {
    public SaxoModelGen(FabricDataOutput output) {
        super(output);
    }

    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(SaxoBlocks.CLOUDED_THOUGHT);
        blockStateModelGenerator.registerSimpleState(SaxoBlocks.COVETOUS_MONOLITH);
    }

    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(SaxoItems.LIBERATION, Models.GENERATED);

        itemModelGenerator.register(SaxoItems.CONTRACT, Models.GENERATED);
        itemModelGenerator.register(SaxoItems.CONTRACT, "_signed", Models.GENERATED);

        itemModelGenerator.register(SaxoItems.FORSAKEN_CHARTER, Models.GENERATED);
        itemModelGenerator.register(SaxoItems.DEIFIC_WARRANT, Models.GENERATED);
        itemModelGenerator.register(SaxoItems.VIRTUS_DEI, Models.GENERATED);
        itemModelGenerator.register(SaxoItems.WRATH_OF_TWILIGHT, Models.GENERATED);
        itemModelGenerator.register(SaxoItems.MARTYRDOM, Models.GENERATED);
    }
}