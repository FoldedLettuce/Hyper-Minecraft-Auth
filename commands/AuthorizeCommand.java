package me.aaron.authorize.commands;

import me.aaron.authorize.Main;
import me.aaron.authorize.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;

public class AuthorizeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cYou are already authorized!");
            return false;
        }
        Player p = (Player) sender;

        if (Main.authorized.contains(p.getUniqueId())) {
            p.sendMessage("§cYou are already authorized!");
            return false;
        }
        if (!(args.length == 1)) {
            p.sendMessage("§cUsage: /authorize <Key>");
            return false;
        }
        if (Main.usedKeys.contains(args[0].toUpperCase())) {
            Config.set("maps." + Main.player.get(p.getUniqueId()), null);
            Config.set("maps." + p.getUniqueId(), null);
            Main.authorized.remove(Main.key.get(args[0].toUpperCase()));
            Main.usedKeys.remove(Main.player.get(p.getUniqueId()));
            Main.key.remove(Main.player.get(p.getUniqueId()));
            Main.player.remove(p.getUniqueId());
             try {
                 Bukkit.getPlayer(Main.key.get(args[0].toUpperCase())).kickPlayer("§cSomeone else connected to the server, using your Key.");
             } catch (Exception ignored) { }
        }
        p.sendMessage("§7Checking key " + args[0].toUpperCase() + "...");
        Bukkit.getScheduler().runTaskAsynchronously(JavaPlugin.getPlugin(Main.class), () -> {
            try {
                URL url = new URL("https://api.hyper.co/v6/licenses/" + args[0].toUpperCase());
                HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Authorization", Main.API_KEY);
                if (con.getResponseCode() != 200) {
                    p.sendMessage("§cInvalid Key! Please check for spelling mistakes!");
                } else {
                    Main.authorized.add(p.getUniqueId());
                    Main.usedKeys.add(args[0].toUpperCase());
                    Main.key.put(args[0].toUpperCase(), p.getUniqueId());
                    Main.player.put(p.getUniqueId(), args[0].toUpperCase());
                    p.setWalkSpeed((float) 0.2);
                    p.setCanPickupItems(true);
                    p.sendMessage("§aSuccessfully authorized!");
                }
                con.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return false;
    }
}
