package com.gmail.spica.toyblocks.market.commands;

import com.gmail.spica.toyblocks.market.ConfigData;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class SetCommand {

    public static void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("§c/mk set <adminshop/rate/limit> <値>");
            return;
        }
        switch (args[1]) {
            case "adminshop":
                if (!sender.hasPermission("market.command.set.adminshop")) {
                    sender.sendMessage("§c権限が不足しています!");
                    return;
                }
                if (args[2].equals("true")) {
                    ConfigData.setAdminShop(true);
                    sender.sendMessage("§aDone!");
                } else if (args[2].equals("false")) {
                    ConfigData.setAdminShop(false);
                    sender.sendMessage("§aDone!");
                } else {
                    sender.sendMessage("§c値が不正です!");
                }
                break;
            case "rate":
                if (!sender.hasPermission("market.command.set.rate")) {
                    sender.sendMessage("§c権限が不足しています!");
                    return;
                }
                double rate;
                try {
                    rate = parseDouble(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage("§c値が不正です!");
                    return;
                }
                ConfigData.setRate(rate);
                sender.sendMessage("§aDone!");
                break;
            case "limit":
                if (!sender.hasPermission("market.command.set.limit")) {
                    sender.sendMessage("§c権限が不足しています!");
                    return;
                }
                int limit;
                try {
                    limit = parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage("§c値が不正です!");
                    return;
                }
                ConfigData.setLimit(limit);
                sender.sendMessage("§aDone!");
                break;
        }
    }
}
