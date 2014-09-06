package com.gmail.spica.toyblocks.market.commands;

import static com.gmail.spica.toyblocks.market.util.FileUtil.removeMarketSign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class MarketSignRemoveCommand {
    public static void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("market.command.marketsign.remove")) {
            sender.sendMessage("§c権限が不足しています!");
            return;
        }
        if (removeMarketSign(args[2])) {
            sender.sendMessage("§c削除に成功しました!今後その看板は使用できませんX(");
        } else {
            sender.sendMessage("§c削除に失敗しました!");
        }
    }
}
