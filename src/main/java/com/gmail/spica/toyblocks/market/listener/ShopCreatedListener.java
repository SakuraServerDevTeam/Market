package com.gmail.spica.toyblocks.market.listener;

import com.Acrobot.ChestShop.Events.ShopCreatedEvent;
import com.gmail.spica.toyblocks.market.MarketSign;
import com.gmail.spica.toyblocks.market.PlayerData;
import static com.gmail.spica.toyblocks.market.util.BlockUtil.getOppSideSign;
import static com.gmail.spica.toyblocks.market.util.BlockUtil.isMarketShop;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getMarketSign;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getPlayerData;
import static com.gmail.spica.toyblocks.market.util.FileUtil.writeMarketSign;
import static com.gmail.spica.toyblocks.market.util.FileUtil.writePlayerData;
import static com.gmail.spica.toyblocks.market.util.MarketSignUtil.updateStatus;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitScheduler;

public class ShopCreatedListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onShopCreated(final ShopCreatedEvent e) {
        if (e.getSign() == null || e.getSign().getBlock().getType() == Material.AIR) {
            return;
        }
        if (!isMarketShop(e.getSign(), true)) {
            return;
        }
        
        MarketSign ms = getMarketSign(getOppSideSign(e.getSign()).getLine(3));
        ms.upDateSign(e.getSignLine((short) 0),e.getSignLine((short) 1),e.getSignLine((short) 2),e.getSignLine((short) 3));
        PlayerData pd = getPlayerData(e.getSignLine((short) 0).replaceAll(" ", ""));
        pd.addCount(ms.type);
        writePlayerData(e.getSignLine((short) 0).replaceAll(" ", ""), pd);
        Player p = e.getPlayer();
        if (writeMarketSign(getOppSideSign(e.getSign()).getLine(3), ms)) {
            p.sendMessage("§7[§3Market§7]§aMarket連携に成功しました!");
            p.sendMessage("§7[§3Market§7]§a管理ID:§6" + getOppSideSign(e.getSign()).getLine(3));
            BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
            scheduler.scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Market"), new Runnable() {
                @Override
                public void run() {
                    updateStatus(e.getSign());
                }
            }, 40L);
            return;
        }
        p.sendMessage("§7[§3Market§7]§aMarket連携に失敗しました!");
        e.getSign().getBlock().breakNaturally();
        updateStatus(e.getSign());
    }
}
