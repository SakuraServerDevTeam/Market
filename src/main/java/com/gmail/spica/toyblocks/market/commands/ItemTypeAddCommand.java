package com.gmail.spica.toyblocks.market.commands;

import com.gmail.spica.toyblocks.market.ItemType;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getItemType;
import static com.gmail.spica.toyblocks.market.util.FileUtil.writeItemType;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ItemTypeAddCommand {

    public static void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("§c/mk itemtype add <itemtype>");
            return;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cプレイヤーのみ実行できます");
            return;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("market.command.itemtype.add")) {
            sender.sendMessage("§c権限が不足しています!");
            return;
        }
        ItemType it = getItemType(args[2]);
        if (it == null) {
            sender.sendMessage("§cそのItemTypeは存在しません!");
            return;
        }
        if (p.getItemInHand().getType() == Material.AIR) {
            sender.sendMessage("§c手に追加アイテムを持って下さい!");
            return;
        }
        it.addItem(p.getItemInHand().getType(), p.getItemInHand().getDurability());
        if (writeItemType(args[2], it)) {
            sender.sendMessage("§a完了しました!");
            return;
        }
        sender.sendMessage("§cファイルエラー");
    }
}
