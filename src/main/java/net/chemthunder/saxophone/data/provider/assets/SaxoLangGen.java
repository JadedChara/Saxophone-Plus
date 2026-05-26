package net.chemthunder.saxophone.data.provider.assets;

import net.chemthunder.saxophone.core.index.SaxoItems;
import net.chemthunder.saxophone.core.index.SaxoStatusEffects;
import net.chemthunder.saxophone.core.index.data.SaxoDamageSources;
import net.chemthunder.saxophone.core.index.tag.SaxoDamageTypeTags;
import net.chemthunder.saxophone.core.util.DatagenUtils;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

/**
 * @author Chemthunder
 */
public class SaxoLangGen extends FabricLanguageProvider {
    public SaxoLangGen(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        SaxoItems.ITEMS.registerLang(wrapperLookup, translationBuilder);
        SaxoDamageTypeTags.registerLang(translationBuilder);
        SaxoStatusEffects.STATUS_EFFECTS.registerLang(wrapperLookup, translationBuilder);

        translationBuilder.add("itemGroup.saxophone", "Saxophone");

        // Item Misc
        translationBuilder.add("item.saxophone.contract_signed", "Signed Contract");

        // Subtitles
        translationBuilder.add("sounds.saxophone.liberation_swing", "Liberation Swings");
        translationBuilder.add("sounds.saxophone.bell_toll", "Virtus Dei tolls");
        translationBuilder.add("sounds.saxophone.covetous_monolith", "Covetous Monolith is placed");

        // Damage Sources
        DatagenUtils.Lang.registerSingleMessageDamageType(translationBuilder, SaxoDamageSources.AVARICES_WILL,
                "%1$s was claimed by unknown forces"
        );

        DatagenUtils.Lang.registerSingleMessageDamageType(translationBuilder, SaxoDamageSources.CLEANSE,
                "%1$s had their soul cleansed of sin"
        );

        DatagenUtils.Lang.registerDamageType(translationBuilder, SaxoDamageSources.LIBERATE,
                "%1$s was liberated",
                "%1$s was liberated by %2$s wielding %3$s",
                "%1$s was liberated by %2$s"
        );

        // Keybindings
        translationBuilder.add("category.saxophone", "Saxophone");
        translationBuilder.add("key.saxophone.explode_ivory", "Cause Scarlet to detonate");
        translationBuilder.add("key.saxophone.avarice_gui","Open Avarice Controls");
        translationBuilder.add("key.saxophone.eos_gui","Open Eos Controls");
        translationBuilder.add("key.saxophone.architect_gui","Open Cosmetics Controls");

        // Screens
        translationBuilder.add("screen.saxophone.avarice.controls","Avarice Controls");
        translationBuilder.add("screen.saxophone.eos.controls","Eos Controls");
        translationBuilder.add("screen.saxophone.architect.controls","Architect Cosmetics");

        // Widget Titles
        translationBuilder.add("button.saxophone.toggle.avarice","Toggle Avarice");
        translationBuilder.add(
                "button.saxophone.toggle.avarice.tooltip",
                "Enable/disable model rendering and name change for Avarice, as well as other features..."
        );

        translationBuilder.add("button.saxophone.toggle.invincibility","Toggle Invincibility");
        translationBuilder.add(
                "button.saxophone.toggle.invincibility.tooltip",
                "Prevent damage to Avarice, but likewise prevent Avarice from inflicting damage..."
        );

        translationBuilder.add("button.saxophone.toggle.invisibility","Toggle Invisibility");
        translationBuilder.add(
                "button.saxophone.toggle.invisibility.tooltip",
                "Enable/disable rendering of Avarice, as well as particles, shadow, and footsteps..."
        );

        translationBuilder.add("button.saxophone.toggle.wavering","Toggle Wavering");
        translationBuilder.add(
                "button.saxophone.toggle.wavering.tooltip",
                "Enable/disable a wavering text style for the player name (and sometimes speech)..."
        );

        translationBuilder.add("button.saxophone.toggle.transparency","Toggle Transparency");
        translationBuilder.add(
                "button.saxophone.toggle.transparency.tooltip",
                "Enable/disable a transparent shader for Avarice..."
        );

        translationBuilder.add("button.saxophone.toggle.flair","Toggle Username Flair");
        translationBuilder.add(
                "button.saxophone.toggle.flair.tooltip",
                "Enable/disable a custom username style set for contributors..."
        );

        translationBuilder.add("button.saxophone.toggle.fx","Toggle FX");
        translationBuilder.add(
                "button.saxophone.toggle.fx.tooltip",
                "Enable/disable custom effects, usually core shaders, for contributors' player models..."
        );

        translationBuilder.add("button.saxophone.toggle.eos","Toggle Eos");
        translationBuilder.add(
                "button.saxophone.toggle.eos.tooltip",
                "Enable/disable model rendering and name change for Eos, as well as other features..."
        );

        translationBuilder.add("button.saxophone.toggle.flight","Toggle Flight");
        translationBuilder.add(
                "button.saxophone.toggle.flight.tooltip",
                "Enable/disable flight for Eos..."
        );

        translationBuilder.add("button.saxophone.toggle.shield_avarice","Shield Avarice");
        translationBuilder.add(
                "button.saxophone.toggle.shield_avarice.tooltip",
                "Enable/disable protection for Avarice, should Eos deem it worthy..."
        );

    }
}