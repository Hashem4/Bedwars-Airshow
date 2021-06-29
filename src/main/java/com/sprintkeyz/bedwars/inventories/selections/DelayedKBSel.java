package com.sprintkeyz.bedwars.inventories.selections;

import com.sprintkeyz.bedwars.managers.CreateItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public class DelayedKBSel implements InventoryHolder {

    private Inventory inv;

    // 3s, 5s, 8s, 10s, 12s, 15s, 20s, 25s

    ItemStack threeSeconds = CreateItem.createitem("§f3s Delay", Material.RED_CONCRETE, Collections.singletonList("§7Knockback delay of 3s!"), 1, true, true);
    ItemStack fiveSeconds = CreateItem.createitem("§f5s Delay", Material.ORANGE_CONCRETE, Collections.singletonList("§7Knockback delay of 5s!"), 1, true, true);
    ItemStack eightSeconds = CreateItem.createitem("§f8s Delay", Material.YELLOW_CONCRETE, Collections.singletonList("§7Knockback delay of 8s!"), 1, true, true);
    ItemStack tenSeconds = CreateItem.createitem("§f10s Delay", Material.GREEN_TERRACOTTA, Collections.singletonList("§7Knockback delay of 10s!"), 1, true, true);
    ItemStack twelveSeconds = CreateItem.createitem("§f12s Delay", Material.LIME_CONCRETE, Collections.singletonList("§7Knockback delay of 12s!"), 1, true, true);
    ItemStack fifteenSeconds = CreateItem.createitem("§f15s Delay", Material.LIGHT_BLUE_CONCRETE, Collections.singletonList("§7Knockback delay of 15s!"), 1, true, true);
    ItemStack twentySeconds = CreateItem.createitem("§f20s Delay", Material.BLUE_CONCRETE, Collections.singletonList("§7Knockback delay of 20s!"), 1, true, true);
    ItemStack twentyfiveSeconds = CreateItem.createitem("§f25s Delay", Material.PURPLE_CONCRETE, Collections.singletonList("§7Knockback delay of 25s!"), 1, true, true);
    ItemStack customDelay = CreateItem.createitem("§eCustom Delay", Material.OAK_SIGN, Collections.singletonList("§7Custom delay up to 30 seconds!"), 1, true, true);

    public DelayedKBSel() {
        inv = Bukkit.createInventory(this, 9, "Choose a Delay");
        init();
    }

    private void init() {
        inv.setItem(0, threeSeconds);
        inv.setItem(1, fiveSeconds);
        inv.setItem(2, eightSeconds);
        inv.setItem(3, tenSeconds);
        inv.setItem(4, twelveSeconds);
        inv.setItem(5, fifteenSeconds);
        inv.setItem(6, twentySeconds);
        inv.setItem(7, twentyfiveSeconds);
        inv.setItem(8, customDelay);
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
