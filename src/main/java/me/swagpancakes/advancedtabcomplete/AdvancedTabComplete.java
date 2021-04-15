package me.swagpancakes.advancedtabcomplete;

import me.swagpancakes.advancedtabcomplete.Commands.MainCommand;
import me.swagpancakes.advancedtabcomplete.Listeners.TabComplete;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class AdvancedTabComplete extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new TabComplete(), this);
        new MainCommand(this);
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[AdvancedTabComplete] Plugin has been enabled!");
        loadConfig();

    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[AdvancedTabComplete] Plugin has been disabled!");
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

    }
}
