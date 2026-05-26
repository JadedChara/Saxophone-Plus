package net.chemthunder.saxophone.core.util;

import com.everest.hibiscus.api.modules.rendering.text.HibiscusPresetEffects;
import com.everest.hibiscus.api.modules.rendering.text.registry.TextEffectManager;
import net.chemthunder.saxophone.core.Saxophone;
import net.chemthunder.saxophone.core.cca.deity.AvariceComponent;
import net.chemthunder.saxophone.core.cca.deity.EosComponent;
import net.chemthunder.saxophone.core.cca.world.AvariceEventComponent;
import net.chemthunder.saxophone.core.index.SaxoItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

import java.util.List;

/**
 * @author Chemthunder
 * @author AcoYT
 */
public class ModUtils {
    public static RegistryKey<World> asphodelKey = RegistryKey.of(RegistryKeys.WORLD, Saxophone.id("asphodel"));

    public static boolean isAvarice(Entity entity) {
        return entity instanceof PlayerEntity player && AvariceComponent.KEY.get(player).isAvarice();
    }

    public static boolean isEos(Entity entity) {
        return entity instanceof PlayerEntity player && EosComponent.KEY.get(player).isEos();
    }

    /**
     * Applies the mod name effect.
     * @param text The text to apply to.
     */
    public static Style nameEffect(MutableText text) {
        return TextEffectManager.withEffect(text.getStyle(), HibiscusPresetEffects.DOUBLE_LERP_WAVE_EFFECT, TextEffectManager.getEffect(HibiscusPresetEffects.DOUBLE_LERP_WAVE_EFFECT));
    }

    /**
     * Applies the mod name effect.
     * @param text The text to apply to.
     */
    public static Style nameEffect(Text text) {
        return TextEffectManager.withEffect(text.getStyle(), HibiscusPresetEffects.DOUBLE_LERP_WAVE_EFFECT, TextEffectManager.getEffect(HibiscusPresetEffects.DOUBLE_LERP_WAVE_EFFECT));
    }

    /**
     * Gets the entities around a specified entity.
     * @param living The entity to check around.
     * @param expansion The size of the area to check.
     */
    public static List<Entity> getEntitiesAroundEntity(Entity living, int expansion) {
        return living.getWorld().getEntitiesByClass(Entity.class, new Box(living.getBlockPos()).expand(expansion), entity -> true);
    }

    /**
     * Gets the entities around a specified BlockPos.
     * @param pos The position to check.
     * @param world The world to use.
     * @param expansion The size of the area to check.
     */
    public static List<Entity> getEntitiesAroundPos(BlockPos pos, World world, int expansion) {
        return world.getEntitiesByClass(Entity.class, new Box(pos).expand(expansion), entity -> true);
    }

    /**
     * Checks if an entity is in Asphodel.
     * @param living The entity to check.
     */
    public static boolean isInAsphodel(LivingEntity living) {
        return living != null && living.getWorld().getRegistryKey() == asphodelKey;
    }

    /**
     * Gets the current Asphodel instance.
     * @param server The server to check.
     */
    public static ServerWorld getAsphodel(MinecraftServer server) {
        return server.getWorld(asphodelKey);
    }

    /**
     * Teleports an entity to Asphodel.
     * @param living The entity to teleport.
     */
    public static void teleportToAsphodel(LivingEntity living) {
        Vec3d pos = new Vec3d(living.getX(), -319, living.getZ());

        if (living.getServer() != null) {
            living.teleportTo(new TeleportTarget(getAsphodel(living.getServer()), pos, living.getVelocity(), living.getYaw(), living.getPitch(), TeleportTarget.NO_OP));
        }
    }

    public static boolean isFollyActive(World world) {
        return world != null && AvariceEventComponent.KEY.get(world).getState();
    }

    /**
     * Checks if a player has a specified item in either of their hands.
     * @param player The player to check.
     * @param item The item to check for.
     */
    public static boolean hasItemInHands(PlayerEntity player, Item item) {
        return player.getMainHandStack().isOf(item) || player.getOffHandStack().isOf(item);
    }

    /**
     * Checks if a player is viable for permakill.
     * @param entity The entity to check.
     */
    public static boolean isViableForSaxophone(PlayerEntity entity) {
        return !ModUtils.isAvarice(entity) || !entity.getInventory().contains(SaxoItems.DEIFIC_WARRANT.getDefaultStack());
    }
}