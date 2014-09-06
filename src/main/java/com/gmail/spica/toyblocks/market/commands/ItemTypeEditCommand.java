package com.gmail.spica.toyblocks.market.commands;

import com.gmail.spica.toyblocks.market.ItemType;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getItemType;
import static com.gmail.spica.toyblocks.market.util.FileUtil.removeItemType;
import static com.gmail.spica.toyblocks.market.util.FileUtil.writeItemType;
import com.gmail.spica.toyblocks.market.util.SerializableItem;
import com.gmail.spica.toyblocks.util.SerializableLocation;
import static java.lang.Integer.parseInt;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ItemTypeEditCommand {

    public static void onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender.hasPermission("market.command.itemtype.edit.loc")
                || sender.hasPermission("market.command.itemtype.edit.idel")
                || sender.hasPermission("market.command.itemtype.edit.name")
                || sender.hasPermission("market.command.itemtype.edit.limit"))) {
            sender.sendMessage("§c権限が不足しています!");
            return;
        }
        if (args.length < 4) {
            sender.sendMessage("§c/mk it edit <loc/idel/name/limit> <itemtype>");
            return;
        }
        ItemType it = getItemType(args[3]);
        if (it == null) {
            sender.sendMessage("§cItemTypeが見つかりませんでした!");
            return;
        }
        switch (args[2]) {
            case "loc":
                if (!sender.hasPermission("market.command.itemtype.edit.loc")) {
                    sender.sendMessage("§c権限が不足しています!");
                    return;
                }
                if (!(sender instanceof Player)) {
                    sender.sendMessage("§cコンソールからは実行できません!");
                    return;
                }
                 {
                    Player p = (Player) sender;
                    ItemType nit = new ItemType(new SerializableLocation(p.getLocation()), it.name, it.limit);
                    nit.setItems(it.getItems());
                    writeItemType(it.name, nit);
                    sender.sendMessage("§aDone!");
                    break;
                }
            case "idel":
                if (!sender.hasPermission("market.command.itemtype.edit.idel")) {
                    sender.sendMessage("§c権限が不足しています!");
                    return;
                }
                if (!(sender instanceof Player)) {
                    sender.sendMessage("§cコンソールからは実行できません!");
                    return;
                }
                 {
                    Player p = (Player) sender;
                    it.removeItem(new SerializableItem(p.getItemInHand().getType(), p.getItemInHand().getDurability()));
                    writeItemType(it.name, it);
                    sender.sendMessage("§aDone!");
                    break;
                }
            case "limit":
                if (!sender.hasPermission("market.command.itemtype.edit.limit")) {
                    sender.sendMessage("§c権限が不足しています!");
                    return;
                }
                if (args.length < 5) {
                    sender.sendMessage("§c/mk it edit limit <itemtype> <limit>");
                    return;
                }
                int limit;
                try {
                    limit = parseInt(args[4]);
                } catch (NumberFormatException e) {
                    sender.sendMessage("§c制限値が異常です!");
                    return;
                }
                 {
                    ItemType nit = new ItemType(it.loc, it.name, limit);
                    nit.setItems(it.getItems());
                    writeItemType(it.name, nit);
                    sender.sendMessage("§aDone!");
                    break;
                }
            case "name":
                if (!sender.hasPermission("market.command.itemtype.edit.limit")) {
                    sender.sendMessage("§c権限が不足しています!");
                    return;
                }
                if (args.length < 5) {
                    sender.sendMessage("§c/mk it edit limit <itemtype> <limit>");
                    return;
                }
                 {
                    ItemType nit = new ItemType(it.loc, args[4], it.limit);
                    removeItemType(args[3]);
                    nit.setItems(it.getItems());
                    writeItemType(args[4], nit);
                    sender.sendMessage("§aDone!");
                    break;
                }
        }
    }
}
