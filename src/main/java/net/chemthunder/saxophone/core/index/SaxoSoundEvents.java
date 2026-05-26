package net.chemthunder.saxophone.core.index;

import net.acoyt.acornlib.api.registrants.SoundEventRegistrant;
import net.chemthunder.saxophone.core.Saxophone;
import net.minecraft.sound.SoundEvent;

/**
 * @author Chemthunder
 */
public interface SaxoSoundEvents {
    SoundEventRegistrant SOUND_EVENTS = new SoundEventRegistrant(Saxophone.MOD_ID);

    SoundEvent LIBERATION_SWING = SOUND_EVENTS.register("liberation_swing");
    SoundEvent MARTYRDOM_SWING = SOUND_EVENTS.register("martyrdom_swing");
    SoundEvent BELL_TOLL = SOUND_EVENTS.register("bell_toll");
    SoundEvent COVETOUS_PLACE = SOUND_EVENTS.register("covetous_place");
    SoundEvent FOLLY_BEGIN = SOUND_EVENTS.register("folly_begin");

    static void init() {}
}