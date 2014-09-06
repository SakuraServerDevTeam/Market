package com.gmail.spica.toyblocks.market.listener;

import com.gmail.spica.toyblocks.market.ItemType;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getItemType;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChangeListener3 implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSignChange(SignChangeEvent e) {
        if (!ChatColor.stripColor(e.getLine(0)).toLowerCase().replaceAll(" ", "").equals("[item-tp]")) {
            return;
        }
        if (!e.getPlayer().hasPermission("market.sign.create.tp")) {
            e.getPlayer().sendMessage("§c権限が不足しています。");
            e.getBlock().breakNaturally();
            return;
        }
        if (e.getLine(1).isEmpty()) {
            e.getPlayer().sendMessage("§c2行目にItemTypeを入力してください。");
            e.getBlock().breakNaturally();
            return;
        }
        ItemType it = getItemType(e.getLine(1));
        if (it == null) {
            e.getPlayer().sendMessage("§cそのItemTypeは存在しません!");
            e.getBlock().breakNaturally();
            return;
        }
        e.setLine(0, "§1[Item-TP]");
        e.getPlayer().sendMessage("§a看板の作製に成功しました!");
    }
}
