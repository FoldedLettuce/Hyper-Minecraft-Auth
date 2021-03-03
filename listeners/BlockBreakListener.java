package me.aaron.authorize.listeners;

import me.aaron.authorize.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (!Main.authorized.contains(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
        }
    }
}
