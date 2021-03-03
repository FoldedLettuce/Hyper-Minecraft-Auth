package me.aaron.authorize;

import me.aaron.authorize.commands.AuthorizeCommand;
import me.aaron.authorize.commands.BypassauthCommand;
import me.aaron.authorize.commands.UnauthorizeCommand;
import me.aaron.authorize.listeners.*;
import me.aaron.authorize.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public final class Main extends JavaPlugin {

    public static HashMap<String, UUID> key = new HashMap<>();
    public static HashMap<UUID, String> player = new HashMap<>();
    public static ArrayList<UUID> authorized = new ArrayList<>();
    public static ArrayList<String> usedKeys = new ArrayList<>();
    public static String API_KEY;

    @Override
    public void onEnable() {
        Config config = new Config();
        config.loadConfig();
        this.getCommand("authorize").setExecutor(new AuthorizeCommand());
        this.getCommand("auth").setExecutor(new AuthorizeCommand());
        this.getCommand("bypassauth").setExecutor(new BypassauthCommand());
        this.getCommand("unauthorize").setExecutor(new UnauthorizeCommand());
        this.getCommand("unauth").setExecutor(new UnauthorizeCommand());

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new MoveListener(), this);
        pm.registerEvents(new BlockBreakListener(), this);
        pm.registerEvents(new BlockPlaceListener(), this);
        pm.registerEvents(new BucketFillListener(), this);
        pm.registerEvents(new BucketEmptyListener(), this);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (!authorized.contains(pl.getUniqueId())) {
                    pl.sendTitle("§cYou aren't authorized", "§cUse §9/authorize <Key> §cto authorize!", 0, 20, 10);
                    pl.setWalkSpeed(0);
                    pl.setCanPickupItems(false);
                }
            }
        }, 0, 20);
    }

    @Override
    public void onDisable() {
        Config config = new Config();
        config.saveConfig();
    }
}
