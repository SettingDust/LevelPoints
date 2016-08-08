package com.settingdust.levelpoints.commands;

import com.settingdust.levelpoints.utils.LanguageUtils;
import org.bukkit.command.CommandSender;

/**
 * Author: SettingDust
 * Date: 16-8-7
 * By IntelliJ IDEA
 */
public class BaseCommand{
    private String[] args = null;
    private String description = LanguageUtils.getCommandDescription(args[0]);

    public BaseCommand(String[] args) {
        this.args = args;
        this.description = description;
    }

    public String[] getArgs() {
        return args;
    }

    public String getDescription() {
        return description;
    }

    public boolean excute(CommandSender sender, String[] args) {
        return false;
    }
}
