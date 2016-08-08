package com.settingdust.levelpoints.commands;

import com.settingdust.levelpoints.LevelPoints;
import com.settingdust.levelpoints.utils.Attribute;
import com.settingdust.levelpoints.utils.LanguageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/**
 * Author: SettingDust
 * Date: 16-8-8
 * By IntelliJ IDEA
 */
public class points extends BaseCommand {
    public points(String[] args) {
        super(args);
    }

    @Override
    public boolean excute(CommandSender sender, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(LanguageUtils.getString("commands.error.only_player"));
        } else {
            Player player = (Player) sender;
            ItemStack[] items = new ItemStack[]{};
            double i = 0;
            for (String type : LevelPoints.pointsUtils.getAttributes().keySet()) {
                ItemStack item = new ItemStack(LevelPoints.pointsUtils.getAttributes().get(type).icon);
                ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(item.getType());
                List<String> lore = new ArrayList<String>();

                item.setAmount(LevelPoints.pointsUtils.getAttribute(player.getName(), type));
                lore.add(LanguageUtils.getString("attributes.type") + "：" + type);
                for (String key : LevelPoints.pointsUtils.getAttributes().get(type).attributes.keySet()) {
                    lore.add(LanguageUtils.getString("key." + key) + "：" +
                            LevelPoints.pointsUtils.getAttribute(player.getName(), type, key));
                }

                itemMeta.setLore(lore);
                itemMeta.setDisplayName(LanguageUtils.getString("attributes." + type));
                items[(int) i] = item;

                i++;
                while (i == 7 + (9 * Math.floor(i / 9)) || i == 8 + (9 * Math.floor(i / 9))) {
                    i++;
                }
            }

            items[7] = new ItemStack(Material.getMaterial(LevelPoints.plugin.getConfig().getString("free_icon")));
            ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(items[7].getType());
            itemMeta.setDisplayName(LanguageUtils.getString("attributes.free") + "：" +
                    LevelPoints.pointsUtils.getAttribute(player.getName(), "free"));
            items[7].setItemMeta(itemMeta);

            items[8] = new ItemStack(Material.getMaterial(LevelPoints.plugin.getConfig().getString("attribute_icon")));
            itemMeta = Bukkit.getItemFactory().getItemMeta(items[8].getType());
            List<String> lore = new ArrayList<String>();

            for (String type : LevelPoints.pointsUtils.getAttributes().keySet()) {
                lore.add(LanguageUtils.getString("attributes." + type) + "：" +
                        LevelPoints.pointsUtils.getAttribute(player.getName(), type));
            }

            lore.add(LanguageUtils.getString("split"));//------------

            Map<String, Double> values = new HashMap<String, Double>();
            for (String type : LevelPoints.pointsUtils.getAttributes().keySet()) {
                for (String key : LevelPoints.pointsUtils.getAttributes().get(type).attributes.keySet()) {
                    if (values.containsKey(key))
                        values.put(key, values.get(key) +
                                LevelPoints.pointsUtils.getAttribute(player.getName(), type, key));
                    else
                        values.put(key, LevelPoints.pointsUtils.getAttribute(player.getName(), type, key));
                }
            }
            for (String key : values.keySet()) {
                lore.add(LanguageUtils.getString("key." + key) + "：" + values.get(key));
            }

            itemMeta.setDisplayName(LanguageUtils.getString("attributes.attribute"));

            Inventory inventory = Bukkit.createInventory(player, (int) Math.ceil(i / 9) * 9, LanguageUtils.getString("gui_title"));
            player.openInventory(inventory);
        }
        return true;
    }
}
