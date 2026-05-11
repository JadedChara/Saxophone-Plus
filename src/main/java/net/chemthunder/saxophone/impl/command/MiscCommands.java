package net.chemthunder.saxophone.impl.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.chemthunder.saxophone.impl.Saxophone;
import net.chemthunder.saxophone.impl.cca.deity.AvariceComponent;
import net.chemthunder.saxophone.impl.cca.entity.ArchitectComponent;
import net.chemthunder.saxophone.impl.util.ModUtils;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

public class MiscCommands implements CommandRegistrationCallback {
    public void register(CommandDispatcher<ServerCommandSource> commandDispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        commandDispatcher.register(CommandManager.literal("spawn").executes(context -> {
            PlayerEntity player = context.getSource().getPlayer();

            if (player != null) {
                World world = player.getWorld();
                BlockPos spawnPos = world.getSpawnPos();

                if (world instanceof ServerWorld serverWorld) {
                    Vec3d pos = new Vec3d(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());

                    if (!ModUtils.isInAsphodel(player)) {
                        player.teleportTo(new TeleportTarget(serverWorld, pos, player.getVelocity(), player.getYaw(), player.getPitch(), TeleportTarget.NO_OP));
                    }
                }
            }

            return Command.SINGLE_SUCCESS;
        }));



        //COSMETICS FOR CONTRIBUTORS
        commandDispatcher.register(CommandManager.literal("cosmetics")
                .requires(MiscCommands::isContributor)
                .then(CommandManager.literal("toggle")
                        .then(
                                CommandManager.literal("flair")
                                        .executes((context)->{
                                            PlayerEntity player = context.getSource().getPlayer();
                                            if (player != null) {
                                                ArchitectComponent
                                                        .KEY
                                                        .get(player)
                                                        .setFlair(!ArchitectComponent.KEY.get(player).hasFlair());
                                            }
                                            context.getSource().sendFeedback(() -> Text.literal(
                                                    "Set Username Flair to " + ArchitectComponent.KEY.get(player).hasFlair()
                                            ), false);
                                            return Command.SINGLE_SUCCESS;
                                        })
                        )
                        .then(
                                CommandManager.literal("fx")
                                        .executes((context)->{
                                            PlayerEntity player = context.getSource().getPlayer();
                                            if (player != null) {
                                                ArchitectComponent
                                                        .KEY
                                                        .get(player)
                                                        .setFX(!ArchitectComponent.KEY.get(player).hasFX());
                                            }
                                            context.getSource().sendFeedback(() -> Text.literal(
                                                    "Set Visual Effects to " + ArchitectComponent.KEY.get(player).hasFX()
                                            ), false);
                                            return Command.SINGLE_SUCCESS;
                                        })
                        )
                        .then(
                                CommandManager.literal("wavering")
                                        .executes((context)->{
                                            PlayerEntity player = context.getSource().getPlayer();
                                            if (player != null) {
                                                ArchitectComponent
                                                        .KEY
                                                        .get(player)
                                                        .setWavering(!ArchitectComponent.KEY.get(player).hasWavering());
                                            }
                                            context.getSource().sendFeedback(() -> Text.literal(
                                                    "Set Wavering Name Effect to " + ArchitectComponent.KEY.get(player).hasWavering()
                                            ), false);
                                            return Command.SINGLE_SUCCESS;
                                        })
                        )
                )
        );
    }
    private static boolean isContributor(ServerCommandSource source) {
        return (
                source.getPlayer() == null)
                || (Saxophone.isChem(source.getEntity())
                || (Saxophone.isNightstrike(source.getEntity()))
                || (Saxophone.isScarlet(source.getEntity()))
        );
    }
}
