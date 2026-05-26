package net.chemthunder.saxophone.core.index;

import net.acoyt.acornlib.api.registrants.StatusEffectRegistrant;
import net.chemthunder.saxophone.core.Saxophone;
import net.chemthunder.saxophone.core.effect.SaxophoneBackgroundStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

/**
 * @author Chemthunder
 */
public interface SaxoStatusEffects {
    StatusEffectRegistrant STATUS_EFFECTS = new StatusEffectRegistrant(Saxophone.MOD_ID);

    StatusEffect MADNESS = STATUS_EFFECTS.register("madness", new SaxophoneBackgroundStatusEffect(StatusEffectCategory.NEUTRAL, 0xFF000000));
    StatusEffect REPENTANCE = STATUS_EFFECTS.register("repentance", new SaxophoneBackgroundStatusEffect(StatusEffectCategory.NEUTRAL, 0xFF400a1b));

    static void init() {}
}