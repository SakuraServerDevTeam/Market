package com.gmail.spica.toyblocks.market.listener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChangeListener4 implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSignChange(SignChangeEvent e) {
        if (!ChatColor.stripColor(e.getLine(0)).toLowerCase().replaceAll(" ", "").equals("[search-p]")) {
            return;
        }
        if (!e.getPlayer().hasPermission("market.sign.create.searchp")) {
            e.getPlayer().sendMessage("§c権限が不足しています。");
            e.getBlock().breakNaturally();
            return;
        }
        e.setLine(0, "§a[Search-P]");
        e.getPlayer().sendMessage("§a看板の作製に成功しました!");
    }
}
