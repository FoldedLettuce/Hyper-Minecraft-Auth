package me.aaron.authorize.commands;

import me.aaron.authorize.Main;
import me.aaron.authorize.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnauthorizeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) || sender.isOp()) {
            if (args.length == 1) {
                try {
                    Player p = Bukkit.getPlayerExact(args[0]);
                    if (!Main.authorized.contains(p.getUniqueId())) {
                        sender.sendMessage("§7This user isn't authorized, so you cant unauthorize them.");
                        return false;
                    }
                    Config.set("maps." + Main.player.get(p.getUniqueId()), null);
                    Config.set("maps." + p.getUniqueId(), null);
                    Main.authorized.remove(p.getUniqueId());
                    Main.usedKeys.remove(Main.player.get(p.getUniqueId()));
                    Main.key.remove(Main.player.get(p.getUniqueId()));
                    Main.player.remove(p.getUniqueId());
                    sender.sendMessage("§7The player §9" + p.getName() + " §7has been unauthorized.");
                    p.sendMessage("§cYou have been unauthorized by an admin.");
                    return true;
                } catch (Exception e) {
                    sender.sendMessage("§cThe player §9" + args[0] + " §cis not online or does not exist!");
                    return false;
                }
            } else if (args.length == 0) {
                Player p = (Player) sender;
                Config.set("maps." + Main.player.get(p.getUniqueId()), null);
                Config.set("maps." + p.getUniqueId(), null);
                Main.authorized.remove(p.getUniqueId());
                Main.usedKeys.remove(Main.player.get(p.getUniqueId()));
                Main.key.remove(Main.player.get(p.getUniqueId()));
                Main.player.remove(p.getUniqueId());
                p.sendMessage("§aYou have unauthorized yourself.");
                return true;
            } else {
                sender.sendMessage("§cUsage: /unauthorize <Username>");
                return false;
            }
        } else {
            if (args.length == 0) {
                Player p = (Player) sender;
                Config.set("maps." + Main.player.get(p.getUniqueId()), null);
                Config.set("maps." + p.getUniqueId(), null);
                Main.authorized.remove(p.getUniqueId());
                Main.usedKeys.remove(Main.player.get(p.getUniqueId()));
                Main.key.remove(Main.player.get(p.getUniqueId()));
                Main.player.remove(p.getUniqueId());
                p.sendMessage("§aYou have unauthorized yourself.");
                return true;
            } else {
                sender.sendMessage("§cPlease do not put any arguments behind this command!");
            }
            return false;
        }
    }
}
