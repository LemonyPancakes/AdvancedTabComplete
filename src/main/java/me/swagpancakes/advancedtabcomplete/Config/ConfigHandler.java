package me.swagpancakes.advancedtabcomplete.Config;

import me.swagpancakes.advancedtabcomplete.AdvancedTabComplete;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigHandler {

    public AdvancedTabComplete plugin;

    public ConfigHandler(AdvancedTabComplete plugin){
        this.plugin = plugin;
    }

    public YamlConfiguration getConfig(){
        return plugin.config;
    }
}
