package com.gmail.spica.toyblocks.market.commands;

import static com.gmail.spica.toyblocks.localizename.Main.getInfo;
import com.gmail.spica.toyblocks.market.ItemType;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getItemType;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getItemTypeNameList;
import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

public class ItemTypeInfoCommand {
    public static void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("market.command.itemtype.info")) {
            sender.sendMessage("§c権限が不足しています!");
            return;
        }
        if (args.length < 3) {
            ArrayList<String> its = getItemTypeNameList();
            if (its.isEmpty()) {
                sender.sendMessage("§cデータが1つも見つかりませんでした。");
            }
            for (int i = 0 ; i < its.size() ; ++i) {
                sender.sendMessage("§6"+(i+1)+"§a:"+its.get(i));
            }
            return;
        }
        ItemType it = getItemType(args[2]);
        if (it == null) {
            sender.sendMessage("§cそのItemTypeは存在しません!");
            return;
        }
        showInfo(sender, it);
    }
    
    private static void showInfo(CommandSender s, ItemType i) {
        Location loc = i.loc.deserializeLocation();
        ArrayList<ItemStack> iss = i.getItemList();
        s.sendMessage("§a名前:§6"+i.name);
        s.sendMessage("§a座標:§6"+loc.getWorld().getName()+"§a,"+loc.getX()+"§a,"+loc.getY()+"§a,"+loc.getZ()+"§a("+loc.getYaw()+"§a,"+loc.getPitch()+"§a)");
        s.sendMessage("§a制限数:§6"+i.limit);
        for (int in = 0; in < i.getItemList().size() ; ++in) {
            s.sendMessage(getInfo(i.getItemList().get(in)));
        }
    }
}
