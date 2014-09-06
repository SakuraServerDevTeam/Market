package com.gmail.spica.toyblocks.market.listener;

import com.Acrobot.ChestShop.Events.ShopDestroyedEvent;
import com.gmail.spica.toyblocks.market.MarketSign;
import com.gmail.spica.toyblocks.market.PlayerData;
import static com.gmail.spica.toyblocks.market.util.BlockUtil.getOppSideSign;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getMarketSign;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getPlayerData;
import static com.gmail.spica.toyblocks.market.util.FileUtil.writeMarketSign;
import static com.gmail.spica.toyblocks.market.util.FileUtil.writePlayerData;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ShopDestroyedListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onShopDestroyed(ShopDestroyedEvent e) {
        Sign marketSign = getOppSideSign(e.getSign());
        if (marketSign==null) {
            return;
        }
        MarketSign ms = getMarketSign(marketSign.getLine(3));
        PlayerData pd = getPlayerData(ms.getOwner().replaceAll(" ", ""));
        pd.removeCount(ms.type);
        writePlayerData(ms.getOwner().replaceAll(" ", ""), pd);
        ms.reset();
        writeMarketSign(marketSign.getLine(3), ms);
        Player p = e.getDestroyer();
        if (p != null) {
            p.sendMessage("§aMarket連携を解除しました!");
        }
    }
}
