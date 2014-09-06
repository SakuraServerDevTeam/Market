package com.gmail.spica.toyblocks.market.commands;

import static com.gmail.spica.toyblocks.market.util.FileUtil.removeDummyMarketSign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class DmyRemoveCommand {

    public static void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("§c/market dmy remove <id>");
            return;
        }
        if (!sender.hasPermission("market.command.dummy.remove")) {
            sender.sendMessage("§c権限が不足しています!");
            return;
        }
        if (removeDummyMarketSign(args[2])) {
            sender.sendMessage("§a削除に成功しました!");
        } else {
            sender.sendMessage("§c削除に失敗しました!");
        }
    }
}
