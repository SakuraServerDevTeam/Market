package com.gmail.spica.toyblocks.market.listener;

import com.gmail.spica.toyblocks.market.MarketSign;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getItemType;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getMarketSign;
import static com.gmail.spica.toyblocks.market.util.FileUtil.writeMarketSign;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChangeListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSignChange(SignChangeEvent e) {
        if (!ChatColor.stripColor(e.getLine(0)).toLowerCase().equals("[market]")) {
            return;
        }
        Player p = e.getPlayer();
        if (!p.hasPermission("market.sign.create.market")) {
            p.sendMessage("§c権限が不足しています!");
            e.getBlock().breakNaturally();
            return;
        }
        if (e.getBlock().getType()!=Material.WALL_SIGN) {
            p.sendMessage("§cMarket看板は壁掛け看板である必要があります!");
            e.getBlock().breakNaturally();
            return;
        }
        String type = e.getLine(1);
        if (type.isEmpty()) {
            p.sendMessage("§cItemTypeを入力してください!");
            e.getBlock().breakNaturally();
            return;
        }
        if (getItemType(type) == null) {
            p.sendMessage("§cItemTypeが設定されていません!");
            e.getBlock().breakNaturally();
            return;
        }
        String signid = RandomStringUtils.randomAlphabetic(15);
        while (getMarketSign(signid) != null) {
            signid = RandomStringUtils.randomAlphabetic(15);
        }
        MarketSign ms = new MarketSign(e.getLine(1));
        writeMarketSign(signid, ms);
        p.sendMessage("§7[§3Market§7]§aMarket看板を新規作成しました!");
        p.sendMessage("§7[§3Market§7]§aデータ生成完了:管理ID§6" + signid);
        e.setLine(3, signid);
        e.setLine(0, ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "Market" + ChatColor.DARK_GRAY + "]");
    }
}
