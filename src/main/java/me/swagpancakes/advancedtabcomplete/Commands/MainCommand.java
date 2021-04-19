package me.swagpancakes.advancedtabcomplete.Commands;

import me.swagpancakes.advancedtabcomplete.AdvancedTabComplete;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MainCommand implements CommandExecutor {

    private AdvancedTabComplete plugin;

    public MainCommand(AdvancedTabComplete plugin){
        plugin.getCommand("atc").setExecutor(this);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("advancedtabcomplete.admin")) {
            if (args.length == 1) {
                switch (args[0].toUpperCase()) {
                    case "RELOAD":
                        plugin.configHandler.saveFiles();
                        sender.sendMessage(plugin.getConfig().getString("lang.reloaded").replaceAll("&", "§"));
                        return false;
                }
            }
            sender.sendMessage(plugin.getConfig().getString("lang.usage").replaceAll("&", "§"));
        }else{
            sender.sendMessage(plugin.getConfig().getString("lang.no-permission").replaceAll("&", "§"));
        }
        return false;
    }
}
