package com.settingdust.levelpoints.utils;

import com.settingdust.levelpoints.LevelPoints;
import com.settingdust.levelpoints.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/**
 * Author: SettingDust
 * Date: 16-8-7
 * By IntelliJ IDEA
 */
public class PointsUtils {
    private Map<String, Attribute> attributes = new HashMap<String, Attribute>();
    private Map<String, Object> playerAttributes = new HashMap<String, Object>();
    public Config config = new Config("attributes.yml", LevelPoints.plugin);

    public PointsUtils() {
        Set<String> types = this.config.getConfig().getKeys(false);
        Iterator<String> iterator = types.iterator();
        while (iterator.hasNext()) {
            String name = iterator.next();
            attributes.put(name, new Attribute(name));
        }
        this.config.load("data.yml");
        this.playerAttributes = this.config.getConfig().getConfigurationSection("players").getValues(true);
    }

    public int getAttribute(String name, String type) {
        int value = 0;
        if (playerAttributes.containsKey(name)) {
            HashMap<String, Integer> playerAttribute = (HashMap<String, Integer>) playerAttributes.get(name);
            if (playerAttribute.containsKey(type)) {
                value = playerAttribute.get(type);
            }
        }
        return value;
    }

    public double getAttribute(String name, String type, String key) {
        double value = 0;
        if (playerAttributes.containsKey(name)) {
            HashMap<String, Integer> playerAttribute = (HashMap<String, Integer>) playerAttributes.get(name);
            if (playerAttribute.containsKey(type)
                    && attributes.get(type).attributes.containsKey(key)) {
                value = playerAttribute.get(type) * attributes.get(type).attributes.get(key);
            }
        }
        return value;
    }

    public void setAttributes(String name, String type, int value) {
        if (!playerAttributes.containsKey(name)) playerAttributes.put(name, new HashMap<String, Integer>());
        HashMap<String, Integer> playerAttribute = (HashMap<String, Integer>) playerAttributes.get(name);
        playerAttribute.put(type, value);
        playerAttributes.put(name, playerAttribute);
    }

    public void addAttribute(String name, String type, int value) {
        this.setAttributes(name, type, getAttribute(name, type) + value);
    }

    public void takeAttribute(String name, String type, int value) {
        this.setAttributes(name, type, getAttribute(name, type) - value);
    }

    public Map<String, Attribute> getAttributes() {
        return attributes;
    }

}