package com.gmail.spica.toyblocks.market.commands;

import com.gmail.spica.toyblocks.market.ItemType;
import com.gmail.spica.toyblocks.market.PlayerData;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getItemType;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getPlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerDataInfoCommand {

    public static void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        PlayerData pd;
        String name;
        if (args.length < 3) {
            if (!sender.hasPermission("market.command.playerdata.info.own")) {
                sender.sendMessage("権限が不足しています!");
                return;
            }
            if (sender instanceof Player) {
                name = ((Player) sender).getName();
                pd = getPlayerData(name);
            } else {
                sender.sendMessage("§c/market pd info <player>");
                return;
            }
        } else {
            if (args.length > 3) {
                if (!sender.hasPermission("market.command.playerdata.info.others")) {
                    sender.sendMessage("権限が不足しています!");
                    return;
                }
                ItemType it = getItemType(args[3]);
                if (it == null) {
                    sender.sendMessage("ItemTypeが存在しません!");
                    return;
                }
                name = args[2];
                pd = getPlayerData(name, false);
                if (pd == null) {
                    sender.sendMessage("§cデータがありません!");
                    return;
                }
                sender.sendMessage("§6" + name + "§aの情報");
                sender.sendMessage("§a" + args[3] + "のショップ数:§6" + pd.getCount(it));
                return;
            }
            if (!sender.hasPermission("market.command.playerdata.info.others")) {
                sender.sendMessage("§c権限が不足しています!");
                return;
            }
            name = args[2];
            pd = getPlayerData(name, false);
            if (pd == null) {
                sender.sendMessage("§cデータがありません!");
                return;
            }
        }
        sender.sendMessage("§6" + name + "§aの情報");
        sender.sendMessage("§a所持ポイント:§6" + pd.getPoint());
        sender.sendMessage("§aショップ数合計:§6" + pd.getTotalCount());
    }
}
