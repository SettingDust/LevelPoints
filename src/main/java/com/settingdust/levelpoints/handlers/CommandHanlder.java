package com.settingdust.levelpoints.handlers;

import com.settingdust.levelpoints.LevelPoints;
import com.settingdust.levelpoints.commands.BaseCommand;
import com.settingdust.levelpoints.commands.add;
import com.settingdust.levelpoints.commands.getop;
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
    private final LevelPoints plugin;
    private static List<BaseCommand> commands = new ArrayList<BaseCommand>();

    public CommandHanlder(LevelPoints plugin) {
        this.plugin = plugin;
        commands.add(new getop(new String[]{"getop"}));
        commands.add(new add(new String[]{"add", "[name]", "[type]", "[points]"}));
        commands.add(new add(new String[]{"take", "[name]", "[type]", "[points]"}));
        commands.add(new add(new String[]{"reload"}));
        commands.add(new add(new String[]{"points"}));
        for (int i = 0; i < commands.size(); i++) {
            for (int i1 = 0; i1 < commands.get(i).getArgs().length; i1++) {
                if (commands.get(i).getArgs()[i].matches("\\[\\w+\\]")) {
                    commands.get(i).getArgs()[i] =
                            "[" + LanguageUtils.config.getConfig().getString(
                                    commands.get(i).getArgs()[0] + ".args." + commands.get(i).getArgs()[i].replace("[", "").replace("]", ""))
                                    + "]";
                }
            }
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase(plugin.command)) {
            if (args.length == 0) {
                String[] commandsInfo = new String[]{ChatColor.LIGHT_PURPLE + LevelPoints.pluginName + "插件指令列表"};
                for (BaseCommand command : commands) {
                    if (command.getDescription() != null) {
                        commandsInfo[commandsInfo.length] = ChatColor.YELLOW + "/" + cmd.getName() + " - "
                                + command.getDescription();
                    }
                }
                commandsInfo[commandsInfo.length] = ChatColor.LIGHT_PURPLE + "Author: SettingDust";
                sender.sendMessage(commandsInfo);
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
                        String[] newArgs = new String[]{};
                        for (int i = 0; i < command.getArgs().length; i++) {
                            if (command.getArgs()[i].matches("\\[\\w+\\]") &&
                                    command.getArgs()[i].equalsIgnoreCase(args[i])) {
                                newArgs[newArgs.length] = args[i];
                            }
                        }
                        command.excute(sender, newArgs);
                    }
                }
            }
        }
        return false;
    }
}
