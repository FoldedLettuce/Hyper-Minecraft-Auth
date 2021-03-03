package me.aaron.authorize.commands;

import me.aaron.authorize.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BypassauthCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) || sender.isOp()) {
            if (args.length == 1) {
                try {
                    Player p = Bukkit.getPlayerExact(args[0]);
                    if (Main.authorized.contains(p.getUniqueId())) {
                        sender.sendMessage("§7This user is already authorized.");
                        return false;
                    }
                    Main.authorized.add(p.getUniqueId());
                    Main.usedKeys.add(p.getUniqueId().toString());
                    Main.key.put(p.getUniqueId().toString(), p.getUniqueId());
                    Main.player.put(p.getUniqueId(), p.getUniqueId().toString());
                    p.setWalkSpeed((float) 0.2);
                    p.setCanPickupItems(true);
                    sender.sendMessage("§7The player §9" + p.getName() + " §7has been authorized.");
                    p.sendMessage("§aYour authorization has been bypassed by an admin.");
                    return true;
                } catch (Exception e) {
                    sender.sendMessage("§cThe player §9" + args[0] + " §cis not online or does not exist!");
                    return false;
                }
            } else {
                sender.sendMessage("§cUsage: /bypassauth <Username>");
                return false;
            }
        } else {
            sender.sendMessage("§cYou do not have permission to use this command!");
            return false;
        }
    }
}
