package dev.fooduhh.chatFixs;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ChatPrefixCommand {
    private final ChatFixs plugin;

    public ChatPrefixCommand(ChatFixs plugin) {
        this.plugin = plugin;
    }

    public LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("chatfixs")
                .then(Commands.literal("set")
                        .then(Commands.literal("prefix")
                                .then(Commands.argument("prefix", StringArgumentType.greedyString())
                                        .executes(ctx -> {
                                            String prefix = StringArgumentType.getString(ctx, "prefix");

                                            CommandSender sender = ctx.getSource().getSender();
                                            if (!(sender instanceof Player plr)) return 0;

                                            set(plr, true, prefix);
                                            return Command.SINGLE_SUCCESS;
                                        })))
                        .then(Commands.literal("suffix")
                                .then(Commands.argument("suffix", StringArgumentType.greedyString())
                                        .executes(ctx -> {
                                            String suffix = StringArgumentType.getString(ctx, "suffix");

                                            CommandSender sender = ctx.getSource().getSender();
                                            if (!(sender instanceof Player plr)) return 0;

                                            set(plr, false, suffix);
                                            return Command.SINGLE_SUCCESS;
                                        }))))
                .then(Commands.literal("unset")
                        .then(Commands.literal("prefix")
                                .executes(ctx -> {
                                    CommandSender sender = ctx.getSource().getSender();
                                    if (!(sender instanceof Player plr)) return 0;

                                    set(plr, true, null);
                                    return Command.SINGLE_SUCCESS;
                                }))
                        .then(Commands.literal("suffix")
                                .executes(ctx -> {
                                    CommandSender sender = ctx.getSource().getSender();
                                    if (!(sender instanceof Player plr)) return 0;

                                    set(plr, false, null);
                                    return Command.SINGLE_SUCCESS;
                                })
                        ))
                .build();
    }

    private void set(Player plr, boolean isPrefix, String fix) {
        UUID uuid = plr.getUniqueId();

        String prefix = isPrefix ? "pre" : "suf";

        if (fix == null || fix.isEmpty()) {
            plugin.getData().set(prefix + "fixes." + uuid, null);
            plr.sendMessage(prefix + "fix removed.");
        } else {
            plugin.getData().set(prefix + "fixes." + uuid, fix);
            plr.sendMessage(prefix + "fix set to '" + fix + "'");
        }

        // save data async because its blocking
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, plugin::saveData);
    }
}
