package me.swagpancakes.advancedtabcomplete.Listeners;

import me.swagpancakes.advancedtabcomplete.AdvancedTabComplete;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

public class TabComplete implements Listener {
    AdvancedTabComplete plugin = AdvancedTabComplete.getPlugin(AdvancedTabComplete.class);

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onCommandSendEvent(PlayerCommandSendEvent event) {
        Player player = event.getPlayer();

        if (!plugin.getConfig().getBoolean("bypass.enable-bypass")){
            event.getCommands().clear();
            plugin.getConfig().getConfigurationSection("groups").getKeys(false).forEach(key ->{
                if (player.hasPermission(plugin.getConfig().getString("groups." + key + ".permission"))){
                    if (plugin.getConfig().getString("groups." + key + ".mode").equalsIgnoreCase("whitelist")){
                        event.getCommands().clear();
                        event.getCommands().addAll(plugin.getConfig().getStringList("groups." + key + ".commands"));
                    }else if (plugin.getConfig().getString("groups." + key + ".mode").equalsIgnoreCase("blacklist")){
                        event.getCommands().clear();
                        event.getCommands().removeAll(plugin.getConfig().getStringList("groups." + key + ".commands"));
                    }
                }
            });
        }else{
            if (!player.hasPermission(plugin.getConfig().getString("bypass.bypass-permission"))){
                event.getCommands().clear();
                plugin.getConfig().getConfigurationSection("groups").getKeys(false).forEach(key -> {
                    if (player.hasPermission(plugin.getConfig().getString("groups." + key + ".permission"))) {
                        if (plugin.getConfig().getString("groups." + key + ".mode").equalsIgnoreCase("whitelist")){
                            event.getCommands().clear();
                            event.getCommands().addAll(plugin.getConfig().getStringList("groups." + key + ".commands"));
                        }else if (plugin.getConfig().getString("groups." + key + ".mode").equalsIgnoreCase("blacklist")){
                            event.getCommands().clear();
                            event.getCommands().removeAll(plugin.getConfig().getStringList("groups." + key + ".commands"));
                        }
                    }
                });
            }
        }

    }

}
