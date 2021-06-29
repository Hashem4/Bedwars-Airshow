package com.sprintkeyz.bedwars.inventories.traps;

import com.sprintkeyz.bedwars.managers.CreateItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrapsInventory implements InventoryHolder {

    private Inventory inv;

    ItemStack itsatrap;
    ItemStack counteroffensive;
    ItemStack alarm;
    ItemStack minerfatigue;
    ItemStack back;

    public static HashMap<Player, Boolean> hasItsaTrap = new HashMap<Player, Boolean>();
    public static HashMap<Player, Boolean> hasCounteroffensive = new HashMap<Player, Boolean>();
    public static HashMap<Player, Boolean> hasAlarmTrap = new HashMap<Player, Boolean>();
    public static HashMap<Player, Boolean> hasMinerFatigue = new HashMap<Player, Boolean>();

    public TrapsInventory() {
        inv = Bukkit.createInventory(this, 27, "Queue a trap!");
        init();
    }

    private void init() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getInventory().contains(Material.DIAMOND, 1)) {
                List<String> meta = new ArrayList<>();
                List<String> meta2 = new ArrayList<>();
                List<String> meta3 = new ArrayList<>();
                List<String> meta4 = new ArrayList<>();

                meta.add("§7Inflicts Blindness and Slowness");
                meta.add("§7for 8 seconds.");
                meta.add("");
                meta.add("§7Cost: " + ChatColor.AQUA + "1 Diamond");
                meta.add("");
                meta.add("§eClick to purchase!");

                meta2.add("§7Grants Speed I and Jump Boost II");
                meta2.add("§7for 10 seconds to allied players");
                meta2.add("§7near your base.");
                meta2.add("");
                meta2.add("§7Cost: " + ChatColor.AQUA + "1 Diamond");
                meta2.add("");
                meta2.add("§eClick to purchase!");

                meta3.add("§7Reveals invisible players as");
                meta3.add("§7well as their name and team.");
                meta3.add("");
                meta3.add("§7Cost: " + ChatColor.AQUA + "1 Diamond");
                meta3.add("");
                meta3.add("§eClick to purchase!");

                meta4.add("§7Inflict Mining Fatigue for 10");
                meta4.add("§7seconds.");
                meta4.add("");
                meta4.add("§7Cost: " + ChatColor.AQUA + "1 Diamond");
                meta4.add("");
                meta4.add("§eClick to purchase!");

                itsatrap = CreateItem.createitem("§aIt's a trap!", Material.TRIPWIRE_HOOK, meta, 1, true, true);
                counteroffensive = CreateItem.createitem("§aCounter-Offensive Trap", Material.FEATHER, meta2, 1, true, true);
                alarm = CreateItem.createitem("§aAlarm Trap", Material.REDSTONE_TORCH, meta3, 1, true, true);
                minerfatigue = CreateItem.createitem("§aMiner Fatigue Trap", Material.IRON_PICKAXE, meta4, 1, true, true);
            }

            else {
                List<String> meta = new ArrayList<>();
                List<String> meta2 = new ArrayList<>();
                List<String> meta3 = new ArrayList<>();
                List<String> meta4 = new ArrayList<>();

                meta.add("§7Inflicts Blindness and Slowness");
                meta.add("§7for 8 seconds.");
                meta.add("");
                meta.add("§7Cost: " + ChatColor.AQUA + "1 Diamond");
                meta.add("");
                meta.add("§cYou don't have enough Diamonds!");

                meta2.add("§7Grants Speed I and Jump Boost II");
                meta2.add("§7for 10 seconds to allied players");
                meta2.add("§7near your base.");
                meta2.add("");
                meta2.add("§7Cost: " + ChatColor.AQUA + "1 Diamond");
                meta2.add("");
                meta2.add("§cYou don't have enough Diamonds!");

                meta3.add("§7Reveals invisible players as");
                meta3.add("§7well as their name and team.");
                meta3.add("");
                meta3.add("§7Cost: " + ChatColor.AQUA + "1 Diamond");
                meta3.add("");
                meta3.add("§cYou don't have enough Diamonds!");

                meta4.add("§7Inflict Mining Fatigue for 10");
                meta4.add("§7seconds.");
                meta4.add("");
                meta4.add("§7Cost: " + ChatColor.AQUA + "1 Diamond");
                meta4.add("");
                meta4.add("§cYou don't have enough Diamonds!");

                itsatrap = CreateItem.createitem("§cIt's a trap!", Material.TRIPWIRE_HOOK, meta, 1, true, true);
                counteroffensive = CreateItem.createitem("§cCounter-Offensive Trap", Material.FEATHER, meta2, 1, true, true);
                alarm = CreateItem.createitem("§cAlarm Trap", Material.REDSTONE_TORCH, meta3, 1, true, true);
                minerfatigue = CreateItem.createitem("§cMiner Fatigue Trap", Material.IRON_PICKAXE, meta4, 1, true, true);
            }
        }

        List<String> backmeta = new ArrayList<>();

        backmeta.add("§7To Upgrades & Traps");

        back = CreateItem.createitem("§aGo Back", Material.ARROW, backmeta, 1, true, true);

        inv.setItem(10, itsatrap);
        inv.setItem(11, counteroffensive);
        inv.setItem(12, alarm);
        inv.setItem(13, minerfatigue);
        inv.setItem(22, back);

    }


    @Override
    public Inventory getInventory() {
        return inv;
    }
}
