package com.settingdust.levelpoints.handlers;

import com.settingdust.levelpoints.LevelPoints;
import com.settingdust.levelpoints.utils.LanguageUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: SettingDust
 * Date: 16-8-7
 * By IntelliJ IDEA
 */
public class GuiHandler implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onUpgrade(PlayerLevelChangeEvent event) {
        if (event.getNewLevel() > event.getOldLevel()) {
            LevelPoints.pointsUtils.addAttribute(event.getPlayer().getName(), "free",
                    LevelPoints.plugin.getConfig().getInt("points"));
            event.getPlayer().sendMessage(LanguageUtils.getString("levelup_message"));
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        Player player = (Player) event.getWhoClicked();
        if (inventory.getTitle().equalsIgnoreCase(LanguageUtils.getString("gui_title"))
                && !event.isCancelled()
                && isVaildItem(inventory.getItem(8))
                && isVaildItem(inventory.getItem(7))

                && inventory.getItem(8).getItemMeta().getDisplayName().equalsIgnoreCase(
                LanguageUtils.getString("attributes.attribute"))
                && inventory.getItem(7).getItemMeta().getDisplayName().equalsIgnoreCase(
                LanguageUtils.getString("attributes.free") + "：" +
                        LevelPoints.pointsUtils.getAttribute(player.getName(), "free"))
                ) {
            if (event.getAction().equals(InventoryAction.PICKUP_ONE)) {
                if (LevelPoints.pointsUtils.getAttribute(player.getName(), "free") > 0) {
                    ItemStack item = event.getCurrentItem();
                    ItemMeta itemMeta = item.getItemMeta();
                    List<String> lore = itemMeta.getLore();
                    item.setAmount(item.getAmount() + 1);
                    inventory.setItem(event.getSlot(), item);

                    for (int i = 0; i < lore.size(); i++) {
                        if (lore.get(i).contains(LanguageUtils.getString("attributes.type") + "：")) {
                            String type = lore.get(i).replace(LanguageUtils.getString("attributes.type") + "：", "");
                            LevelPoints.pointsUtils.addAttribute(player.getName(), type, 1);
                        }
                    }

                    item = inventory.getItem(8);
                    itemMeta = item.getItemMeta();
                    lore = new ArrayList<String>();

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
                    itemMeta.setLore(lore);
                    item.setItemMeta(itemMeta);

                    item = inventory.getItem(7);
                    itemMeta = item.getItemMeta();
                    itemMeta.setDisplayName(LanguageUtils.getString("attributes.free") + "：" +
                            LevelPoints.pointsUtils.getAttribute(player.getName(), "free"));
                    item.setItemMeta(itemMeta);
                }
                event.setCancelled(true);
            } else {
                event.setCancelled(true);
            }
        }
    }

    private boolean isVaildItem(ItemStack item) {
        return item != null && !item.getType().equals(Material.AIR) && item.hasItemMeta()
                && item.getItemMeta().hasLore();
    }
}
