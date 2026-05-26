package net.chemthunder.saxophone.data;

import net.chemthunder.saxophone.data.provider.SaxoDamageTypeTagGen;
import net.chemthunder.saxophone.data.provider.SaxoDynamicRegistryGen;
import net.chemthunder.saxophone.data.provider.assets.SaxoLangGen;
import net.chemthunder.saxophone.data.provider.assets.SaxoModelGen;
import net.chemthunder.saxophone.core.index.data.SaxoDamageSources;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

/**
 * @author Chemthunder
 */
public class SaxophoneDataGen implements DataGeneratorEntrypoint {
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(SaxoDynamicRegistryGen::new);
        
        pack.addProvider(SaxoLangGen::new);
        pack.addProvider(SaxoModelGen::new);
        pack.addProvider(SaxoDamageTypeTagGen::new);
	}

    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.DAMAGE_TYPE, SaxoDamageSources::bootstrap);
    }
}