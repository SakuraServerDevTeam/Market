package com.gmail.spica.toyblocks.market.listener;

import com.gmail.spica.toyblocks.market.commands.MarketSignInfoCommand;
import com.gmail.spica.toyblocks.market.commands.SearchBCommand;
import com.gmail.spica.toyblocks.market.commands.SearchPCommand;
import com.gmail.spica.toyblocks.market.commands.SearchSCommand;
import com.gmail.spica.toyblocks.market.commands.TpCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) {
            return;
        }
        if (e.getClickedBlock().getType() != Material.WALL_SIGN) {
            return;
        }
        Sign s = (Sign) e.getClickedBlock().getState();
        switch (ChatColor.stripColor(s.getLine(0)).replaceAll(" ", "").toLowerCase()) {
            case "[market]":
                if (e.getPlayer().hasPermission("market.sign.use.market")) {
                    if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        String[] args = new String[3];
                        args[2] = ChatColor.stripColor(s.getLine(3)).replaceAll(" ", "");
                        MarketSignInfoCommand.onCommand(e.getPlayer(), null, null, args, true);
                    }
                }
                break;
            case "[search-item]":
                if (e.getPlayer().hasPermission("market.sign.use.search")) {
                    String[] args;
                    if (ChatColor.stripColor(s.getLine(2)).replaceAll(" ", "").equals("")) {
                        args = new String[4];
                        args[3] = ChatColor.stripColor(s.getLine(2)).replaceAll(" ", "");
                    } else {
                        args = new String[3];
                    }
                    args[2] = ChatColor.stripColor(s.getLine(1)).replaceAll(" ", "");
                    if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        SearchBCommand.onCommand(e.getPlayer(), null, null, args, true);
                    } else if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
                        SearchSCommand.onCommand(e.getPlayer(), null, null, args, true);
                    }
                }
                break;
            case "[search-p]":
                if (e.getPlayer().hasPermission("market.sign.use.searchp")) {
                    if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        String[] args;
                        if (s.getLine(2).equals("")) {
                            args = new String[3];
                            args[2] = s.getLine(1);
                            SearchPCommand.onCommand(e.getPlayer(), null, null, args, true);
                        } else {
                            args = new String[4];
                            args[2] = ChatColor.stripColor(s.getLine(1)).replaceAll(" ", "");
                            args[3] = ChatColor.stripColor(s.getLine(2)).replaceAll(" ", "");
                            SearchPCommand.onCommand(e.getPlayer(), null, null, args, true);
                        }
                    }
                }
                break;
            case "[item-tp]":
                if (e.getPlayer().hasPermission("market.sign.use.tp")) {
                    if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        String[] args = new String[2];
                        args[1] = ChatColor.stripColor(s.getLine(1)).replaceAll(" ", "");
                        TpCommand.onCommand(e.getPlayer(), null, null, args, true);
                    }
                }
                break;
        }
    }
}
