package com.gmail.spica.toyblocks.market.commands;

import com.gmail.spica.toyblocks.market.ItemType;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getItemType;
import static org.bukkit.Bukkit.getPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpCommand {

    public static void onCommand(CommandSender sender, Command cmd, String label, String[] args, boolean ignorePerm) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cプレイヤーからのみ実行できます!");
            return;
        }
        Player p;
        if (args.length < 3) {
            if (!ignorePerm) {
                if (!sender.hasPermission("market.command.tp.own")) {
                    sender.sendMessage("§c権限が不足しています!");
                    return;
                }
            }
            p = (Player) sender;
        } else {
            if (!sender.hasPermission("market.command.tp.others")) {
                sender.sendMessage("§c権限が不足しています!");
                return;
            }
            p = getPlayer(args[2]);
            if (p == null) {
                sender.sendMessage("§cプレイヤーが見つかりません!");
                return;
            }
        }
        ItemType it = getItemType(args[1]);
        if (it == null) {
            sender.sendMessage("§cItemTypeが見つかりません!");
            return;
        }
        p.teleport(it.loc.deserializeLocation());
    }
}
