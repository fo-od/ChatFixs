package dev.fooduhh.chatPrefix;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChatPrefix extends JavaPlugin {
    private final ChatPrefixCommand command = new ChatPrefixCommand(this);
    private final ChatListener listener = new ChatListener(this);

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(listener, this);

        // register plugin command
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> commands.registrar().register(command.create()));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
