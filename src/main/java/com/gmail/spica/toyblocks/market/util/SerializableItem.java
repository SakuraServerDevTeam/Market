package com.gmail.spica.toyblocks.market.util;

import java.io.Serializable;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SerializableItem implements Serializable {
    public Material material;
    public short damage;
    
    public SerializableItem(Material mtr, short dmg) {
        material = mtr;
        damage = dmg;
    }
    
    public ItemStack deserialize() {
        return new ItemStack(material, 1, damage);
    }
}
