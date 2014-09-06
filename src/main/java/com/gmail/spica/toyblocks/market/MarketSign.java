package com.gmail.spica.toyblocks.market;

import com.Acrobot.Breeze.Utils.MaterialUtil;
import static com.Acrobot.Breeze.Utils.PriceUtil.getBuyPrice;
import static com.Acrobot.Breeze.Utils.PriceUtil.getSellPrice;
import static com.Acrobot.Breeze.Utils.PriceUtil.hasBuyPrice;
import static com.Acrobot.Breeze.Utils.PriceUtil.hasSellPrice;
import static com.gmail.spica.toyblocks.market.util.FileUtil.getItemType;
import java.io.Serializable;
import static java.lang.Integer.parseInt;
import org.bukkit.Material;

public class MarketSign implements Serializable {

    public final ItemType type;
    private int amount;
    private short damage;
    private Material item;
    private String name;
    private double buyprice, sellprice;
    private boolean empty, full;

    public MarketSign(String str) {
        type = getItemType(str);
        empty = true;
        full = false;
    }
    
    public double getSPrice() {
        return sellprice;
    }
    
    public double getBPrice() {
        return buyprice;
    }
    
    public Material getItem() {
        return item;
    }
    
    public int getAmount(){
        return amount;
    }
    
    public short getDamage() {
        return damage;
    }
    
    public String getOwner() {
        return name;
    }

    public void upDateSign(String l1, String l2, String l3, String l4) {
        name = l1;
        if (hasBuyPrice(l3)) {
            buyprice = getBuyPrice(l3);
        } else {
            buyprice = 0;
        }
        if (hasSellPrice(l3)) {
            sellprice = getSellPrice(l3);
        } else {
            sellprice = 0;
        }
        try {
            amount = parseInt(l2);
        } catch (NumberFormatException e) {
            amount = 0;
        }
        item = MaterialUtil.getItem(l4).getType();
        damage = MaterialUtil.getItem(l4).getDurability();
    }
    
    public void reset() {
        buyprice = 0;
        sellprice = 0;
        amount = 0;
        damage = 0;
        name = null;
        item = null;
    }

    public boolean isValid() {
        if (amount < 1) {
            return false;
        }
        if (sellprice < 1 && buyprice < 1) {
            return false;
        }
        if (type == null) {
            return false;
        }
        return item != null;
    }
    
    public void writeStatus(boolean e,boolean f) {
        empty = e;
        full = f;
    }
    
    public boolean isEmpty() {
        return empty;
    }
    
    public boolean isFull() {
        return full;
    }
}
