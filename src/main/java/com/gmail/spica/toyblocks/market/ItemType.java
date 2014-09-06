package com.gmail.spica.toyblocks.market;

import com.gmail.spica.toyblocks.market.util.SerializableItem;
import com.gmail.spica.toyblocks.util.SerializableLocation;
import java.io.Serializable;
import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemType implements Serializable {
    public final String name;
    private ArrayList<SerializableItem> bid = new ArrayList<>();
    public final SerializableLocation loc;
    public final int limit;

    public ItemType(SerializableLocation sloc, String str, int lmt) {
        name = str;
        loc = sloc;
        limit = lmt;
    }

    public void addItem(Material mtr, short damage) {
        bid.add(new SerializableItem(mtr, damage));
    }

    public boolean containsItem(Material mtr) {
        if (bid == null) {
            return false;
        }
        if (bid.isEmpty()) {
            return false;
        }
        for (int i = 0; i < bid.size() ; ++i) {
            if (bid.get(i).material == mtr) return true;
        }
        return false;
    }
    
    public ArrayList<SerializableItem> getItems() {
        return bid;
    }
    
    public ArrayList<ItemStack> getItemList() {
        ArrayList<ItemStack> is = new ArrayList<>();
        for (int i = 0; i < bid.size() ; ++i) {
            is.add(bid.get(i).deserialize());
        }
        return is;
    }
    
    public boolean removeItem(SerializableItem si) {
        if (!bid.contains(si)) {
            return false;
        }
        bid.remove(si);
        return true;
    }
    
    public void setItems(ArrayList<SerializableItem> il) {
        bid = il;
    }
}
