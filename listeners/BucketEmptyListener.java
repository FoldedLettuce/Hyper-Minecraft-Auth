package me.aaron.authorize.listeners;

import me.aaron.authorize.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public class BucketEmptyListener implements Listener {
    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent e) {
        if (!Main.authorized.contains(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
        }
    }
}
