package com.gmail.spica.toyblocks.market.commands;

import static com.gmail.spica.toyblocks.localizename.Main.getInfo;
import com.gmail.spica.toyblocks.market.MarketSign;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getMarketSignList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

public class SearchPCommand {

    public static void onCommand(CommandSender sender, Command cmd, String label, String[] args, boolean ignorePerm) {
        if (!ignorePerm) {
            if (!sender.hasPermission("market.command.search.player")) {
                sender.sendMessage("§c権限が不足しています!");
                return;
            }
        }
        if (args.length < 3) {
            sender.sendMessage("§c/mk s p <player/*> [itemtype]");
            return;
        }
        HashMap<MarketSign, Double> map = new HashMap<>();
        {
            ArrayList<MarketSign> list = getMarketSignList();
            for (int i = 0; i < list.size(); ++i) {
                if (args.length < 4) {
                    if (list.get(0).type != null) {
                        if (list.get(i).getOwner().equals(args[2]) || args[2].equals("*")) {
                            double priceStack = list.get(i).getSPrice() / list.get(i).getAmount() * 64;
                            map.put(list.get(i), priceStack);
                        }
                    }
                } else {
                    if (list.get(0).type != null) {
                        if (args[3].equals(list.get(i).type.name)) {
                            if (list.get(i).getOwner().equals(args[2]) || args[2].equals("*")) {
                                double priceStack = list.get(i).getSPrice() / list.get(i).getAmount() * 64;
                                map.put(list.get(i), priceStack);
                            }
                        }
                    }
                }
            }
        }
        if (args.length > 3) {

        }
        ArrayList entries = new ArrayList(map.entrySet());
        if (entries.isEmpty()) {
            sender.sendMessage("§c該当するショップは見つかりませんでした…。");
            return;
        }
        for (int i = 0; i < entries.size(); ++i) {
            Map.Entry entry = (Map.Entry) entries.get(i);
            MarketSign ms = (MarketSign) entry.getKey();
            sender.sendMessage("§a" + ms.type.name + ":§6" + getInfo(new ItemStack(ms.getItem(), 1, ms.getDamage())));
            if (ms.getSPrice() > 0) {
                sender.sendMessage("§6" + ms.getAmount() + "§a個§c" + ms.getSPrice() + "§acoin§7[買取]");
            }
            if (ms.getBPrice() > 0) {
                sender.sendMessage("§6" + ms.getAmount() + "§a個§c" + ms.getBPrice() + "§acoin§7[販売]");
            }
            if (ms.isEmpty() && ms.getSPrice() != 0) {
                sender.sendMessage("§c【販売不可】");
            } else if (ms.isFull() && ms.getBPrice() != 0) {
                sender.sendMessage("§c【買取不可】");
            }
        }
    }
}
