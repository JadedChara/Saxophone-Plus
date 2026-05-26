package net.chemthunder.resewn.core;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

/**
 * @author Chemthunder
 */
public class Resewn implements ModInitializer {
    public static final String MOD_ID = "resewn";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public void onInitialize() {

    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
