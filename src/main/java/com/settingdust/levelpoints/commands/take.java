package com.settingdust.levelpoints.commands;

import com.settingdust.levelpoints.LevelPoints;
import com.settingdust.levelpoints.utils.LanguageUtils;
import org.bukkit.command.CommandSender;

/**
 * Author: SettingDust
 * Date: 16-8-7
 * By IntelliJ IDEA
 */
public class take extends BaseCommand {
    public take(String[] args) {
        super(args);
    }

    @Override
    public boolean excute(CommandSender sender, String[] args) {
        if (!(sender instanceof CommandSender)) {
            if (args[2].matches("\\d+")) {
                LevelPoints.pointsUtils.takeAttribute(args[0], args[1], Integer.parseInt(args[2]));
                sender.sendMessage(LanguageUtils.getString("commands.error.take.message")
                        .replace("{points}", String.valueOf(LevelPoints.pointsUtils.getAttribute(args[0], args[1]))));
            } else {
                sender.sendMessage(LanguageUtils.getString("commands.error.only_int"));
            }
        } else {
            sender.sendMessage(LanguageUtils.getString("commands.error.only_player"));
        }
        return true;
    }
}
