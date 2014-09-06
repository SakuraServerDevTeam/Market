package com.gmail.spica.toyblocks.market.listener;

import com.Acrobot.ChestShop.Events.TransactionEvent;
import com.Acrobot.ChestShop.Events.TransactionEvent.TransactionType;
import static com.Acrobot.ChestShop.Signs.ChestShopSign.isAdminShop;
import com.gmail.spica.toyblocks.market.ConfigData;
import com.gmail.spica.toyblocks.market.PlayerData;
import static com.gmail.spica.toyblocks.market.util.BlockUtil.isMarketShop;
import static com.gmail.spica.toyblocks.market.util.BlockUtil.isMarketShop;
import static com.gmail.spica.toyblocks.market.util.BlockUtil.isMarketShop;
import static com.gmail.spica.toyblocks.market.util.BlockUtil.isMarketShop;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getPlayerData;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getPlayerData;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getPlayerData;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getPlayerData;
import static com.gmail.spica.toyblocks.market.util.FileUtil.writePlayerData;
import static com.gmail.spica.toyblocks.market.util.MarketSignUtil.updateStatus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class TranscationListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTranscation(TransactionEvent e) {
        if (!isMarketShop(e.getSign())) {
            return;
        }
        updateStatus(e.getSign());
        e.getClient().sendMessage("§aマーケットのご利用ありがとうございます!");
        if (isAdminShop(e.getSign()) && ConfigData.ignoreAdminshop) {
            return;
        }
        if (e.getTransactionType() == TransactionType.BUY) {
            PlayerData pd = getPlayerData(e.getClient().getName());
            if (pd.addPoint(e.getPrice())) {
                e.getClient().sendMessage("§aポイントが付与されました!");
                writePlayerData(e.getClient().getName(), pd);
            }
        } else {
            PlayerData pd = getPlayerData(e.getOwner().getName());
            pd.addPoint(e.getPrice());
            writePlayerData(e.getOwner().getName(), pd);
        }
    }
}
