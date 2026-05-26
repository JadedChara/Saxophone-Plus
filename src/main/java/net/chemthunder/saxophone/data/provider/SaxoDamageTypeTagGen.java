package net.chemthunder.saxophone.data.provider;

import net.chemthunder.saxophone.core.index.data.SaxoDamageSources;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.TagKey;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Chemthunder
 */
public class SaxoDamageTypeTagGen extends FabricTagProvider<DamageType> {
    public SaxoDamageTypeTagGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.DAMAGE_TYPE, registriesFuture);
    }

    List<TagKey<DamageType>> BYPASSES_ALL = List.of(
            DamageTypeTags.BYPASSES_ARMOR,
            DamageTypeTags.BYPASSES_ENCHANTMENTS,
            DamageTypeTags.BYPASSES_INVULNERABILITY,
            DamageTypeTags.BYPASSES_SHIELD,
            DamageTypeTags.BYPASSES_EFFECTS
    );

    protected void configure(RegistryWrapper.WrapperLookup lookup) {
        registerOmnipotentDamageSource(SaxoDamageSources.AVARICES_WILL);
        registerOmnipotentDamageSource(SaxoDamageSources.CLEANSE);
    }

    private void registerOmnipotentDamageSource(RegistryKey<DamageType> source) {
        for (TagKey<DamageType> key : BYPASSES_ALL) {
            this.getOrCreateTagBuilder(key).add(source).setReplace(false);
        }
    }
}