package com.gmail.spica.toyblocks.market.commands;

import static com.Acrobot.ChestShop.Signs.ChestShopSign.isValid;
import com.gmail.spica.toyblocks.market.ItemType;
import com.gmail.spica.toyblocks.market.MarketSign;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getDummyMarketSign;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getItemType;
import static com.gmail.spica.toyblocks.market.util.FileUtil.writeDummyMarketSign;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DmyCreateCommand {

    public static void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 4) {
            sender.sendMessage("§c/market dmy create <id> <itemtype>");
            return;
        }
        if (args.length == 4) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cコンソールからは実行できません!");
                return;
            }
            Player p = (Player) sender;
            if (!p.hasPermission("market.command.dummy.create")) {
                p.sendMessage("§c権限が不足しています!");
                return;
            }
            Block b = p.getTargetBlock(null, 5);
            if (b == null) {
                p.sendMessage("§cブロックが見つかりませんでした!");
                return;
            }
            if (!(b.getState() instanceof Sign)) {
                p.sendMessage("§c看板が見つかりませんでした!");
                return;
            }
            Sign s = (Sign) b.getState();
            if (!isValid(s)) {
                p.sendMessage("§cその看板はチェストショップではありません!");
                return;
            }
            ItemType it = getItemType(args[3]);
            if (it == null) {
                p.sendMessage("§cそのItemTypeはありません!");
                return;
            }
            if (getDummyMarketSign(args[2]) != null) {
                p.sendMessage("§cそのダミーは既存です!");
                return;
            }
            MarketSign ms = new MarketSign(it.name);
            ms.upDateSign(s.getLine(0), s.getLine(1), s.getLine(2), s.getLine(3));
            ms.writeStatus(false, false);
            if (writeDummyMarketSign(args[2], ms)) {
                p.sendMessage("§a完了しました!");
            } else {
                p.sendMessage("§cファイルエラー!");
            }
        } else {
            sender.sendMessage("§c/market dmy create <id> <itemtype>");
        }
    }
}
