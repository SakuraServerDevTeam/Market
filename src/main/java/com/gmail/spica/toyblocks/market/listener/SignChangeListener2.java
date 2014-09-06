package com.gmail.spica.toyblocks.market.listener;

import static java.lang.Integer.parseInt;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChangeListener2 implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSignChange(SignChangeEvent e) {
        if (!ChatColor.stripColor(e.getLine(0)).toLowerCase().replaceAll(" ", "").equals("[search-item]")) {
            return;
        }
        if (!e.getPlayer().hasPermission("market.sign.create.search")) {
            e.getPlayer().sendMessage("§c権限が不足しています。");
            e.getBlock().breakNaturally();
            return;
        }
        if (e.getLine(1).isEmpty()) {
            e.getPlayer().sendMessage("§c2行目にアイテムIDを入力してください。");
            e.getBlock().breakNaturally();
            return;
        }
        int id;
        short damage = 0;
        try {
            id = parseInt(e.getLine(1));
        } catch (NumberFormatException ex) {
            e.getPlayer().sendMessage("§c2行目に正しくアイテムIDを入力してください。");
            e.getBlock().breakNaturally();
            return;
        }
        if (!e.getLine(2).isEmpty()) {
            try {
                id = parseInt(e.getLine(2));
            } catch (NumberFormatException ex) {
                e.getPlayer().sendMessage("§c3行目に正しく値を入力してください。");
                e.getBlock().breakNaturally();
                return;
            }
        }
        e.setLine(0, "§a[Search-Item]");
        e.getPlayer().sendMessage("§a看板の作製に成功しました!");
    }

}
