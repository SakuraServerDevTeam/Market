package com.gmail.spica.toyblocks.market.commands;

import static com.gmail.spica.toyblocks.market.util.FileUtil.removeItemType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ItemTypeRemoveCommand {

    public static void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("§c/mk itemtype remove <itemtype>");
            return;
        }
        if (sender instanceof Player) {
            if (!((Player) sender).hasPermission("market.command.itemtype.remove")) {
                sender.sendMessage("§c権限が不足しています!");
                return;
            }
        }
        if (removeItemType(args[2])) {
            sender.sendMessage("§a成功しました!");
            return;
        }
        sender.sendMessage("§cファイルエラー");
    }
}
