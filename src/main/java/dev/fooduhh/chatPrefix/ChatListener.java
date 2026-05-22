package dev.fooduhh.chatPrefix;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

public class ChatListener implements Listener {
    private final ChatPrefix plugin;

    public ChatListener(ChatPrefix plugin) {
        this.plugin = plugin;
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onChat(@NonNull AsyncChatEvent event) {
        if (event.isCancelled()) return;

        UUID uuid = event.getPlayer().getUniqueId();

        String prefix = plugin.getConfig().getString("prefix." + uuid);

        if (prefix == null || prefix.isEmpty()) return;

        Component prefixedMessage = Component.text(prefix);

        event.message(prefixedMessage.append(event.message()));
    }
}
