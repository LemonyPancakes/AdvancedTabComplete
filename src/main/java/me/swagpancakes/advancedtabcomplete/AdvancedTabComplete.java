package me.swagpancakes.advancedtabcomplete;

import me.swagpancakes.advancedtabcomplete.Commands.MainCommand;
import me.swagpancakes.advancedtabcomplete.Config.ConfigHandler;
import me.swagpancakes.advancedtabcomplete.Listeners.TabComplete;
import me.swagpancakes.advancedtabcomplete.Metrics.Metrics;
import me.swagpancakes.advancedtabcomplete.Utils.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class AdvancedTabComplete extends JavaPlugin {

    public static Boolean update, checkUpdate;
    public static String latestUpdate;
    public static int resourceID = 91322;
    public static String downloadLink = "https://www.spigotmc.org/resources/" + resourceID + "/";
    public String version = getDescription().getVersion();

    public ConfigHandler configHandler = new ConfigHandler(this);

    public YamlConfiguration config = new YamlConfiguration();

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[AdvancedTabComplete] Plugin has been enabled!");

        registerCommands();
        registerListeners();
        startMetrics();
        checkUpdates();
        loadConfig();

    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[AdvancedTabComplete] Plugin has been disabled!");
    }

    public void registerCommands(){
        new MainCommand(this);
    }

    public void registerListeners(){
        new TabComplete(this);
    }

    @SuppressWarnings("unused")
    public void startMetrics(){
        Metrics metrics = new Metrics(this, 11051);
    }

    public void checkUpdates(){
        UpdateChecker updater = new UpdateChecker(this, resourceID);
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
                            }
                        }
                    }.runTaskLater(this, 20 * 5);
                    update = true;
                    latestUpdate = updater.getLatestVersion();
                }else {
                    update = false;
                }
            } catch (Exception e) {
                update = false;
            }
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

    }
}
