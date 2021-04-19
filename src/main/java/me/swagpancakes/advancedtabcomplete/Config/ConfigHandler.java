package me.swagpancakes.advancedtabcomplete.Config;

import me.swagpancakes.advancedtabcomplete.AdvancedTabComplete;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigHandler {

    public AdvancedTabComplete plugin;

    public ConfigHandler(AdvancedTabComplete plugin){
        this.plugin = plugin;
    }

    public FileConfiguration config;
    public FileConfiguration settings;
    public File configfile;
    public File settingsfile;

    public FileConfiguration getConfig(){
        return config;
    }

    public FileConfiguration getSettings(){
        return settings;
    }

    public void setup(){
        if (!plugin.getDataFolder().exists()){
            plugin.getDataFolder().mkdir();
        }

        configfile = new File(plugin.getDataFolder(), "config.yml");
        settingsfile = new File(plugin.getDataFolder(), "settings.yml");

        if (!configfile.exists()){
            plugin.getConfig().options().copyDefaults(true);
            plugin.saveDefaultConfig();
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[AdvancedTabComplete] The file config.yml has been created");
        }

        if (!settingsfile.exists()){
            plugin.saveResource("settings.yml", false);
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[AdvancedTabComplete] The file settings.yml has been created");
        }

        config = YamlConfiguration.loadConfiguration(configfile);
        settings = YamlConfiguration.loadConfiguration(settingsfile);
    }

    public void saveFiles(){
        try {
            settings.save(settingsfile);
        } catch (IOException event){
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[AdvancedTabComplete] Could not save the settings.yml file");
        }

        plugin.saveDefaultConfig();
    }

    //public void updateFiles(){
}
