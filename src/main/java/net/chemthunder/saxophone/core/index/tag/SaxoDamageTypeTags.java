package net.chemthunder.saxophone.core.index.tag;

import net.acoyt.acornlib.api.util.MiscUtils;
import net.chemthunder.saxophone.core.Saxophone;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Chemthunder
 */
public interface SaxoDamageTypeTags {
    Map<TagKey<DamageType>, String> DAMAGE_TAGS = new HashMap<>();

    TagKey<DamageType> BYPASSES_ALL = create("bypasses_all");
    TagKey<DamageType> ASPHODEL_BYPASS = create("asphodel_bypass");

    private static TagKey<DamageType> create(String id) {
        TagKey<DamageType> gen = TagKey.of(RegistryKeys.DAMAGE_TYPE, Saxophone.id(id));
        DAMAGE_TAGS.put(gen, id);
        return gen;
    }

    static void registerLang(FabricLanguageProvider.TranslationBuilder builder) {
        DAMAGE_TAGS.forEach((damageTypeTagKey, s) -> {
            builder.add(damageTypeTagKey.getTranslationKey(), MiscUtils.formatString(s));
        });
    }
}
