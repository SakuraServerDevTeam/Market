package com.gmail.spica.toyblocks.market.commands;

import static com.gmail.spica.toyblocks.localizename.Main.getInfo;
import com.gmail.spica.toyblocks.market.MarketSign;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getDummyIDList;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getDummyMarketSign;
import java.util.ArrayList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

public class DmyInfoCommand {

    public static void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("market.command.dummy.info")) {
            sender.sendMessage("§c権限が不足しています!");
            return;
        }
        if (args.length < 3) {
            ArrayList<String> msl = getDummyIDList();
            if (msl.isEmpty()) {
                sender.sendMessage("§cデータが1つも見つかりませんでした!");
            }
            for (int i = 0; i < msl.size(); ++i) {
                sender.sendMessage("§6" + (i + 1) + ":§a" + msl.get(i).replaceAll("DMY_", ""));
            }
            return;
        }
        MarketSign ms = getDummyMarketSign(args[2]);
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
        }
    }
}
