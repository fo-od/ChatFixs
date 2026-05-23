package dev.fooduhh.chatFixs;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

public class ChatListener implements Listener {
    private final ChatFixs plugin;

    public ChatListener(ChatFixs plugin) {
        this.plugin = plugin;
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onChat(@NonNull AsyncChatEvent event) {
        if (event.isCancelled()) return;
        Component fixedMessage = Component.empty();

        UUID uuid = event.getPlayer().getUniqueId();

        // handle prefix
        String prefix = plugin.getData().getString("prefixes." + uuid);
        if (prefix != null && !prefix.isEmpty()) {
            fixedMessage = fixedMessage.append(Component.text(prefix));
        }

        fixedMessage = fixedMessage.append(event.message());

        // handle suffix
        String suffix = plugin.getData().getString("suffixes." + uuid);
        if (suffix != null && !suffix.isEmpty()) {
            fixedMessage = fixedMessage.append(Component.text(suffix));
        }

        event.message(fixedMessage);
    }
}
