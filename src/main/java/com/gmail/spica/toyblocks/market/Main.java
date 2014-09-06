package com.gmail.spica.toyblocks.market;

import static com.gmail.spica.toyblocks.market.ConfigData.loadConfig;
import com.gmail.spica.toyblocks.market.commands.CheckCommand;
import com.gmail.spica.toyblocks.market.commands.DmyCreateCommand;
import com.gmail.spica.toyblocks.market.commands.DmyInfoCommand;
import com.gmail.spica.toyblocks.market.commands.DmyRemoveCommand;
import com.gmail.spica.toyblocks.market.commands.ItemTypeAddCommand;
import com.gmail.spica.toyblocks.market.commands.ItemTypeCreateCommand;
import com.gmail.spica.toyblocks.market.commands.ItemTypeEditCommand;
import com.gmail.spica.toyblocks.market.commands.ItemTypeInfoCommand;
import com.gmail.spica.toyblocks.market.commands.ItemTypeRemoveCommand;
import com.gmail.spica.toyblocks.market.commands.MarketSignInfoCommand;
import com.gmail.spica.toyblocks.market.commands.MarketSignRemoveCommand;
import com.gmail.spica.toyblocks.market.commands.PlayerDataInfoCommand;
import com.gmail.spica.toyblocks.market.commands.PointCommand;
import com.gmail.spica.toyblocks.market.commands.SearchBCommand;
import com.gmail.spica.toyblocks.market.commands.SearchPCommand;
import com.gmail.spica.toyblocks.market.commands.SearchSCommand;
import com.gmail.spica.toyblocks.market.commands.SetCommand;
import com.gmail.spica.toyblocks.market.commands.TpCommand;
import com.gmail.spica.toyblocks.market.listener.BlockBreakListener;
import com.gmail.spica.toyblocks.market.listener.InventoryCloseListener;
import com.gmail.spica.toyblocks.market.listener.PlayerInteractListener;
import com.gmail.spica.toyblocks.market.listener.PreShopCreationListener;
import com.gmail.spica.toyblocks.market.listener.ShopCreatedListener;
import com.gmail.spica.toyblocks.market.listener.ShopDestroyedListener;
import com.gmail.spica.toyblocks.market.listener.SignChangeListener;
import com.gmail.spica.toyblocks.market.listener.SignChangeListener2;
import com.gmail.spica.toyblocks.market.listener.SignChangeListener3;
import com.gmail.spica.toyblocks.market.listener.SignChangeListener4;
import com.gmail.spica.toyblocks.market.listener.TranscationListener;
import static com.gmail.spica.toyblocks.market.util.FileUtil.createFolder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("The Market plugin has been loaded");
        saveDefaultConfig();
        loadConfig();
        createFolder();
        getServer().getPluginManager().registerEvents(new ShopCreatedListener(), this);
        getServer().getPluginManager().registerEvents(new SignChangeListener(), this);
        getServer().getPluginManager().registerEvents(new SignChangeListener2(), this);
        getServer().getPluginManager().registerEvents(new SignChangeListener3(), this);
        getServer().getPluginManager().registerEvents(new SignChangeListener4(), this);
        getServer().getPluginManager().registerEvents(new PreShopCreationListener(), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryCloseListener(), this);
        getServer().getPluginManager().registerEvents(new ShopDestroyedListener(), this);
        getServer().getPluginManager().registerEvents(new TranscationListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("The Market plugin has been unloaded");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("§c/market <it/ms/pd/tp/c/s/p/dmy>");
            return true;
        }
        switch (args[0]) {
            //<editor-fold defaultstate="collapsed" desc="ItemType処理">
            case "it":
                if (args.length < 2) {
                    sender.sendMessage("§c/market it <create/remove/add/info/edit> <itemtype>");
                    return true;
                }
                switch (args[1]) {
                    case "create":
                        ItemTypeCreateCommand.onCommand(sender, cmd, label, args);
                        break;
                    case "remove":
                        ItemTypeRemoveCommand.onCommand(sender, cmd, label, args);
                        break;
                    case "add":
                        ItemTypeAddCommand.onCommand(sender, cmd, label, args);
                        break;
                    case "info":
                        ItemTypeInfoCommand.onCommand(sender, cmd, label, args);
                        break;
                    case "edit":
                        ItemTypeEditCommand.onCommand(sender, cmd, label, args);
                        break;
                    default:
                        sender.sendMessage("§c/market it <create/remove/add/info/edit> <itemtype>");
                }
                return true;
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="MarketSign処理">
            case "ms":
                if (args.length < 2) {
                    sender.sendMessage("§c/market ms <info/remove> <id>");
                    return true;
                }
                switch (args[1]) {
                    case "info":
                        MarketSignInfoCommand.onCommand(sender, cmd, label, args, false);
                        break;
                    case "remove":
                        MarketSignRemoveCommand.onCommand(sender, cmd, label, args);
                        break;
                    default:
                        sender.sendMessage("§c/market ms <info/remove> <id>");
                }
                return true;
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="PlayerData処理">
            case "pd":
                if (args.length < 2) {
                    sender.sendMessage("§c/market pd <info> <id>");
                    return true;
                }
                switch (args[1]) {
                    case "info":
                        PlayerDataInfoCommand.onCommand(sender, cmd, label, args);
                        break;
                    default:
                        sender.sendMessage("§c/market pd <info> <id>");
                }
                return true;
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Tp処理">
            case "tp":
                if (args.length < 2) {
                    sender.sendMessage("§c/market tp <itemtype>");
                    return true;
                }
                TpCommand.onCommand(sender, cmd, label, args, false);
                return true;
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Search処理">
            case "s":
                if (args.length < 2) {
                    sender.sendMessage("§c/market s <b(売値)/s(買値)/p(店主)> <アイテムID/プレイヤー名>");
                    return true;
                }
                switch (args[1]) {
                    case "s":
                        SearchSCommand.onCommand(sender, cmd, label, args, false);
                        break;
                    case "b":
                        SearchBCommand.onCommand(sender, cmd, label, args, false);
                        break;
                    case "p":
                        SearchPCommand.onCommand(sender, cmd, label, args, false);
                        break;
                    default:
                        sender.sendMessage("§c/market s <b(売値)/s(買値)/p(店主)> <アイテムID/プレイヤー名>");
                }
                return true;
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Check処理">
            case "c":
                CheckCommand.onCommand(sender, cmd, label, args);
                return true;
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Point処理">
            case "p":
                PointCommand.onCommand(sender, cmd, label, args);
                return true;
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Set処理">
            case "set":
                SetCommand.onCommand(sender, cmd, label, args);
                return true;
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Dummy処理">
            case "dmy":
                if (args.length < 2) {
                    sender.sendMessage("§c/market dmy <info/remove/create>");
                    return true;
                }
                switch (args[1]) {
                    case "create":
                        DmyCreateCommand.onCommand(sender, cmd, label, args);
                        break;
                    case "remove":
                        DmyRemoveCommand.onCommand(sender, cmd, label, args);
                        break;
                    case "info":
                        DmyInfoCommand.onCommand(sender, cmd, label, args);
                        break;
                    default:
                        sender.sendMessage("§c/market dmy <info/remove/create>");
                }
                return true;
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Default処理">
            default:
                sender.sendMessage("§c/market <it/ms/pd/tp/c/s/p/dmy>");
                return true;
//</editor-fold>
        }
    }
}
