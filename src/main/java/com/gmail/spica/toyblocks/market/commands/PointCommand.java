package com.gmail.spica.toyblocks.market.commands;

import com.gmail.spica.toyblocks.market.PlayerData;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getPlayerData;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getPlayerData;
import static com.gmail.spica.toyblocks.market.util.FileUtil.writePlayerData;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseInt;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PointCommand {

    public static void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cコンソールからは実行できません。");
            return;
        }
        Player p = (Player) sender;
        PlayerData pd = getPlayerData(p.getName());
        if (args.length < 2) {
            if (!p.hasPermission("market.command.point.check")) {
                sender.sendMessage("§c権限が不足しています!");
                return;
            }
            sender.sendMessage("§aあなたは今、§6"+pd.getPoint()+"ポイント§a持っています!");
            sender.sendMessage("§a1000ポイントごとに1000coinと引き換えられます(§c/mk p <枚数>§a)");
        } else {
            if (!p.hasPermission("market.command.point.ex")) {
                sender.sendMessage("§c権限が不足しています!");
                return;
            }
            int amount;
            try {
                amount = parseInt(args[1]);
            } catch (NumberFormatException e) {
                sender.sendMessage("§c引き換え数を数値で指定してください!");
                return;
            }
            if (pd.change(amount, p.getName())) {
                writePlayerData(p.getName(), pd);
                sender.sendMessage("§6"+amount+"回§a分の引き換えが完了しました!");
            } else {
                sender.sendMessage("§cその枚数を引き換えることはできません!");
            }
        }
    }
}
