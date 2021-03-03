package me.aaron.authorize.utils;

import me.aaron.authorize.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class Config {
    private static File file;
    private static YamlConfiguration config;

    public Config() {
        File dir = new File("./plugins/MatalabsMinecraftAuth");
        if (!dir.exists()) {
            dir.mkdir();
        }

        file = new File(dir, "config.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    private ArrayList getArrayList(String path) {
        return (ArrayList) config.getList(path);
    }

    public static void set(String path, Object value) {
        config.set(path, value);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            ArrayList<String> UUIDStrings = new ArrayList<>();
            for (UUID uuid : Main.authorized) {
                UUIDStrings.add(uuid.toString());
            }
            config.set("lists.authorizedplayers", UUIDStrings);
            config.set("lists.usedkeys", Main.usedKeys);
            for (String string : Main.usedKeys) {
                config.set("maps." + string, Main.key.get(string).toString());
            }
            for (UUID uuid : Main.authorized) {
                config.set("maps." + uuid.toString(), Main.player.get(uuid));
            }
            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            resetConfig();
        }
    }

    public void loadConfig() {
        try {
            if (config.contains("API_KEY") && !(config.getString("API_KEY").equals("Insert your API-Key here!"))) {
                Main.API_KEY = config.getString("API_KEY");
            } else {
                config.set("API_KEY", "Insert your API-Key here!");
                config.save(file);
                Bukkit.getLogger().warning("You need to enter your Metalabs API-Key in the config.yml");
            }
            ArrayList<UUID> uuids = new ArrayList<>();
            for (Object string : getArrayList("lists.authorizedplayers")) {
                uuids.add(UUID.fromString(string.toString()));
            }
            Main.authorized = uuids;
            Main.usedKeys = getArrayList("lists.usedkeys");
            for (String string : Main.usedKeys) {
                Main.key.put(string, UUID.fromString(config.getString("maps." + string)));
            }
            for (UUID uuid : Main.authorized) {
                Main.player.put(uuid, config.getString("maps." + uuid));
            }
        } catch (Exception e) {
            resetConfig();
        }
    }

    public void resetConfig() {
        config.set("API_KEY", "Insert your Key here!");
        config.set("lists.authorizedplayers", new ArrayList<>());
        config.set("lists.usedkeys", new ArrayList<>());
        loadConfig();
    }
}
