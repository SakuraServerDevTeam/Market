package com.gmail.spica.toyblocks.market;

import static com.gmail.spica.toyblocks.util.EconUtil.giveMoney;
import java.io.Serializable;
import static java.lang.Math.floor;
import java.util.HashMap;

public class PlayerData implements Serializable {
    private int point;
    private HashMap<String, Integer> count = new HashMap<>();
    private ItemType teleport;
    
    public void setLastest(ItemType it) {
        teleport = it;
    }
    
    public ItemType getLastest() {
        ItemType it = teleport;
        teleport = null;
        return it;
    }
    
    public int getCount(String str) {
        if (count.get(str)==null) {
            count.put(str, 0);
            return 0;
        }
        return count.get(str);
    }
    
    public int getCount(ItemType it) {
        return getCount(it.name);
    } 
    
    public int getTotalCount() {
        if (count.isEmpty()) {
            return 0;
        }
        int total = 0;
        for (String key : count.keySet()) {
            total = total + getCount(key);
        }
        return total;
    }
    
    public void addCount(ItemType it) {
        int def = getCount(it);
        count.put(it.name, def +1);
    }
    
    public void removeCount(ItemType it) {
        int def = getCount(it);
        count.put(it.name, def - 1);
    }
    
    public int getPoint() {
        return point;
    }
    
    public boolean addPoint(double price) {
        int add = (int) floor(price * ConfigData.point / 100);
        point = point + add;
        return !(add == 0);
    }
    
    public boolean change(int i, String name) {
        double can = floor(point / 1000);
        if (can < i) {
            return false;
        }
        if (giveMoney(name, i*1000)) {
        point = point - (i * 1000);
        return true;
        }
        return false;
    }
}
