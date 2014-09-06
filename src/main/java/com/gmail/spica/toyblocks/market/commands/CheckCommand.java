package com.gmail.spica.toyblocks.market.commands;

import com.gmail.spica.toyblocks.market.ItemType;
import com.gmail.spica.toyblocks.market.PlayerData;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getPlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CheckCommand {

    public static void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cコンソールからは実行できません!");
            return;
        }
        if (!sender.hasPermission("market.command.check")) {
            sender.sendMessage("§c権限が足りません!");
            return;
        }
        Player p = (Player) sender;
        PlayerData pd = getPlayerData(p.getName());
        ItemType it = pd.getLastest();
        if (it == null) {
            sender.sendMessage("§c対象がありません!");
            return;  
        }
        if (it.loc == null) {
            sender.sendMessage("§c対象がありません!");
            return;  
        }
        p.teleport(it.loc.deserializeLocation());
        p.sendMessage("§aテレポートしました!");
    }
}
