package com.gmail.spica.toyblocks.market.commands;

import com.Acrobot.ChestShop.Configuration.Properties;
import com.gmail.spica.toyblocks.market.MarketSign;
import com.gmail.spica.toyblocks.market.PlayerData;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getMarketSignList;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getPlayerData;
import static com.gmail.spica.toyblocks.market.util.FileUtil.writePlayerData;
import static java.lang.Integer.parseInt;
import static java.lang.Short.parseShort;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SearchBCommand {

    public static void onCommand(CommandSender sender, Command cmd, String label, String[] args, boolean ignorePerm) {
        if (!ignorePerm) {
            if (!sender.hasPermission("market.command.search.buyprice")) {
                sender.sendMessage("§c権限が不足しています!");
                return;
            }
        }
        short damage;
        int id;
        if (args.length > 3) {
            try {
                damage = parseShort(args[3]);
            } catch (NumberFormatException e) {
                sender.sendMessage("§c/market s b <itemid> <durability> 値が不正です!");
                return;
            }
        } else {
            damage = 0;
        }
        if (args.length > 2) {
            try {
                id = parseInt(args[2]);
            } catch (NumberFormatException e) {
                sender.sendMessage("§c/market s b <itemid> <durability> 値が不正です!");
                return;
            }
        } else {
            sender.sendMessage("§c/market s b <itemid> <durability>");
            return;
        }
        HashMap<MarketSign, Double> map = new HashMap<>();
        {
            ArrayList<MarketSign> list = getMarketSignList();
            for (MarketSign ms: list) {
                if (!(ms.isEmpty()) && ms.getBPrice() != 0) {
                    if (ms.getItem() == Material.getMaterial(id)) {
                        if (ms.getDamage() == damage) {
                            double priceStack = ms.getBPrice() / ms.getAmount() * 64;
                            map.put(ms, priceStack);
                        }
                    }
                }
            }
        }
        ArrayList entries = new ArrayList(map.entrySet());
        Collections.sort(entries, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Map.Entry e1 = (Map.Entry) o1;
                Map.Entry e2 = (Map.Entry) o2;
                int v1 = (int) Math.round((Double) e1.getValue() * 100);
                int v2 = (int) Math.round((Double) e2.getValue() * 100);
                return v1 - v2;
            }
        });
        if (entries.isEmpty()) {
            sender.sendMessage("§c該当するショップは見つかりませんでした…。");
            return;
        }
        if (sender instanceof Player) {
            PlayerData pd = getPlayerData(((Player) sender).getName());
            Map.Entry me = (Map.Entry) entries.get(0);
            MarketSign ms = (MarketSign) me.getKey();
            pd.setLastest(ms.type);
            writePlayerData(((Player) sender).getName(), pd);
        }
        for (int i = 0; i < entries.size(); ++i) {
            Map.Entry entry = (Map.Entry) entries.get(i);
            MarketSign ms = (MarketSign) entry.getKey();
            if (ms.getOwner().equals(Properties.ADMIN_SHOP_NAME)) {
                sender.sendMessage("＠§c" + ms.getOwner() + "§aの店§7[販売]");
            } else {
                sender.sendMessage("・§6" + ms.getOwner() + "§aの店§7[販売]");
            }
            sender.sendMessage("§6" + ms.getAmount() + "§a個§c" + ms.getBPrice() + "§acoin");
        }
        if (sender.hasPermission("market.command.check")) {
            sender.sendMessage("§c/mk c§aで最も売値が安い店の周辺へテレポートします!");
        }
    }
}
