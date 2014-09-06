package com.gmail.spica.toyblocks.market.listener;

import static com.Acrobot.Breeze.Utils.MaterialUtil.getItem;
import com.Acrobot.ChestShop.Events.PreShopCreationEvent;
import static com.Acrobot.ChestShop.Signs.ChestShopSign.isAdminShop;
import com.gmail.spica.toyblocks.market.ConfigData;
import com.gmail.spica.toyblocks.market.ItemType;
import com.gmail.spica.toyblocks.market.PlayerData;
import static com.gmail.spica.toyblocks.market.util.BlockUtil.getOppSideSign;
import static com.gmail.spica.toyblocks.market.util.BlockUtil.isMarketShop;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getItemType;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getMarketSign;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getPlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class PreShopCreationListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onShopCreated(PreShopCreationEvent e) {
        if (e.getOutcome() != PreShopCreationEvent.CreationOutcome.SHOP_CREATED_SUCCESSFULLY) {
            return;
        }
        if (!isMarketShop(e.getSign(), true)) {
            return;
        }
        Player p = e.getPlayer();
        if (getOppSideSign(e.getSign()).getLine(1).isEmpty()) {
            p.sendMessage("§cMarket看板の記述内容が異常です!");
            e.getSign().getBlock().breakNaturally();
            e.setOutcome(PreShopCreationEvent.CreationOutcome.NO_CHEST);
            return;
        }
        if (getMarketSign(getOppSideSign(e.getSign()).getLine(3)) == null) {
            p.sendMessage("§cMarket看板の記述内容が異常です!");
            e.getSign().getBlock().breakNaturally();
            e.setOutcome(PreShopCreationEvent.CreationOutcome.NO_CHEST);
            return;
        }
        if (getItemType(getOppSideSign(e.getSign()).getLine(1)) == null) {
            p.sendMessage("§cItemTypeが不正です!");
            e.getSign().getBlock().breakNaturally();
            e.setOutcome(PreShopCreationEvent.CreationOutcome.NO_CHEST);
            return;
        }
        ItemType it = getItemType(getOppSideSign(e.getSign()).getLine(1));
        byte line = 3;
        ItemStack is = getItem(e.getSignLine(line));
        if (!it.containsItem(is.getType())) {
            p.sendMessage("§cそのアイテムはここでは出品できません!");
            e.getSign().getBlock().breakNaturally();
            e.setOutcome(PreShopCreationEvent.CreationOutcome.NO_CHEST);
            return;
        }
        if (!p.hasPermission("market.sign.shop")) {
            p.sendMessage("§c権限が不足しています");
            e.getSign().getBlock().breakNaturally();
            e.setOutcome(PreShopCreationEvent.CreationOutcome.NO_CHEST);
        }
        if (!isAdminShop(e.getSign())) {
            PlayerData pd = getPlayerData(e.getSignLine((byte) 0).replaceAll(" ", ""));
            if (ConfigData.limit == pd.getTotalCount()) {
                p.sendMessage("§cあなたはマーケットにアイテムをもう出品できません!");
                e.getSign().getBlock().breakNaturally();
                e.setOutcome(PreShopCreationEvent.CreationOutcome.NO_CHEST);
            }
        }
        if (!isAdminShop(e.getSign())) {
            PlayerData pd = getPlayerData(e.getSignLine((byte) 0).replaceAll(" ", ""));
            if (it.limit == pd.getCount(it)) {
                p.sendMessage("§cあなたはこの種類のアイテムをもう出品できません!");
                e.getSign().getBlock().breakNaturally();
                e.setOutcome(PreShopCreationEvent.CreationOutcome.NO_CHEST);
            }
        }
    }
}
