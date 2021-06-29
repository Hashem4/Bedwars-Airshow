package com.sprintkeyz.bedwars.inventories.selections;

import com.sprintkeyz.bedwars.managers.CreateItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public class TeamSelector implements InventoryHolder {

    private Inventory inv;

    ItemStack RedSel;
    ItemStack BlueSel;
    ItemStack GreenSel;
    ItemStack YellowSel;
    ItemStack AquaSel;
    ItemStack WhiteSel;
    ItemStack PinkSel;
    ItemStack GraySel;

    public TeamSelector() {
        inv = Bukkit.createInventory(this, 9, "Team Selection");
        init();
    }

    private void init() {
        RedSel = CreateItem.createitem("§cRed Team", Material.RED_STAINED_GLASS_PANE, Collections.singletonList("§7Join Red Team!"), 1, true, false);
        BlueSel = CreateItem.createitem("§1Blue Team", Material.BLUE_STAINED_GLASS_PANE, Collections.singletonList("§7Join Blue Team!"), 1, true, false);
        GreenSel = CreateItem.createitem("§aGreen Team", Material.LIME_STAINED_GLASS_PANE, Collections.singletonList("§7Join Green Team!"), 1, true, false);
        YellowSel = CreateItem.createitem("§eYellow Team", Material.YELLOW_STAINED_GLASS_PANE, Collections.singletonList("§7Join Yellow Team!"), 1, true, false);
        AquaSel = CreateItem.createitem("§bAqua Team", Material.LIGHT_BLUE_STAINED_GLASS_PANE, Collections.singletonList("§7Join Aqua Team!"), 1, true, false);
        WhiteSel = CreateItem.createitem("§fWhite Team", Material.WHITE_STAINED_GLASS_PANE, Collections.singletonList("§7Join White Team!"), 1, true, false);
        PinkSel = CreateItem.createitem("§dPink Team", Material.PINK_STAINED_GLASS_PANE, Collections.singletonList("§7Join Pink Team!"), 1, true, false);
        GraySel = CreateItem.createitem("§8Gray Team", Material.GRAY_STAINED_GLASS_PANE, Collections.singletonList("§7Join Gray Team!"), 1, true, false);

        inv.setItem(0, RedSel);
        inv.setItem(1, BlueSel);
        inv.setItem(2, GreenSel);
        inv.setItem(3, YellowSel);
        inv.setItem(4, AquaSel);
        inv.setItem(5, WhiteSel);
        inv.setItem(6, PinkSel);
        inv.setItem(7, GraySel);
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
