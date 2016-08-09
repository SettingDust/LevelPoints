package com.settingdust.levelpoints.utils;

import com.settingdust.levelpoints.LevelPoints;
import com.settingdust.levelpoints.config.Config;

/**
 * Author: SettingDust
 * Date: 16-8-7
 * By IntelliJ IDEA
 */
public class LanguageUtils {
    public static Config config;

    public LanguageUtils() {
        config = new Config("language\\" + LevelPoints.plugin.getConfig().getString("language") + ".yml",
                LevelPoints.plugin);
    }

    public static String getCommandDescription(String name) {
        if (config.getConfig().contains("command." + name + ".description"))
            return getString("command." + name + ".description");
        else
            return null;
    }

    public static String getString(String path) {
        if (config.getConfig().contains(path))
            return config.getConfig().getString(path).replace("&", "§");
        else
            return "";
    }
}
