package me.swagpancakes.advancedtabcomplete.Listeners;

import me.swagpancakes.advancedtabcomplete.AdvancedTabComplete;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class AsyncTabComplete implements Listener {

    private AdvancedTabComplete plugin;

    public AsyncTabComplete(AdvancedTabComplete plugin){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onCommandSendEvent(PlayerCommandSendEvent event) {
        new BukkitRunnable() {

            @Override
            public void run() {
                Player player = event.getPlayer();
                plugin.cache.clear();

                if (!plugin.getConfig().getBoolean("bypass.enable-bypass")) {
                    event.getCommands().clear();
                    plugin.getConfig().getConfigurationSection("groups").getKeys(false).forEach(key -> {
                        if (player.hasPermission(plugin.getConfig().getString("groups." + key + ".permission"))) {
                            if (plugin.getConfig().getString("groups." + key + ".mode").equalsIgnoreCase("whitelist")) {
                                event.getCommands().clear();
                                plugin.cache.addAll(plugin.getConfig().getStringList("groups." + key + ".commands"));
                                event.getCommands().addAll(plugin.cache);
                            } else if (plugin.getConfig().getString("groups." + key + ".mode").equalsIgnoreCase("blacklist")) {
                                event.getCommands().clear();
                                plugin.cache.addAll(plugin.getConfig().getStringList("groups." + key + ".commands"));
                                event.getCommands().removeAll(plugin.cache);
                            }
                        }
                    });
                } else {
                    if (!player.hasPermission(plugin.getConfig().getString("bypass.bypass-permission"))) {
                        event.getCommands().clear();
                        plugin.getConfig().getConfigurationSection("groups").getKeys(false).forEach(key -> {
                            if (player.hasPermission(plugin.getConfig().getString("groups." + key + ".permission"))) {
                                if (plugin.getConfig().getString("groups." + key + ".mode").equalsIgnoreCase("whitelist")) {
                                    event.getCommands().clear();
                                    plugin.cache.addAll(plugin.getConfig().getStringList("groups." + key + ".commands"));
                                    event.getCommands().addAll(plugin.cache);
                                } else if (plugin.getConfig().getString("groups." + key + ".mode").equalsIgnoreCase("blacklist")) {
                                    event.getCommands().clear();
                                    plugin.cache.addAll(plugin.getConfig().getStringList("groups." + key + ".commands"));
                                    event.getCommands().removeAll(plugin.cache);
                                }
                            }
                        });
                    }
                }
            }
        }.runTaskAsynchronously(this.plugin);
    }
}
