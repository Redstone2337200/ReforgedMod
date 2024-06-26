package com.redstone233.spawn.reforged.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.redstone233.spawn.reforged.ModGlobalInfo;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.ServerTickManager;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class TrsmCommand {
    public static LiteralArgumentBuilder<ServerCommandSource> register() {
        return CommandManager.literal("trsm")
            .requires(src -> src.hasPermissionLevel(2))
                .then(CommandManager.literal("info")
                    .then(CommandManager.argument("page", IntegerArgumentType.integer())
                    .executes(run -> getInfoMod(run.getSource(),
                        IntegerArgumentType.getInteger(run, "page"))
                )
            )
        )
            .requires(src -> src.hasPermissionLevel(2))
                .then(CommandManager.literal("depends")
                    .then(CommandManager.literal("fabric")
                    .executes(run -> getDependsFabric(run.getSource()))
            ).then(CommandManager.literal("minecraft")
                .executes(run -> getDependsMinecraft(run.getSource()))
            )
        );
    }

    private static int getDependsFabric(ServerCommandSource source) throws CommandSyntaxException {
        PlayerEntity player = source.getPlayer();
        ServerTickManager serverTickManager = source.getServer().getTickManager();
        boolean bl = serverTickManager.stopStepping();
        if (!bl) {
            player.sendMessage(Text.translatable("commands.trsm.fabric.version",
                ModGlobalInfo.TextGlobal.versionAr[1]));
        } else {
            source.sendError(Text.translatable("commands.trsm.fail"));
            return 0;
        }
        /*if (!source.getServer().isDedicated()){
            throw ModCommands.TRSM_COMMAND_EXCEPTION_TYPE.create();
        }*/
        source.sendFeedback(() -> Text.translatable("commands.reforged.math.success"), true);
        return 1;
    }

    private static int getDependsMinecraft(ServerCommandSource source) throws CommandSyntaxException {
        PlayerEntity player = source.getPlayer();
        ServerTickManager serverTickManager = source.getServer().getTickManager();
        boolean bl = serverTickManager.stopStepping();
        if (!bl) {
            player.sendMessage(Text.translatable("commands.trsm.minecraft.version",
                ModGlobalInfo.TextGlobal.versionAr[0]));
        } else {
            source.sendError(Text.translatable("commands.trsm.fail"));
            return 0;
        }
        /*if (!source.getServer().isDedicated()){
            throw ModCommands.TRSM_COMMAND_WORONG_BE_INTEGRATED.create();
        }*/
        source.sendFeedback(() -> Text.translatable("commands.reforged.math.success"), true);
        return 1;   
    }

    private static int getInfoMod(ServerCommandSource source,int page) throws CommandSyntaxException {
        PlayerEntity player = source.getPlayer();
        ServerTickManager serverTickManager = source.getServer().getTickManager();
        boolean bl = serverTickManager.stopStepping();
        if (!bl) {
                if (page == 1) {
                    for (String textArrys : ModGlobalInfo.TextGlobal.textArray) {
                        player.sendMessage(Text.translatable("commands.trsm.info.mod",textArrys));
                    }
                } else if (page == 2) {
                    for (String versionArs : ModGlobalInfo.TextGlobal.versionAr) {
                        player.sendMessage(Text.translatable("commands.trsm.info.version",versionArs));
                    }
                }
                if (page < 1) {
                    player.sendMessage(Text.translatable("commands.trsm.info.page.small",page));
            } else if (page >= 3) {
                player.sendMessage(Text.translatable("commands.trsm.info.page.biggist",page));     
            } else {
                source.sendError(Text.translatable("commands.reforged.math.fail"));
            }
        }
        /*if (!source.getServer().isDedicated()){
            throw ModCommands.TRSM_COMMAND_WORONG_BE_INTEGRATED.create();
        }*/
        source.sendFeedback(() -> Text.translatable("commands.reforged.math.success"), true);
        return Command.SINGLE_SUCCESS;
    }

}
