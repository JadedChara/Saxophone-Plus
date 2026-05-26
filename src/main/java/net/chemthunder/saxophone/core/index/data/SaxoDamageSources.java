package net.chemthunder.saxophone.core.index.data;

import net.chemthunder.saxophone.core.Saxophone;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chemthunder
 */
public interface SaxoDamageSources {
    List<DamageSourceData> DATA = new ArrayList<>();

    RegistryKey<DamageType> LIBERATE = register("liberate", 0.0f);
    RegistryKey<DamageType> AVARICES_WILL = register("avarices_will", 0.0f);
    RegistryKey<DamageType> IVORY_EXPLODE = register("ivory_explode", 99.9f);
    RegistryKey<DamageType> CLEANSE = register("cleanse", 0.0f);

    private static RegistryKey<DamageType> register(String name, float exhaustion) {
        RegistryKey<DamageType> key = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Saxophone.id(name));
        DamageSourceData data = new DamageSourceData(key, name, exhaustion);

        DATA.add(data);
        return key;
    }

    static void bootstrap(Registerable<DamageType> registerable) {
        DATA.forEach(damageSourceData -> registerable.register(damageSourceData.key, new DamageType(damageSourceData.name, damageSourceData.exhaustion)));
    }

    record DamageSourceData(RegistryKey<DamageType> key, String name, float exhaustion) {}
}
