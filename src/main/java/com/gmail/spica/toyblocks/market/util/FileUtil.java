package com.gmail.spica.toyblocks.market.util;

import com.gmail.spica.toyblocks.market.ItemType;
import com.gmail.spica.toyblocks.market.MarketSign;
import com.gmail.spica.toyblocks.market.PlayerData;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import org.bukkit.Bukkit;

public class FileUtil {

    private static final File dataFolder = Bukkit.getPluginManager().getPlugin("Market").getDataFolder();
    private static final File itemtypeDataFolder = new File(dataFolder + "/itemtypes");
    private static final File marketDataFolder = new File(dataFolder + "/shopdata");
    private static final File playerDataFolder = new File(dataFolder + "/players");

    public static void createFolder() {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        if (!itemtypeDataFolder.exists()) {
            itemtypeDataFolder.mkdirs();
        }
        if (!marketDataFolder.exists()) {
            marketDataFolder.mkdirs();
        }
        if (!playerDataFolder.exists()) {
            playerDataFolder.mkdirs();
        }
    }

    public static MarketSign getMarketSign(String name) {
        try {
            FileInputStream inFile = new FileInputStream(new File(marketDataFolder, name + ".market"));
            ObjectInputStream in = new ObjectInputStream(inFile);
            return (MarketSign) in.readObject();
        } catch (FileNotFoundException ex) {
            return null;
        } catch (IOException | ClassNotFoundException ex) {
            return null;
        }
    }

    public static MarketSign getDummyMarketSign(String name) {
        return getMarketSign("DMY_" + name);
    }

    public static PlayerData getPlayerData(String name, boolean create) {
        try {
            FileInputStream inFile = new FileInputStream(new File(playerDataFolder, name + ".data"));
            ObjectInputStream in = new ObjectInputStream(inFile);
            return (PlayerData) in.readObject();
        } catch (FileNotFoundException ex) {
            if (create) {
                return createPlayerData(name);
            }
            return null;
        } catch (IOException | ClassNotFoundException ex) {
            if (create) {
                return createPlayerData(name);
            }
            return null;
        }
    }

    public static PlayerData getPlayerData(String name) {
        return getPlayerData(name, true);
    }

    public static PlayerData createPlayerData(String name) {
        PlayerData pd = new PlayerData();
        if (writePlayerData(name, pd)) {
            return pd;
        } else {
            return null;
        }
    }

    public static ItemType getItemType(String name) {
        try {
            FileInputStream inFile = new FileInputStream(new File(itemtypeDataFolder, name + ".itemtype"));
            ObjectInputStream in = new ObjectInputStream(inFile);
            return (ItemType) in.readObject();
        } catch (FileNotFoundException ex) {
            return null;
        } catch (IOException | ClassNotFoundException ex) {
            return null;
        }
    }

    public static ArrayList<ItemType> getItemTypeList() {
        ArrayList<ItemType> result = new ArrayList<>();
        File[] files = itemtypeDataFolder.listFiles();
        for (int i = 0; i < files.length; ++i) {
            try {
                FileInputStream fos = new FileInputStream(files[i]);
                ObjectInputStream ois = new ObjectInputStream(fos);
                result.add((ItemType) ois.readObject());
            } catch (IOException | ClassNotFoundException ex) {
                return null;
            }
        }
        return result;
    }

    public static ArrayList<MarketSign> getMarketSignList() {
        ArrayList<MarketSign> result = new ArrayList<>();
        File[] files = marketDataFolder.listFiles();
        for (int i = 0; i < files.length; ++i) {
            try {
                FileInputStream fos = new FileInputStream(files[i]);
                ObjectInputStream ois = new ObjectInputStream(fos);
                result.add((MarketSign) ois.readObject());
            } catch (IOException | ClassNotFoundException ex) {
                return null;
            }
        }
        return result;
    }

    public static ArrayList<String> getDummyIDList() {
        ArrayList<String> result = new ArrayList<>();
        File[] files = marketDataFolder.listFiles();
        for (int i = 0; i < files.length; ++i) {
            if (files[i].getName().startsWith("DMY_")) {
                result.add(files[i].getName().replaceAll(".market", ""));
            }
        }
        return result;
    }

    public static ArrayList<String> getIDList() {
        ArrayList<String> result = new ArrayList<>();
        File[] files = marketDataFolder.listFiles();
        for (int i = 0; i < files.length; ++i) {
            if (!files[i].getName().startsWith("DMY_")) {
                result.add(files[i].getName().replaceAll(".market", ""));
            }
        }
        return result;
    }

    public static ArrayList<String> getItemTypeNameList() {
        ArrayList<String> result = new ArrayList<>();
        File[] files = itemtypeDataFolder.listFiles();
        for (int i = 0; i < files.length; ++i) {
            result.add(files[i].getName().replaceAll(".itemtype", ""));
        }
        return result;
    }

    public static boolean writeItemType(String name, ItemType it) {
        try {
            File file = new File(itemtypeDataFolder, name + ".itemtype");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(it);
        } catch (FileNotFoundException ex) {
            return false;
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    public static boolean writeMarketSign(String name, MarketSign ms) {
        try {
            File file = new File(marketDataFolder, name + ".market");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(ms);
        } catch (FileNotFoundException ex) {
            return false;
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    public static boolean writePlayerData(String name, PlayerData pd) {
        try {
            File file = new File(playerDataFolder, name + ".data");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(pd);
        } catch (FileNotFoundException ex) {
            return false;
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    public static boolean writeDummyMarketSign(String name, MarketSign ms) {
        return writeMarketSign("DMY_" + name, ms);
    }

    public static boolean removeItemType(String name) {
        File file = new File(itemtypeDataFolder, name + ".itemtype");
        if (!file.exists()) {
            return false;
        }
        return file.delete();
    }

    public static boolean removeMarketSign(String name) {
        File file = new File(marketDataFolder, name + ".market");
        if (!file.exists()) {
            return false;
        }
        return file.delete();
    }

    public static boolean removeDummyMarketSign(String name) {
        return removeMarketSign("DMY_" + name);
    }
}
