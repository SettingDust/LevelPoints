package com.settingdust.levelpoints.utils;

import com.settingdust.levelpoints.LevelPoints;
import com.settingdust.levelpoints.config.Config;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Author: SettingDust
 * Date: 16-8-8
 * By IntelliJ IDEA
 */
public class Attribute {
    public String name;
    public Material icon;

    public Map<String, Double> attributes = new HashMap<String, Double>();
    public Config config = new Config("attributes.yml", LevelPoints.plugin);

    public Attribute(String name) {
        this.name = name;
        this.icon = Material.getMaterial(this.config.getConfig().getString(this.name + ".icon"));
        Set<String> types = this.config.getConfig().getConfigurationSection(name).getKeys(false);
        for (String type : types) {
            if (!type.equalsIgnoreCase("icon"))
                this.attributes.put(type, this.config.getConfig().getDouble(name + "." + type));
        }
    }
}
