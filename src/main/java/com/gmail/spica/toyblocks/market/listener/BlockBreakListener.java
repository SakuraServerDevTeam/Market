package com.gmail.spica.toyblocks.market.listener;

import static com.gmail.spica.toyblocks.market.util.BlockUtil.canBreak;
import static com.gmail.spica.toyblocks.market.util.FileUtil.removeMarketSign;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener{
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent e) {
        if (!canBreak(e.getPlayer(), e.getBlock())) {
            e.setCancelled(true);
            return;
        }
        if (e.getBlock().getType()!=Material.WALL_SIGN) {
            return;
        }
        Sign sign = (Sign) e.getBlock().getState();
        if (!ChatColor.stripColor(sign.getLine(0)).toLowerCase().equals("[market]")) {
            return;
        }
        removeMarketSign(sign.getLine(3));
        e.getPlayer().sendMessage("§7[§3Market§7]§aデータ削除完了:管理ID§6"+sign.getLine(3));
    }
}

