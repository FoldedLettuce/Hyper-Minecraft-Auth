package me.aaron.authorize.listeners;

import me.aaron.authorize.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketFillEvent;

public class BucketFillListener implements Listener {
    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent e) {
        if (!Main.authorized.contains(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
        }
    }
}
