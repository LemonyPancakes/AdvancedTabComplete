package me.swagpancakes.advancedtabcomplete;

import me.swagpancakes.advancedtabcomplete.Commands.MainCommand;
import me.swagpancakes.advancedtabcomplete.Config.ConfigHandler;
import me.swagpancakes.advancedtabcomplete.Listeners.AsyncTabComplete;
import me.swagpancakes.advancedtabcomplete.Listeners.NonAsyncTabComplete;
import me.swagpancakes.advancedtabcomplete.Metrics.Metrics;
import me.swagpancakes.advancedtabcomplete.Utils.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public final class AdvancedTabComplete extends JavaPlugin {

    public static Boolean update, checkUpdate;
    public static String latestUpdate;
    public static int resourceID = 91322;
    public static String downloadLink = "https://www.spigotmc.org/resources/" + resourceID + "/";
    public String version = getDescription().getVersion();

    public List<String> cache;

    public ConfigHandler configHandler = new ConfigHandler(this);

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[AdvancedTabComplete] Plugin has been enabled!");

        cache = new ArrayList<>();

        loadFiles();
        registerCommands();
        registerListeners();
        startMetrics();
        checkUpdates();

    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[AdvancedTabComplete] Plugin has been disabled!");
    }

    public void registerCommands(){
        new MainCommand(this);
    }

    public void registerListeners(){
        if (configHandler.getSettings().getBoolean("settings.async")){
            new NonAsyncTabComplete(this);
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[AdvancedTabComplete] Running the plugin on " + ChatColor.YELLOW + "ASYNC MODE");
        }else{
            new NonAsyncTabComplete(this);
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[AdvancedTabComplete] Running the plugin on " + ChatColor.GOLD + "NON ASYNC MODE");
        }
    }

    @SuppressWarnings("unused")
    public void startMetrics(){
        Metrics metrics = new Metrics(this, 11051);
    }

    public void checkUpdates(){
        UpdateChecker updater = new UpdateChecker(this, resourceID);

        new BukkitRunnable(){

            @Override
            public void run(){
                if (configHandler.getConfig().contains("updates.notifications")) {
                    checkUpdate = configHandler.getConfig().getBoolean("updates.notifications");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[AdvancedTabComplete] Checking for updates...");
                }
            }
        }.runTaskLater(this, 20 * 10);

            try{
                if (updater.checkForUpdates()){
                    new BukkitRunnable(){

                        @Override
                        public void run(){
                            if(configHandler.getConfig().contains("updates.notifications")) {
                                checkUpdate = configHandler.getConfig().getBoolean("updates.notifications");
                            } else {
                                checkUpdate = true;
                            }
                            if(checkUpdate) {
                                Bukkit.getConsoleSender().sendMessage("");
                                Bukkit.getConsoleSender().sendMessage("§6[AdvancedTabComplete] A new update is available!");
                                Bukkit.getConsoleSender().sendMessage("§6[AdvancedTabComplete] Running on §c" + version + " §6while latest is §a" + latestUpdate + "§6!");
                                Bukkit.getConsoleSender().sendMessage("§6[AdvancedTabComplete] §e§n" + downloadLink);
                                Bukkit.getConsoleSender().sendMessage("");
                            } else{
                                Bukkit.getConsoleSender().sendMessage("§a[AdvancedTabComplete] No updates found.");
                            }
                        }
                    }.runTaskLater(this, 20 * 15);
                    update = true;
                    latestUpdate = updater.getLatestVersion();
                }else {
                    new BukkitRunnable(){

                        @Override
                        public void run(){
                            if (configHandler.getConfig().contains("updates.notifications")) {
                                checkUpdate = configHandler.getConfig().getBoolean("updates.notifications");
                                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[AdvancedTabComplete] No updates found.");
                            }
                        }
                    }.runTaskLater(this, 20 * 15);
                    update = false;
                }
            } catch (Exception e) {
                update = false;
            }
    }

    public void loadFiles() {
        configHandler.setup();
        configHandler.saveFiles();
    }
}
