package dev.fooduhh.chatPrefix;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ChatPrefixCommand {
    private final ChatPrefix plugin;

    public ChatPrefixCommand(ChatPrefix plugin) {
        this.plugin = plugin;
    }

    public LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("chatprefix")
                .then(Commands.literal("set")
                        .then(Commands.argument("prefix", StringArgumentType.greedyString())
                                .executes(ctx -> {
                                    String prefix = StringArgumentType.getString(ctx, "prefix");

                                    CommandSender sender = ctx.getSource().getSender();
                                    if (!(sender instanceof Player plr)) return 0;

                                    set(plr, prefix);
                                    return Command.SINGLE_SUCCESS;
                                })))
                .then(Commands.literal("unset")
                        .executes(ctx -> {
                            CommandSender sender = ctx.getSource().getSender();
                            if (!(sender instanceof Player plr)) return 0;

                            set(plr, "");
                            return Command.SINGLE_SUCCESS;
                        }))
                .build();
    }

    private void set(Player plr, String prefix) {
        UUID uuid = plr.getUniqueId();

        if (prefix.isEmpty()) {
            plugin.getConfig().set("prefix." + uuid, null);
            plr.sendMessage("Prefix removed.");
        }
        else {
            plugin.getConfig().set("prefix." + uuid, prefix);
            plr.sendMessage("Prefix set to " + prefix + ".");
        }

        plugin.saveConfig();
        plugin.reloadConfig();
    }
}
