package com.gmail.spica.toyblocks.market.commands;

import static com.gmail.spica.toyblocks.localizename.Main.getInfo;
import com.gmail.spica.toyblocks.market.MarketSign;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getIDList;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getMarketSign;
import java.util.ArrayList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

public class MarketSignInfoCommand {

    public static void onCommand(CommandSender sender, Command cmd, String label, String[] args, boolean ignorePerm) {
        if (!ignorePerm) {
            if (!sender.hasPermission("market.command.marketsign.info")) {
                sender.sendMessage("§c権限が不足しています!");
                return;
            }
        }
        if (args.length < 3) {
            ArrayList<String> msl = getIDList();
            if (msl.isEmpty()) {
                sender.sendMessage("§cデータが1つも見つかりませんでした!");
            }
            for (int i = 0; i < msl.size(); ++i) {
                sender.sendMessage("§6"+(i+1)+":§a"+msl.get(i));
            }
            return;
        }
        MarketSign ms = getMarketSign(args[2]);
        if (ms == null) {
            sender.sendMessage("§c該当管理IDのファイルは存在しません!");
            return;
        }
        if (ms.getItem() == null || ms.getAmount() == 0) {
            sender.sendMessage("§a取扱い種別:§6" + ms.type.name);
        } else {
            ItemStack is = new ItemStack(ms.getItem(), ms.getAmount(), ms.getDamage());
            sender.sendMessage("§a取扱い種別:§6" + ms.type.name);
            sender.sendMessage("§aアイテム:§6" + getInfo(is));
            sender.sendMessage("§a店主:§6" + ms.getOwner());
            sender.sendMessage("§a売値:§6" + ms.getSPrice());
            sender.sendMessage("§a買値:§6" + ms.getBPrice());
            sender.sendMessage("§a取引個数:§6" + ms.getAmount());
            if (ms.isEmpty() && ms.getSPrice() != 0) {
                sender.sendMessage("§c【販売不可】");
            } else if (ms.isFull() && ms.getBPrice() != 0) {
                sender.sendMessage("§c【買取不可】");
            }
        }
    }
}
