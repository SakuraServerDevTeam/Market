package com.gmail.spica.toyblocks.market.util;

import static com.Acrobot.ChestShop.Signs.ChestShopSign.isValid;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

public class BlockUtil {

    private static final BlockFace[] BLOCKS_AROUND = {BlockFace.UP, BlockFace.DOWN, BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH};

    public static boolean isMarketShop(org.bukkit.block.Sign shopsign, boolean skipShopCheck) {
        if (!skipShopCheck) {
            if (!isValid(shopsign)) {
                return false;
            }
        }
        if (shopsign.getBlock().getType() != Material.WALL_SIGN) {
            return false;
        }
        if (getOppSideSign(shopsign) == null) {
            return false;
        }
        org.bukkit.block.Sign marketsign = getOppSideSign(shopsign);
        return ChatColor.stripColor(marketsign.getLine(0)).toLowerCase().equals("[market]");
    }

    public static boolean isMarketShop(org.bukkit.block.Sign s) {
        return isMarketShop(s, false);
    }

    public static org.bukkit.block.Sign getOppSideSign(org.bukkit.block.Sign signblock1) {
        org.bukkit.material.Sign signdata1 = (org.bukkit.material.Sign) signblock1.getBlock().getState().getData();
        org.bukkit.block.Block block2 = signblock1.getBlock().getRelative(signdata1.getAttachedFace(), 2);
        if (block2.getType() != Material.WALL_SIGN) {
            return null;
        }
        org.bukkit.block.Sign signblock2 = (org.bukkit.block.Sign) block2.getState();
        org.bukkit.material.Sign signdata2 = (org.bukkit.material.Sign) signblock2.getData();
        if (signdata2.getAttachedFace().getOppositeFace() != signdata1.getAttachedFace()) {
            return null;
        }
        return signblock2;
    }

    public static ArrayList<org.bukkit.block.Sign> getConnectedSigns(Block b) {
        ArrayList<org.bukkit.block.Sign> result = new ArrayList<>();
        for (BlockFace face : BLOCKS_AROUND) {
            if (b.getRelative(face).getType() == Material.WALL_SIGN) {
                org.bukkit.block.Sign sign = (org.bukkit.block.Sign) b.getRelative(face).getState();
                if (isMarketShop(sign)) {
                    result.add(sign);
                }
            }
        }
        return result;
    }

    public static boolean canBreak(Player p, Block b) {
        boolean market = false, search = false, tp = false, sp = false, np = false;
        if (b.getType() == Material.WALL_SIGN) {
            switch (((Sign) b.getState()).getLine(0)) {
                case "[market]":
                    market = true;
                    break;
                case "[search-item]":
                    search = true;
                    break;
                case "[item-tp]":
                    tp = true;
                    break;
                case "[search-p]":
                    sp = true;
                    break;
            }
        }
        if (b.getRelative(BlockFace.WEST).getType() == Material.WALL_SIGN) {
            if (((org.bukkit.material.Sign) b.getRelative(BlockFace.WEST).getState().getData()).getAttachedFace() == BlockFace.EAST) {
                String line1 = ChatColor.stripColor(((Sign) b.getRelative(BlockFace.WEST).getState()).getLine(0)).toLowerCase();
                switch (line1) {
                    case "[market]":
                        np = true;
                        break;
                    case "[search-item]":
                        np = true;
                        break;
                    case "[item-tp]":
                        np = true;
                        break;
                    case "[search-p]":
                        np = true;
                        break;
                }
            }
        }
        if (b.getRelative(BlockFace.EAST).getType() == Material.WALL_SIGN) {
            if (((org.bukkit.material.Sign) b.getRelative(BlockFace.EAST).getState().getData()).getAttachedFace() == BlockFace.WEST) {
                String line1 = ChatColor.stripColor(((Sign) b.getRelative(BlockFace.EAST).getState()).getLine(0)).toLowerCase();
                switch (line1) {
                    case "[market]":
                        np = true;
                        break;
                    case "[search-item]":
                        np = true;
                        break;
                    case "[item-tp]":
                        np = true;
                        break;
                    case "[search-p]":
                        np = true;
                        break;
                }
            }
        }
        if (b.getRelative(BlockFace.NORTH).getType() == Material.WALL_SIGN) {
            if (((org.bukkit.material.Sign) b.getRelative(BlockFace.NORTH).getState().getData()).getAttachedFace() == BlockFace.SOUTH) {
                String line1 = ChatColor.stripColor(((Sign) b.getRelative(BlockFace.NORTH).getState()).getLine(0)).toLowerCase();
                switch (line1) {
                    case "[market]":
                        np = true;
                        break;
                    case "[search-item]":
                        np = true;
                        break;
                    case "[item-tp]":
                        np = true;
                        break;
                    case "[search-p]":
                        np = true;
                        break;
                }
            }
        }
        if (b.getRelative(BlockFace.SOUTH).getType() == Material.WALL_SIGN) {
            if (((org.bukkit.material.Sign) b.getRelative(BlockFace.SOUTH).getState().getData()).getAttachedFace() == BlockFace.NORTH) {
                String line1 = ChatColor.stripColor(((Sign) b.getRelative(BlockFace.SOUTH).getState()).getLine(0)).toLowerCase();
                switch (line1) {
                    case "[market]":
                        np = true;
                        break;
                    case "[search-item]":
                        np = true;
                        break;
                    case "[item-tp]":
                        np = true;
                        break;
                    case "[search-p]":
                        np = true;
                        break;
                }
            }
        }
        if (p.hasPermission("market.sign.break.market")) {
            market = false;
        }
        if (p.hasPermission("market.sign.break.search")) {
            search = false;
        }
        if (p.hasPermission("market.sign.break.tp")) {
            tp = false;
        }
        if (p.hasPermission("market.sign.break.searchp")) {
            tp = false;
        }
        return !(market || search || tp || np);
    }
}
