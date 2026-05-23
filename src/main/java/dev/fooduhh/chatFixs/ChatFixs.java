package dev.fooduhh.chatFixs;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class ChatFixs extends JavaPlugin {
    private final ChatPrefixCommand command = new ChatPrefixCommand(this);
    private final ChatListener listener = new ChatListener(this);
    private final File dataFile = new File(getDataFolder(), "fixs.yml");
    private final YamlConfiguration fixsConfig = YamlConfiguration.loadConfiguration(dataFile);

    @Override
    public void onEnable() {
        saveResource("fixs.yml", false);

        Bukkit.getPluginManager().registerEvents(listener, this);

        // register plugin command
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> commands.registrar().register(command.create()));
    }

    @Override
    public void onDisable() {
        // save for good measure
        saveData();
    }

    public YamlConfiguration getData() {
        return fixsConfig;
    }

    public void saveData() {
        try {
            fixsConfig.save(dataFile);
        } catch (IOException e) {
            this.getLogger().severe("Could not save fixs!");
        }
    }
}
