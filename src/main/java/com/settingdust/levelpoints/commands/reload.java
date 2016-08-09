package com.settingdust.levelpoints.commands;

import com.settingdust.levelpoints.LevelPoints;
import com.settingdust.levelpoints.utils.LanguageUtils;
import com.settingdust.levelpoints.utils.PointsUtils;
import org.bukkit.command.CommandSender;

/**
 * Author: SettingDust
 * Date: 16-8-8
 * By IntelliJ IDEA
 */
public class reload extends BaseCommand {
    public reload(String[] args) {
        super(args);
    }

    @Override
    public boolean excute(CommandSender sender, String[] args) {
        LevelPoints.plugin.reloadConfig();
        LevelPoints.plugin.getConfig().options().copyDefaults(true);
        LevelPoints.plugin.saveConfig();
        LevelPoints.language = new LanguageUtils();
        LevelPoints.pointsUtils = new PointsUtils();
        sender.sendMessage(LanguageUtils.getString("command.reload.message"));
        return true;
    }
}
