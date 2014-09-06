package com.gmail.spica.toyblocks.market.util;

import static com.Acrobot.Breeze.Utils.InventoryUtil.fits;
import static com.Acrobot.Breeze.Utils.InventoryUtil.hasItems;
import static com.Acrobot.Breeze.Utils.MaterialUtil.getItem;
import static com.Acrobot.ChestShop.Signs.ChestShopSign.isAdminShop;
import static com.Acrobot.ChestShop.Utils.uBlock.findConnectedChest;
import com.gmail.spica.toyblocks.market.MarketSign;
import static com.gmail.spica.toyblocks.market.util.BlockUtil.getOppSideSign;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getMarketSign;
import static com.gmail.spica.toyblocks.market.util.FileUtil.writeMarketSign;
import static java.lang.Integer.parseInt;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MarketSignUtil {

    public static void updateStatus(Sign shop) {
        Sign market = getOppSideSign(shop);
        MarketSign ms = getMarketSign(market.getLine(3));
        if (isAdminShop(shop)) {
            ms.writeStatus(false, false);
            writeMarketSign(market.getLine(3), ms);
            return;
        }
        ItemStack is = getItem(shop.getLine(3));
        int amount;
        try {
            amount = parseInt(shop.getLine(1));
        } catch (NumberFormatException e) {
            amount = 0;
        }
        is.setAmount(amount);
        ItemStack[] items = {is};
        Chest c = findConnectedChest(shop);
        if (c == null) {
            ms.writeStatus(false, false);
            writeMarketSign(market.getLine(3), ms);
            return;
        }
        Inventory inv = c.getInventory();
        ms.writeStatus(!hasItems(items, inv), !fits(is, inv));
        writeMarketSign(market.getLine(3), ms);
    }
}
