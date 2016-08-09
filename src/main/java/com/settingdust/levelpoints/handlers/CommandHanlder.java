package com.settingdust.levelpoints.handlers;

import com.settingdust.levelpoints.LevelPoints;
import com.settingdust.levelpoints.commands.*;
import com.settingdust.levelpoints.utils.LanguageUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: SettingDust
 * Date: 16-8-7
 * By IntelliJ IDEA
 */
public class CommandHanlder implements CommandExecutor {
    private static List<BaseCommand> commands = new ArrayList<BaseCommand>();

    public CommandHanlder(LevelPoints plugin) {
        commands.add(new getop(new String[]{"getop"}));
        commands.add(new add(new String[]{"add", "[name]", "[type]", "[points]"}));
        commands.add(new take(new String[]{"take", "[name]", "[type]", "[points]"}));
        commands.add(new reload(new String[]{"reload"}));
        commands.add(new points(new String[]{"points"}));
        for (int i = 0; i < commands.size(); i++) {
            for (int i1 = 0; i1 < commands.get(i).getArgs().length; i1++) {
                if (commands.get(i).getArgs()[i1].matches("\\[\\w+\\]")) {
                    commands.get(i).getArgs()[i1] =
                            "[" + LanguageUtils.getString(
                                    "command." + commands.get(i).getArgs()[0] + ".args." + (commands.get(i).getArgs()[i1].replace("[", "").replace("]", "")))
                                    + "]";
                }
            }
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase(LevelPoints.command)) {
            if (args.length == 0) {
                List<String> commandsInfo = new ArrayList<String>();
                for (BaseCommand command : commands) {
                    if (command.getDescription() != null) {
                        String message = ChatColor.YELLOW + "/" + cmd.getName();
                        for (String arg : command.getArgs()) {
                            message = message + " " + arg;
                        }
                        message = message + " - " + command.getDescription();
                        commandsInfo.add(message);
                    }
                }
                commandsInfo.add(ChatColor.LIGHT_PURPLE + "Author: SettingDust");
                sender.sendMessage(commandsInfo.toArray(new String[commandsInfo.size()]));
                return true;
            }
            for (BaseCommand command : commands) {
                if (command.getArgs().length == args.length) {
                    boolean excute = true;
                    for (int i = 0; i < command.getArgs().length; i++) {
                        if (!command.getArgs()[i].matches("\\[\\w+\\]") &&
                                !command.getArgs()[i].equalsIgnoreCase(args[i])) {
                            excute = false;
                            break;
                        }
                    }
                    if (excute) {
                        List<String> newArgs = new ArrayList<String>();
                        for (int i = 0; i < command.getArgs().length; i++) {
                            if (command.getArgs()[i].matches("\\[\\w+\\]") &&
                                    !command.getArgs()[i].equalsIgnoreCase(args[i])) {
                                newArgs.add(args[i]);
                            }
                        }

                        return command.excute(sender, newArgs.toArray(new String[newArgs.size()]));
                    }
                }
            }
        }
        sender.sendMessage(LanguageUtils.getString("command.error.args"));
        return false;
    }
}
