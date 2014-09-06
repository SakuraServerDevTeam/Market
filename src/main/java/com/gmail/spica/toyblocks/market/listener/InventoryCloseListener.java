package com.gmail.spica.toyblocks.market.listener;

import static com.gmail.spica.toyblocks.market.util.BlockUtil.getConnectedSigns;
import static com.gmail.spica.toyblocks.market.util.MarketSignUtil.updateStatus;
import java.util.ArrayList;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;

public class InventoryCloseListener implements Listener{
@EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClose(InventoryCloseEvent e) {
        InventoryHolder h = e.getInventory().getHolder();
        ArrayList<Sign> signs = new ArrayList<>();
        if (h instanceof DoubleChest) {
            DoubleChest c = (DoubleChest) h;
            Chest c1 = (Chest) c.getLeftSide();
            Chest c2 = (Chest) c.getRightSide();
            signs.addAll(getConnectedSigns(c1.getBlock()));
            signs.addAll(getConnectedSigns(c2.getBlock()));
        } else if (h instanceof Chest) {
            Chest c = (Chest) h;
            signs.addAll(getConnectedSigns(c.getBlock()));
        } else {
            return;
        }
        int size = signs.size();
        for (int i = 0; i < size; i++) {
            updateStatus(signs.get(i));
        }
    }
}
