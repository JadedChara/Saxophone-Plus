package net.chemthunder.saxophone.core.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.chemthunder.saxophone.core.Saxophone;
import net.chemthunder.saxophone.core.cca.deity.AvariceComponent;
import net.chemthunder.saxophone.core.cca.deity.EosComponent;
import net.chemthunder.saxophone.core.entity.HopefulSkyEntity;
import net.chemthunder.saxophone.core.index.SaxoEntities;
import net.chemthunder.saxophone.core.util.ModUtils;
import net.chemthunder.saxophone.core.util.command.ItemArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class EosCommands implements CommandRegistrationCallback {
    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        commandDispatcher.register(
                CommandManager.literal("eos")
                        .then(CommandManager.literal("dev")
                                .then(CommandManager.literal("query").executes(context -> {
                                    World world = context.getSource().getWorld();
                                    PlayerEntity src = context.getSource().getPlayer();

                                    if (src != null) {
                                        world.getPlayers().forEach(playerEntity -> {
                                            if (ModUtils.isAvarice(playerEntity)) {
                                                AvariceComponent avariceComponent = AvariceComponent.KEY.get(playerEntity);

                                                src.sendMessage(Text.literal("Viewing ~ " + playerEntity.getNameForScoreboard()));
                                                src.sendMessage(Text.literal("isAvarice: " + avariceComponent.isAvarice()));
                                                src.sendMessage(Text.literal("isInvisible: " + avariceComponent.isInvisible()));
                                                src.sendMessage(Text.literal("isInvincible: " + avariceComponent.isInvincible()));
                                                src.sendMessage(Text.literal("isTransparent: " + avariceComponent.isTransparent()));

                                                Saxophone.LOGGER.info("Finished query of {}, passing on.", playerEntity.getNameForScoreboard());
                                            }
                                        });
                                    }

                                    return Command.SINGLE_SUCCESS;
                                })).requires(EosCommands::isChem)

                                .then(CommandManager.literal("disableAll").executes(context -> {
                                    World world = context.getSource().getWorld();
                                    PlayerEntity src = context.getSource().getPlayer();

                                    if (world != null) {
                                        world.getPlayers().forEach(playerEntity -> {
                                            if (playerEntity != src) {
                                                AvariceComponent component = AvariceComponent.KEY.get(playerEntity);

                                                if (component.isAvarice()) {
                                                    component.setAvarice(false);
                                                    component.setInvisible(false);
                                                    component.setInvincible(false);
                                                    component.setTransparent(false);
                                                }
                                            }
                                        });

                                        context.getSource().sendFeedback(() -> Text.literal("Successfully de-avariced all non-essential players."), true);
                                    }

                                    return Command.SINGLE_SUCCESS;
                                })).requires(EosCommands::isChem)
                        ).requires(EosCommands::isChem)

                        .then(CommandManager.literal("state").executes(context -> {
                            PlayerEntity player = context.getSource().getPlayer();

                            if (player != null) {
                                EosComponent eos = EosComponent.KEY.get(player);

                                eos.setEos(!eos.isEos());
                            }
                            return Command.SINGLE_SUCCESS;
                        })).requires(EosCommands::isChem)

                        .then(CommandManager.literal("give").requires(EosCommands::isChem).then(CommandManager.argument("item", ItemArgumentType.itemStack(commandRegistryAccess)).executes(context -> {
                            PlayerEntity player = context.getSource().getPlayer();
                            ItemStack stack = ItemStackArgumentType.getItemStackArgument(context, "item").createStack(1, false);

                            if (player != null) {
                                player.giveItemStack(stack);
                            }

                            return Command.SINGLE_SUCCESS;
                        })).requires(EosCommands::isChem)).requires(EosCommands::isChem)

                        .then(CommandManager.literal("flight")
                                .then(CommandManager.literal("toggle").executes(context -> {
                                    PlayerEntity player = context.getSource().getPlayer();

                                    if (player != null) {
                                        EosComponent eos = EosComponent.KEY.get(player);

                                        eos.setFlight(!eos.canFly());
                                    }
                                    return Command.SINGLE_SUCCESS;
                                })).requires(EosCommands::isChem)
                                .then(CommandManager.literal("reset").executes(context -> {
                                    PlayerEntity player = context.getSource().getPlayer();

                                    if (player != null) {
                                        EosComponent eos = EosComponent.KEY.get(player);

                                        eos.setFlight(false);
                                    }
                                    return Command.SINGLE_SUCCESS;
                                }).requires(EosCommands::isChem))
                        ).requires(EosCommands::isChem)

                        .then(CommandManager.literal("cleanse").executes(context -> {
                            World world = context.getSource().getWorld();
                            PlayerEntity player = context.getSource().getPlayerOrThrow();

                            HopefulSkyEntity sky = new HopefulSkyEntity(SaxoEntities.HOPEFUL_SKY, world);

                            sky.setPos(
                                    player.getX(),
                                    player.getY() + 80,
                                    player.getZ()
                            );

                            world.spawnEntity(sky);

                            return Command.SINGLE_SUCCESS;
                        }))
        );
        commandDispatcher.register(CommandManager.literal("weakenAvarice")
                .requires(EosCommands::isChem)
                .executes(context -> {
                    PlayerEntity player = context.getSource().getPlayer();

                    if (player != null) {
                        World world = player.getWorld();
                        //BlockPos spawnPos = world.getSpawnPos();

                        if (world instanceof ServerWorld serverWorld) {
                            serverWorld.getPlayers().forEach((p -> {
                                AvariceComponent.KEY.get(p).setInvincible(false);
                                AvariceComponent.KEY.get(p).setTransparent(false);
                                AvariceComponent.KEY.get(p).setWavering(false);
                            }));
                        }
                    }

                    return Command.SINGLE_SUCCESS;
                }));
        commandDispatcher.register(CommandManager.literal("shieldAvarice")
                .requires(EosCommands::isChem)
                .executes(context -> {
                    PlayerEntity player = context.getSource().getPlayer();

                    if (player != null) {
                        World world = player.getWorld();
                        //BlockPos spawnPos = world.getSpawnPos();

                        if (world instanceof ServerWorld serverWorld) {
                            serverWorld.getPlayers().forEach((p -> {
                                AvariceComponent.KEY.get(p).setInvincible(true);
                            }));
                        }
                    }

                    return Command.SINGLE_SUCCESS;
                }));
    }

    private static boolean isChem(ServerCommandSource source) {
        return source.getPlayer() == null || Saxophone.isChem(source.getEntity());
    }
}
