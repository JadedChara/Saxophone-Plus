package net.chemthunder.saxophone.core.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.chemthunder.oracle.impl.index.OracleItems;
import net.chemthunder.reverence.impl.index.ReverenceItems;
import net.chemthunder.saxophone.core.Saxophone;
import net.chemthunder.saxophone.core.cca.deity.AvariceComponent;
import net.chemthunder.saxophone.core.cca.entity.InsistenceComponent;
import net.chemthunder.saxophone.core.cca.world.AvariceEventComponent;
import net.chemthunder.saxophone.core.index.SaxoItems;
import net.chemthunder.saxophone.core.util.command.ExternalModArgumentType;
import net.chemthunder.saxophone.core.util.command.ItemArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

/**
 * @author Chemthunder
 */
public class AvariceCommands implements CommandRegistrationCallback {
    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        commandDispatcher.register(
                CommandManager.literal("avarice").requires(AvariceCommands::isScarlet)
                        .then(CommandManager.literal("apply").executes(context -> {
                            PlayerEntity player = context.getSource().getPlayer();
                            if (player != null) {
                                AvariceComponent component = AvariceComponent.KEY.get(player);

                                component.setAvarice(!component.isAvarice());
                                context.getSource().sendFeedback(() -> Text.literal("Set AvariceState to " + component.isAvarice()), false);
                                if (component.isAvarice()){
                                    //power up effect
                                    //player.playSound();
                                }else{
                                    //power down effect
                                    //player.playSound();
                                }
                            }
                            return Command.SINGLE_SUCCESS;
                        }).requires(AvariceCommands::isScarlet))

                        .then(CommandManager.literal("toggle")
                                .then(CommandManager.literal("invisibility").executes(context -> {
                                    PlayerEntity player = context.getSource().getPlayer();
                                    if (player != null) {
                                        AvariceComponent component = AvariceComponent.KEY.get(player);

                                        component.setInvisible(!component.isInvisible());
                                        context.getSource().sendFeedback(() -> Text.literal("Set Invisibility to " + component.isInvisible()), false);
                                    }
                                    return Command.SINGLE_SUCCESS;
                                })).requires(AvariceCommands::isScarlet)

                                .then(CommandManager.literal("invincibility").executes(context -> {
                                    PlayerEntity player = context.getSource().getPlayer();
                                    if (player != null) {
                                        AvariceComponent component = AvariceComponent.KEY.get(player);

                                        component.setInvincible(!component.isInvincible());
                                        context.getSource().sendFeedback(() -> Text.literal("Set Invulnerability to " + component.isInvincible()), false);
                                    }
                                    return Command.SINGLE_SUCCESS;
                                })).requires(AvariceCommands::isScarlet)

                                .then(CommandManager.literal("transparency").executes(context -> {
                                    PlayerEntity player = context.getSource().getPlayer();
                                    if (player != null) {
                                        AvariceComponent component = AvariceComponent.KEY.get(player);

                                        component.setTransparent(!component.isTransparent());
                                        context.getSource().sendFeedback(() -> Text.literal("Set Transparency to " + component.isTransparent()), false);
                                    }
                                    return Command.SINGLE_SUCCESS;
                                })).requires(AvariceCommands::isScarlet)

                                .then(CommandManager.literal("wavering").executes(context -> {
                                    PlayerEntity player = context.getSource().getPlayer();
                                    if (player != null) {
                                        AvariceComponent component = AvariceComponent.KEY.get(player);
                                        component.setWavering(!component.isWavering());

                                        context.getSource().sendFeedback(() -> Text.literal("Set Wavering Text to " + component.isWavering()), false);
                                    }
                                    return Command.SINGLE_SUCCESS;
                                })).requires(AvariceCommands::isScarlet)
                        ).requires(AvariceCommands::isScarlet)

                        .then(CommandManager.literal("give").requires(AvariceCommands::isScarlet).then(CommandManager.argument("item", ItemArgumentType.itemStack(commandRegistryAccess)).executes(context -> {
                            PlayerEntity player = context.getSource().getPlayer();
                            ItemStack stack = ItemStackArgumentType.getItemStackArgument(context, "item").createStack(1, false);

                            if (player != null) {
                                if (SaxoItems.ITEMS.toRegister.contains(stack.getItem())) {
                                    player.giveItemStack(stack);
                                } else {
                                    player.playSoundToPlayer(SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.MASTER, 1, 1);
                                    player.sendMessage(Text.literal("what the freak :C").formatted(Formatting.RED), true);
                                }
                            }

                            return Command.SINGLE_SUCCESS;
                        })).requires(AvariceCommands::isScarlet)).requires(AvariceCommands::isScarlet)

                        .then(CommandManager.literal("folly").requires(AvariceCommands::isScarlet)
                                .then(CommandManager.literal("begin").requires(AvariceCommands::isScarlet).executes(context -> {
                                    World world = context.getSource().getWorld();
                                    PlayerEntity player = context.getSource().getPlayer();

                                    if (world != null && player != null) {
                                        AvariceEventComponent event = AvariceEventComponent.KEY.get(world);

                                        event.beginEvent(player);
                                    }
                                    return Command.SINGLE_SUCCESS;
                                }))
                                .then(CommandManager.literal("cease").requires(AvariceCommands::isScarlet).executes(context -> {
                                    World world = context.getSource().getWorld();
                                    PlayerEntity src = context.getSource().getPlayer();

                                    if (world != null) {
                                        AvariceEventComponent event = AvariceEventComponent.KEY.get(world);

                                        if (event.getState()) {
                                            event.ceaseEvent();
                                        } else {
                                            if (src != null) {
                                                src.playSoundToPlayer(SoundEvents.BLOCK_ANVIL_DESTROY, SoundCategory.MASTER, 1, 1);
                                                src.sendMessage(Text.literal("don't be a fucking dumbass next time -chem").formatted(Formatting.RED));
                                            }
                                        }
                                    }
                                    return Command.SINGLE_SUCCESS;
                                }))
                                .then(CommandManager.literal("shade").requires(AvariceCommands::isScarlet).executes(context -> {
                                    World world = context.getSource().getWorld();

                                    if (world != null) {
                                        AvariceEventComponent event = AvariceEventComponent.KEY.get(world);

                                        event.setShade(!event.getShade());
                                    }
                                    return Command.SINGLE_SUCCESS;
                                }))
                        )

                        .then(CommandManager.literal("contracted")
                                .then(CommandManager.literal("sendToAll")
                                        .then(CommandManager.argument("broadcast", StringArgumentType.string()).executes(context -> {
                                            String sent = StringArgumentType.getString(context, "broadcast");
                                            World world = context.getSource().getWorld();

                                            if (world != null) {
                                                world.getPlayers().forEach(player -> {
                                                    if (Saxophone.ALL_CONTRACTED_PLAYERS.contains(player.getUuid())) {
                                                        player.sendMessage(Text.literal(sent), true);
                                                    }
                                                });
                                            }
                                            return Command.SINGLE_SUCCESS;
                                        })).requires(AvariceCommands::isScarlet)
                                ).requires(AvariceCommands::isScarlet)

                                .then(CommandManager.literal("lock")
                                        .then(CommandManager.argument("duration", IntegerArgumentType.integer()).executes(context -> {
                                            int ticks = IntegerArgumentType.getInteger(context, "duration");
                                            World world = context.getSource().getWorld();

                                            if (world != null) {
                                                world.getPlayers().forEach(playerEntity -> {
                                                    if (Saxophone.ALL_CONTRACTED_PLAYERS.contains(playerEntity.getUuid())) {
                                                        InsistenceComponent component = InsistenceComponent.KEY.get(playerEntity);

                                                        component.setActiveTicks(ticks * 20);
                                                    }
                                                });
                                            }

                                            return Command.SINGLE_SUCCESS;
                                        }))).requires(AvariceCommands::isScarlet)
                        ).requires(AvariceCommands::isScarlet)

                        .then(CommandManager.literal("external")
                                .then(CommandManager.literal("giveExternalItem").requires(AvariceCommands::isScarlet).then(CommandManager.argument("externalitem", ExternalModArgumentType.itemStack(commandRegistryAccess)).executes(context -> {
                                    PlayerEntity player = context.getSource().getPlayer();
                                    ItemStack stack = ExternalModArgumentType.getItemStackArgument(context, "externalitem").createStack(1, false);

                                    if (player != null) {
                                        if (EXTERNAL_ITEMS.contains(stack.getItem())) {
                                            player.giveItemStack(stack);
                                        }
                                    }

                                    return Command.SINGLE_SUCCESS;
                                })))
                        ).requires(AvariceCommands::isScarlet)
        );
    }

    private static List<Item> EXTERNAL_ITEMS = List.of(
            OracleItems.SALVATION,
            ReverenceItems.EMPTINESS
    );

    private static boolean isScarlet(ServerCommandSource source) {
        return source.getPlayer() == null || Saxophone.isScarlet(source.getEntity());// || isNightstrike(source);
    }

    private static boolean isNightstrike(ServerCommandSource source){
        return source.getPlayer() == null || (
                Saxophone.isNightstrike(source.getEntity())
                && source.getEntity().getWorld().getGameRules().getBoolean(Saxophone.allowNightstrikeShenanigans)
        );
    }
}