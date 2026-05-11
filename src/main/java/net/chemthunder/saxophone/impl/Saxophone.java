package net.chemthunder.saxophone.impl;

import net.acoyt.acornlib.api.ALib;
import net.chemthunder.saxophone.impl.command.AvariceCommands;
import net.chemthunder.saxophone.impl.command.EosCommands;
import net.chemthunder.saxophone.impl.command.MiscCommands;
import net.chemthunder.saxophone.impl.index.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Chemthunder
 * @author JadedChara
 */
public class Saxophone implements ModInitializer {

    //~~~~~~~~~~~~~~~~~
    //entrypoint command for allowing Nightstrike to test the mod's functions.
    // Stripped of usefulness outside of names lol.
    public static final GameRules.Key<GameRules.BooleanRule> allowNightstrikeShenanigans =
            GameRuleRegistry.register(
                    "allowNightstrikeShenanigans",
                    GameRules.Category.MISC,
                    GameRuleFactory.createBooleanRule(false)
                    );
    //~~~~~~~~~~~~~~~~~


	public static final String MOD_ID = "saxophone";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final int modColor = 0xd70048;

    private static final MutableText MOD_NAME = Text.literal("Saxophone");
    private static final MutableText MOD_SUMMARY = Text.literal("Consequences of your actions.");

    private static final ALib.ModMenuData DATA = new ALib.ModMenuData(
            MOD_NAME.withColor(0xd70048),
            MOD_SUMMARY.withColor(0xd70048),
            Text.literal(
                    "\"I looked down upon a feeble world, and breathed in excitement. The world is yours to take, and I reached for the stars.\""
            ).formatted(Formatting.ITALIC)
    );

    public static List<UUID> ALL_CONTRACTED_PLAYERS = new ArrayList<>();

	public void onInitialize() {
        SaxoItems.init();
        SaxoDataComponents.init();
        SaxoItemGroups.init();
        SaxoEntities.init();
        SaxoBlocks.init();
        SaxoParticles.init();
        SaxoSoundEvents.init();
        SaxoBlockEntities.init();
        SaxoStatusEffects.init();

        SaxoNetworking.registerTypes();
        SaxoNetworking.registerC2SPackets();

        registerEvents();

        ALib.registerModData(MOD_ID, DATA);
	}

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }

    /**
     * Checks if an entity is Scarlet / Ivory / Netvision
     * @param entity The entity to check.
     */
    public static boolean isScarlet(Entity entity) {
        return entity != null && (entity.getUuidAsString().equals("c38f83cf-2723-497a-9327-f5937fb2fc08"))|| (isChem(entity));
    }
    /**
     * Checks if an entity is Chemthunder.
     * @param entity The entity to check.
     */
    public static boolean isChem(Entity entity) {
        return entity != null && (entity.getUuidAsString().equals("a26e29f1-532e-4116-9112-ca18ea30d27f"));
    }
    /**
     * Checks if an entity is YTNightstrike.
     * @param entity The entity to check.
     */
    public static boolean isNightstrike(Entity entity) {
        return entity != null && (entity.getUuidAsString().equals("4d495917-0c94-4758-9e7d-b66a03f0d648"));
    }
    /**
     * Checks if an entity is AcoYT. Unused due to Aco's lack of activity.
     * @param entity The entity to check.
     */
    @Deprecated
    public static boolean isAco(Entity entity) {
        return entity != null && (entity.getUuidAsString().equals(""));
    }
    /**
     * Checks if an entity is Hstar747.
     * @param entity The entity to check.
     */
    public static boolean isHstar(Entity entity) {
        return entity != null && (entity.getUuidAsString().equals("edcc4442-f423-4b3b-bb58-c6661085195c"));
    }
    /**
     * Checks if an entity is Heartless.
     * @param entity The entity to check.
     */
    public static boolean isHeartless(Entity entity) {
        return entity != null && (entity.getUuidAsString().equals("d0f1f0f4-631e-4290-9f60-78ace9e5e0ef"));
    }


    /**
     * Registers command sets.
     *
     */
    private static void registerEvents() {
        CommandRegistrationCallback.EVENT.register(new AvariceCommands());
        CommandRegistrationCallback.EVENT.register(new EosCommands());
        CommandRegistrationCallback.EVENT.register(new MiscCommands());
    }
}