package com.settingdust.levelpoints.commands;

import com.settingdust.levelpoints.LevelPoints;
import com.settingdust.levelpoints.utils.LanguageUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

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
        if (!(sender instanceof ConsoleCommandSender)) {
            if (args[2].matches("\\d+")) {
                LevelPoints.pointsUtils.takeAttribute(args[0], args[1], Integer.parseInt(args[2]));
                sender.sendMessage(LanguageUtils.getString("command.take.message")
                        .replace("{points}", String.valueOf(LevelPoints.pointsUtils.getAttribute(args[0], args[1]))));
            } else {
                sender.sendMessage(LanguageUtils.getString("command.error.only_int"));
            }
        } else {
            sender.sendMessage(LanguageUtils.getString("command.error.only_player"));
        }
        return true;
    }
}
