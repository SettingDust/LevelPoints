package com.settingdust.levelpoints;

import com.settingdust.levelpoints.handlers.CommandHanlder;
import com.settingdust.levelpoints.handlers.GuiHandler;
import com.settingdust.levelpoints.utils.LanguageUtils;
import com.settingdust.levelpoints.utils.PointsUtils;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Author: SettingDust
 * Date: 16-8-7
 * By IntelliJ IDEA
 */
public class LevelPoints extends JavaPlugin {
    public final static String command = "levelpoints";
    public final static String pluginName = "LevelPoints";

    public static LevelPoints plugin;
    public static LanguageUtils language;
    public static PointsUtils pointsUtils;

    @Override
    public void onEnable() {
        this.plugin = this;
        this.language = new LanguageUtils();
        this.pointsUtils = new PointsUtils();

        this.getLogger().info("Author: SettingDust");
        this.getLogger().info("E-mail: settingdust@gmail.com");
        this.getLogger().info("Please report bugs with e-mail. ");
        this.getServer().getPluginManager().registerEvents(new GuiHandler(), this);
        this.getCommand(command).setExecutor(new CommandHanlder(this));
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }

    @Override
    public void onDisable() {
        this.saveConfig();
        this.pointsUtils.config.saveConfig();
    }
}
