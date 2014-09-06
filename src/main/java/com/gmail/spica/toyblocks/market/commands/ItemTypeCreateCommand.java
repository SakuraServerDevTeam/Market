package com.gmail.spica.toyblocks.market.commands;

import com.gmail.spica.toyblocks.market.ItemType;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getItemType;
import static com.gmail.spica.toyblocks.market.util.FileUtil.writeItemType;
import com.gmail.spica.toyblocks.util.SerializableLocation;
import static java.lang.Integer.parseInt;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ItemTypeCreateCommand {

    public static void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 4) {
            sender.sendMessage("§c/mk <name> <limit>§6をtp位置で実行してください!");
            return;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c/mk <name>§6をtpの位置で実行してください!");
            return;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("market.command.itemtype.add")) {
            sender.sendMessage("§c権限が不足しています!");
            return;
        }
        if (getItemType(args[2]) != null) {
            sender.sendMessage("§cそのItemTypeは既存です。");
            return;
        }
        int limit;
        try {
            limit = parseInt(args[3]);
        } catch(NumberFormatException e) {
            sender.sendMessage("§cその数値は異常です。");
            return;
        }
        ItemType it = new ItemType(new SerializableLocation(p.getLocation()), args[2], limit);
        if (writeItemType(args[2], it)) {
            sender.sendMessage("§a登録が完了しました!");
            return;
        }
        sender.sendMessage("§cファイルエラー");
    }
}
