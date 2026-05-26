package net.chemthunder.saxophone.core.util.keybinds;

import net.chemthunder.saxophone.impl.Saxophone;
import net.chemthunder.saxophone.impl.client.screens.ArchitectScreen;
import net.chemthunder.saxophone.impl.client.screens.AvariceScreen;
import net.chemthunder.saxophone.impl.client.screens.EosScreen;
import net.chemthunder.saxophone.impl.networking.c2s.ExplodeIvoryPayload;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

/**
 * @author Chemthunder
 */
public class SaxophoneKeybindings {
    public static KeyBinding explodeIvory;
    public static KeyBinding guiAvarice;
    public static KeyBinding guiArchitect;
    public static KeyBinding guiEos;

    public static void register() {
        registerKeyBindings();
        setupPressDetection();
    }

    private static void registerKeyBindings() {
        String saxophoneCategory = "category.saxophone";
        explodeIvory = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.saxophone.explode_ivory",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UP,
                saxophoneCategory
        ));

        guiAvarice = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.saxophone.avarice_gui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_M,
                saxophoneCategory
        ));

        guiArchitect = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.saxophone.architect_gui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                saxophoneCategory
        ));

        guiEos = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.saxophone.eos_gui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                saxophoneCategory
        ));
    }

    private static void setupPressDetection() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (client.player != null && explodeIvory.isPressed()) {
                handleExplodeIvory(client);
            }
            if (client.player != null && guiAvarice.isPressed()) {
                if(client.world != null && Saxophone.isScarlet(client.player)){
                    client.setScreen(new AvariceScreen());
                }
                //handleExplodeIvory(client);
            }
            if (client.player != null && guiArchitect.isPressed()) {
                if(client.world != null && Saxophone.isContributor(client.player)){
                    client.setScreen(new ArchitectScreen());
                }
            }
            if (client.player != null && guiEos.isPressed()) {
                if(client.world != null && Saxophone.isChem(client.player)){
                    client.setScreen(new EosScreen());
                }
            }
        });
    }

    private static void handleExplodeIvory(MinecraftClient client) {
        if (client.player != null) {
            try {
                ExplodeIvoryPayload.send();
            } catch (Exception e) {
                Saxophone.LOGGER.error("Failed to send Ikir Switch Payload");
            }
        }
    }
}
