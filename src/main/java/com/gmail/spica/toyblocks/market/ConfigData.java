package com.gmail.spica.toyblocks.market;

import com.Acrobot.ChestShop.Configuration.Properties;
import java.io.Serializable;
import org.bukkit.Bukkit;

public class ConfigData implements Serializable{
    public static String adminshop;
    public static double point;
    public static boolean ignoreAdminshop;
    public static int limit;
    
    public static void loadConfig() {
        adminshop = Properties.ADMIN_SHOP_NAME.replaceAll(" ", "");
        point = Bukkit.getPluginManager().getPlugin("Market").getConfig().getDouble("rate");
        ignoreAdminshop = Bukkit.getPluginManager().getPlugin("Market").getConfig().getBoolean("adminshop");
        limit = Bukkit.getPluginManager().getPlugin("Market").getConfig().getInt("limit");
    }
    
    public static void setAdminShop(boolean atai) {
        ignoreAdminshop = atai;
        Bukkit.getPluginManager().getPlugin("Market").getConfig().set("adminshop", atai);
        Bukkit.getPluginManager().getPlugin("Market").saveConfig();
    }
    
    public static void setRate(double atai) {
        point = atai;
        Bukkit.getPluginManager().getPlugin("Market").getConfig().set("rate", atai);
        Bukkit.getPluginManager().getPlugin("Market").saveConfig();
    }
    
    public static void setLimit(int atai) {
        limit = atai;
        Bukkit.getPluginManager().getPlugin("Market").getConfig().set("limit", atai);
        Bukkit.getPluginManager().getPlugin("Market").saveConfig();
    }
}
