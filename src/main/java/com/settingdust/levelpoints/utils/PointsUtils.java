package com.settingdust.levelpoints.utils;

import com.settingdust.levelpoints.LevelPoints;
import com.settingdust.levelpoints.config.Config;
import org.bukkit.configuration.MemorySection;

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
        for (String type : types) {
            attributes.put(type, new Attribute(type));
        }
        this.config.load("data.yml", false);
        this.playerAttributes = this.config.getConfig().getValues(true);
    }

    public int getAttribute(String name, String type) {
        int value = 0;
        if (playerAttributes.containsKey(name)) {
            MemorySection playerAttribute = (MemorySection) playerAttributes.get(name);
            if (playerAttribute.contains(type)) {
                value = playerAttribute.getInt(type);
            }
        }
        return value;
    }

    public double getAttribute(String name, String type, String key) {
        double value = 0;
        if (playerAttributes.containsKey(name)) {
            MemorySection playerAttribute = (MemorySection) playerAttributes.get(name);
            if (playerAttribute.contains(type)
                    && attributes.get(type).attributes.containsKey(key)) {
                value = playerAttribute.getInt(type) * attributes.get(type).attributes.get(key);
            }
        }
        return value;
    }

    public void setAttributes(String name, String type, int value) {
        if (!playerAttributes.containsKey(name)) {
            this.config.getConfig().addDefault(name + "." + type, 0);
            this.playerAttributes.put(name, this.config.getConfig().getConfigurationSection(name));
        }
        MemorySection playerAttribute = (MemorySection) playerAttributes.get(name);
        playerAttribute.set(type, value);
        this.playerAttributes.put(name, playerAttribute);
        this.config.getConfig().set(name, playerAttribute);
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