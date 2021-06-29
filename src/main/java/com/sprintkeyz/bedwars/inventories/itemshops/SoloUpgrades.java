package com.sprintkeyz.bedwars.inventories.itemshops;

import com.sprintkeyz.bedwars.events.BedwarsEvents;
import com.sprintkeyz.bedwars.inventories.traps.TrapsInventory;
import com.sprintkeyz.bedwars.managers.CreateItem;
import com.sprintkeyz.bedwars.managers.Generators;
import com.sprintkeyz.bedwars.managers.Teams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SoloUpgrades implements InventoryHolder {

    Generators generators = new Generators();

    private Inventory inv;

    public static HashMap<Player, Integer> hastelevel = new HashMap<Player, Integer>();
    public static HashMap<Player, Integer> protlevel = new HashMap<Player, Integer>();

    ItemStack trap1empty;
    ItemStack trap2empty;
    ItemStack trap3empty;

    ItemStack trap1minerfatigue;

    ItemStack sharpness;
    ItemStack prot;
    ItemStack maniacminer;
    ItemStack forgeupgrades;
    ItemStack healpool;
    ItemStack dragonbuff;
    ItemStack traps;
    ItemStack graypanes = CreateItem.createitem("§8⬆ §7Purchasable", Material.GRAY_STAINED_GLASS_PANE, Collections.singletonList("§8⬇ §7Traps Queue"), 1, false, false);

    public SoloUpgrades() {
        inv = Bukkit.createInventory(this, 45, "Upgrades & Traps");
        init();
    }

    private void init() {
        for (Player p : Bukkit.getOnlinePlayers()) {

            if (Teams.red.getEntries().contains(p.toString())) {
                if (generators.redGenTime == 60) {
                    if (p.getInventory().contains(Material.DIAMOND, 2)) {
                        List<String> meta3 = new ArrayList<>();
                        meta3.add("§7Upgrade resource spawning on");
                        meta3.add("§7your island.");
                        meta3.add("");
                        meta3.add("§7Tier 1: +50% Resources, " + ChatColor.AQUA + "2 Diamonds");
                        meta3.add("§7Tier 2: +100% Resources, " + ChatColor.AQUA + "4 Diamonds");
                        meta3.add("§7Tier 3: Spawn emeralds, " + ChatColor.AQUA + "6 Diamonds");
                        meta3.add("§7Tier 4: +200% Resources, " + ChatColor.AQUA + "8 Diamonds");
                        meta3.add("");
                        meta3.add("§eClick to purchase!");

                        forgeupgrades = CreateItem.createitem("§aIron Forge", Material.FURNACE, meta3, 1, true, true);
                    }

                    else {
                        List<String> meta3 = new ArrayList<>();

                        meta3.add("§7Upgrade resource spawning on");
                        meta3.add("§7your island.");
                        meta3.add("");
                        meta3.add("§7Tier 1: +50% Resources, " + ChatColor.AQUA + "2 Diamonds");
                        meta3.add("§7Tier 2: +100% Resources, " + ChatColor.AQUA + "4 Diamonds");
                        meta3.add("§7Tier 3: Spawn emeralds, " + ChatColor.AQUA + "6 Diamonds");
                        meta3.add("§7Tier 4: +200% Resources, " + ChatColor.AQUA + "8 Diamonds");
                        meta3.add("");
                        meta3.add("§cYou don't have enough Diamonds!");

                        forgeupgrades = CreateItem.createitem("§cIron Forge", Material.FURNACE, meta3, 1, true, true);
                    }
                }

                if (generators.redGenTime == 90) {
                    if (p.getInventory().contains(Material.DIAMOND, 4)) {
                        List<String> meta3 = new ArrayList<>();
                        meta3.add("§7Upgrade resource spawning on");
                        meta3.add("§7your island.");
                        meta3.add("");
                        meta3.add("§aTier 1: +50% Resources, " + ChatColor.AQUA + "2 Diamonds");
                        meta3.add("§7Tier 2: +100% Resources, " + ChatColor.AQUA + "4 Diamonds");
                        meta3.add("§7Tier 3: Spawn emeralds, " + ChatColor.AQUA + "6 Diamonds");
                        meta3.add("§7Tier 4: +200% Resources, " + ChatColor.AQUA + "8 Diamonds");
                        meta3.add("");
                        meta3.add("§eClick to purchase!");

                        forgeupgrades = CreateItem.createitem("§aGolden Forge", Material.FURNACE, meta3, 1, true, true);
                    }

                    else {
                        List<String> meta3 = new ArrayList<>();

                        meta3.add("§7Upgrade resource spawning on");
                        meta3.add("§7your island.");
                        meta3.add("");
                        meta3.add("§aTier 1: +50% Resources, " + ChatColor.AQUA + "2 Diamonds");
                        meta3.add("§7Tier 2: +100% Resources, " + ChatColor.AQUA + "4 Diamonds");
                        meta3.add("§7Tier 3: Spawn emeralds, " + ChatColor.AQUA + "6 Diamonds");
                        meta3.add("§7Tier 4: +200% Resources, " + ChatColor.AQUA + "8 Diamonds");
                        meta3.add("");
                        meta3.add("§cYou don't have enough Diamonds!");

                        forgeupgrades = CreateItem.createitem("§cGolden Forge", Material.FURNACE, meta3, 1, true, true);
                    }
                }
            }

            // MANIAC MINER
            if (hastelevel.get(p) == 0) {
                if (p.getInventory().contains(Material.DIAMOND, 2)) {
                    List<String> meta = new ArrayList<>();
                    meta.add("§7All players on your team");
                    meta.add("§7permanently gain Haste.");
                    meta.add("");
                    meta.add("§7Tier 1: Haste I, §b2 Diamonds");
                    meta.add("§7Tier 2: Haste II, §b4 Diamonds");
                    meta.add("");
                    meta.add("§eClick to purchase!");

                    maniacminer = CreateItem.createitem("§aManiac Miner I", Material.GOLDEN_PICKAXE, meta, 1, true, true);
                }

                else {
                    List<String> meta = new ArrayList<>();
                    meta.add("§7All players on your team");
                    meta.add("§7permanently gain Haste.");
                    meta.add("");
                    meta.add("§7Tier 1: Haste I, §b2 Diamonds");
                    meta.add("§7Tier 2: Haste II, §b4 Diamonds");
                    meta.add("");
                    meta.add("§cYou don't have enough Diamonds!");

                    maniacminer = CreateItem.createitem("§cManiac Miner I", Material.GOLDEN_PICKAXE, meta, 1, true, true);
                }
            }

            if (hastelevel.get(p) == 1) {
                if (p.getInventory().contains(Material.DIAMOND, 4)) {
                    List<String> meta = new ArrayList<>();
                    meta.add("§7All players on your team");
                    meta.add("§7permanently gain Haste.");
                    meta.add("");
                    meta.add("§aTier 1: Haste I, §b2 Diamonds");
                    meta.add("§7Tier 2: Haste II, §b4 Diamonds");
                    meta.add("");
                    meta.add("§eClick to purchase!");

                    maniacminer = CreateItem.createitem("§aManiac Miner II", Material.GOLDEN_PICKAXE, meta, 1, true, true);
                }

                else {
                    List<String> meta = new ArrayList<>();
                    meta.add("§7All players on your team");
                    meta.add("§7permanently gain Haste.");
                    meta.add("");
                    meta.add("§aTier 1: Haste I, §b2 Diamonds");
                    meta.add("§7Tier 2: Haste II, §b4 Diamonds");
                    meta.add("");
                    meta.add("§cYou don't have enough Diamonds!");

                    maniacminer = CreateItem.createitem("§cManiac Miner II", Material.GOLDEN_PICKAXE, meta, 1, true, true);
                }
            }

            if (hastelevel.get(p) == 2) {
                List<String> meta = new ArrayList<>();
                meta.add("§7All players on your team");
                meta.add("§7permanently gain Haste.");
                meta.add("");
                meta.add("§aTier 1: Haste I, §b2 Diamonds");
                meta.add("§aTier 2: Haste II, §b4 Diamonds");
                meta.add("");
                meta.add("§aUNLOCKED");
                maniacminer = CreateItem.createitem("§aManiac Miner II", Material.GOLDEN_PICKAXE, meta, 1, true, true);
            }

            if (protlevel.get(p) == 0) {
                if (p.getInventory().contains(Material.DIAMOND, 2)) {
                    List<String> meta = new ArrayList<>();
                    meta.add("§7Your team permanently gains");
                    meta.add("§7Protection on all armor pieces!");
                    meta.add("");
                    meta.add("§7Tier 1: Protection I, §b2 Diamonds");
                    meta.add("§7Tier 1: Protection II, §b4 Diamonds");
                    meta.add("§7Tier 1: Protection III, §b8 Diamonds");
                    meta.add("§7Tier 1: Protection IV, §b16 Diamonds");
                    meta.add("");
                    meta.add("§eClick to purchase!");

                    prot = CreateItem.createitem("§aReinforced Armor I", Material.IRON_CHESTPLATE, meta, 1, true, true);
                }

                else {
                    List<String> meta = new ArrayList<>();
                    meta.add("§7Your team permanently gains");
                    meta.add("§7Protection on all armor pieces!");
                    meta.add("");
                    meta.add("§7Tier 1: Protection I, §b2 Diamonds");
                    meta.add("§7Tier 1: Protection II, §b4 Diamonds");
                    meta.add("§7Tier 1: Protection III, §b8 Diamonds");
                    meta.add("§7Tier 1: Protection IV, §b16 Diamonds");
                    meta.add("");
                    meta.add("§cYou don't have enough Diamonds!");

                    prot = CreateItem.createitem("§cReinforced Armor I", Material.IRON_CHESTPLATE, meta, 1, true, true);
                }
            }

            if (protlevel.get(p) == 1) {
                if (p.getInventory().contains(Material.DIAMOND, 4)) {
                    List<String> meta = new ArrayList<>();
                    meta.add("§7Your team permanently gains");
                    meta.add("§7Protection on all armor pieces!");
                    meta.add("");
                    meta.add("§aTier 1: Protection I, §b2 Diamonds");
                    meta.add("§7Tier 1: Protection II, §b4 Diamonds");
                    meta.add("§7Tier 1: Protection III, §b8 Diamonds");
                    meta.add("§7Tier 1: Protection IV, §b16 Diamonds");
                    meta.add("");
                    meta.add("§eClick to purchase!");

                    prot = CreateItem.createitem("§aReinforced Armor II", Material.IRON_CHESTPLATE, meta, 1, true, true);
                }

                else {
                    List<String> meta = new ArrayList<>();
                    meta.add("§7Your team permanently gains");
                    meta.add("§7Protection on all armor pieces!");
                    meta.add("");
                    meta.add("§aTier 1: Protection I, §b2 Diamonds");
                    meta.add("§7Tier 1: Protection II, §b4 Diamonds");
                    meta.add("§7Tier 1: Protection III, §b8 Diamonds");
                    meta.add("§7Tier 1: Protection IV, §b16 Diamonds");
                    meta.add("");
                    meta.add("§cYou don't have enough Diamonds!");

                    prot = CreateItem.createitem("§cReinforced Armor II", Material.IRON_CHESTPLATE, meta, 1, true, true);
                }
            }

            if (protlevel.get(p) == 2) {
                if (p.getInventory().contains(Material.DIAMOND, 8)) {
                    List<String> meta = new ArrayList<>();
                    meta.add("§7Your team permanently gains");
                    meta.add("§7Protection on all armor pieces!");
                    meta.add("");
                    meta.add("§aTier 1: Protection I, §b2 Diamonds");
                    meta.add("§aTier 1: Protection II, §b4 Diamonds");
                    meta.add("§7Tier 1: Protection III, §b8 Diamonds");
                    meta.add("§7Tier 1: Protection IV, §b16 Diamonds");
                    meta.add("");
                    meta.add("§eClick to purchase!");

                    prot = CreateItem.createitem("§aReinforced Armor III", Material.IRON_CHESTPLATE, meta, 1, true, true);
                }

                else {
                    List<String> meta = new ArrayList<>();
                    meta.add("§7Your team permanently gains");
                    meta.add("§7Protection on all armor pieces!");
                    meta.add("");
                    meta.add("§aTier 1: Protection I, §b2 Diamonds");
                    meta.add("§aTier 1: Protection II, §b4 Diamonds");
                    meta.add("§7Tier 1: Protection III, §b8 Diamonds");
                    meta.add("§7Tier 1: Protection IV, §b16 Diamonds");
                    meta.add("");
                    meta.add("§cYou don't have enough Diamonds!");

                    prot = CreateItem.createitem("§cReinforced Armor III", Material.IRON_CHESTPLATE, meta, 1, true, true);
                }
            }

            if (protlevel.get(p) == 3) {
                if (p.getInventory().contains(Material.DIAMOND, 16)) {
                    List<String> meta = new ArrayList<>();
                    meta.add("§7Your team permanently gains");
                    meta.add("§7Protection on all armor pieces!");
                    meta.add("");
                    meta.add("§aTier 1: Protection I, §b2 Diamonds");
                    meta.add("§aTier 1: Protection II, §b4 Diamonds");
                    meta.add("§aTier 1: Protection III, §b8 Diamonds");
                    meta.add("§7Tier 1: Protection IV, §b16 Diamonds");
                    meta.add("");
                    meta.add("§eClick to purchase!");

                    prot = CreateItem.createitem("§aReinforced Armor IV", Material.IRON_CHESTPLATE, meta, 1, true, true);
                }

                else {
                    List<String> meta = new ArrayList<>();
                    meta.add("§7Your team permanently gains");
                    meta.add("§7Protection on all armor pieces!");
                    meta.add("");
                    meta.add("§aTier 1: Protection I, §b2 Diamonds");
                    meta.add("§aTier 1: Protection II, §b4 Diamonds");
                    meta.add("§aTier 1: Protection III, §b8 Diamonds");
                    meta.add("§7Tier 1: Protection IV, §b16 Diamonds");
                    meta.add("");
                    meta.add("§cYou don't have enough Diamonds!");

                    prot = CreateItem.createitem("§cReinforced Armor IV", Material.IRON_CHESTPLATE, meta, 1, true, true);
                }
            }

            if (protlevel.get(p) == 4) {
                List<String> meta = new ArrayList<>();
                meta.add("§7Your team permanently gains");
                meta.add("§7Protection on all armor pieces!");
                meta.add("");
                meta.add("§aTier 1: Protection I, §b2 Diamonds");
                meta.add("§aTier 1: Protection II, §b4 Diamonds");
                meta.add("§aTier 1: Protection III, §b8 Diamonds");
                meta.add("§aTier 1: Protection IV, §b16 Diamonds");
                meta.add("");
                meta.add("§aUNLOCKED");

                prot = CreateItem.createitem("§aReinforced Armor IV", Material.IRON_CHESTPLATE, meta, 1, true, true);
            }


            if (p.getInventory().contains(Material.DIAMOND, 4)) {
                List<String> meta = new ArrayList<>();
                meta.add("§7Your team permanently gains");
                meta.add("§7Sharpness I on all swords and");
                meta.add("§7axes!");
                meta.add("");
                meta.add("§7Cost: " + ChatColor.AQUA + "4 Diamonds");
                meta.add("");
                meta.add("§eClick to purchase!");

                List<String> meta2 = new ArrayList<>();
                meta2.add("§7Your team permanently gains");
                meta2.add("§7Sharpness I on all swords and");
                meta2.add("§7axes!");
                meta2.add("");
                meta2.add("§7Cost: " + ChatColor.AQUA + "4 Diamonds");
                meta2.add("");
                meta2.add("§aUNLOCKED");

                if (!BedwarsEvents.hasSharpness.get(p)) {
                    sharpness = CreateItem.createitem("§aSharpened Swords", Material.IRON_SWORD, meta, 1, true, true);
                }

                else {
                    sharpness = CreateItem.createitem("§aSharpened Swords", Material.IRON_SWORD, meta2, 1, true, true);
                }
            }

            else {
                List<String> meta = new ArrayList<>();
                meta.add("§7Your team permanently gains");
                meta.add("§7Sharpness I on all swords and");
                meta.add("§7axes!");
                meta.add("");
                meta.add("§7Cost: " + ChatColor.AQUA + "4 Diamonds");
                meta.add("");
                meta.add("§cYou don't have enough Diamonds!");

                List<String> meta2 = new ArrayList<>();
                meta2.add("§7Your team permanently gains");
                meta2.add("§7Sharpness I on all swords and");
                meta2.add("§7axes!");
                meta2.add("");
                meta2.add("§7Cost: " + ChatColor.AQUA + "4 Diamonds");
                meta2.add("");
                meta2.add("§aUNLOCKED");

                if (!BedwarsEvents.hasSharpness.get(p)) {
                    sharpness = CreateItem.createitem("§cSharpened Swords", Material.IRON_SWORD, meta, 1, true, true);
                }

                else {
                    sharpness = CreateItem.createitem("§aSharpened Swords", Material.IRON_SWORD, meta2, 1, true, true);
                }

            }


            if (!BedwarsEvents.healPool.get(p)) {
                if (p.getInventory().contains(Material.DIAMOND, 1)) {
                    List<String> meta = new ArrayList<>();
                    meta.add("§7Creates a regeneration field");
                    meta.add("§7around your base!");
                    meta.add("");
                    meta.add("§7Cost: " + ChatColor.AQUA + "1 Diamond");
                    meta.add("");
                    meta.add("§eClick to purchase!");
                    healpool = CreateItem.createitem("§aHeal Pool", Material.BEACON, meta, 1, true, true);
                } else {
                    List<String> meta = new ArrayList<>();
                    meta.add("§7Creates a regeneration field");
                    meta.add("§7around your base!");
                    meta.add("");
                    meta.add("§7Cost: " + ChatColor.AQUA + "1 Diamond");
                    meta.add("");
                    meta.add("§cYou don't have enough Diamonds!");
                    healpool = CreateItem.createitem("§cHeal Pool", Material.BEACON, meta, 1, true, true);
                }
            }

            else {
                List<String> meta = new ArrayList<>();
                meta.add("§7Creates a regeneration field");
                meta.add("§7around your base!");
                meta.add("");
                meta.add("§7Cost: " + ChatColor.AQUA + "1 Diamond");
                meta.add("");
                meta.add("§aUNLOCKED");
                healpool = CreateItem.createitem("§aHeal Pool", Material.BEACON, meta, 1, true, true);
            }

            if (p.getInventory().contains(Material.DIAMOND, 5)) {
                List<String> meta = new ArrayList<>();

                meta.add("§7Your team will have 2 dragons");
                meta.add("§7instead of 1 during deathmatch!");
                meta.add("");
                meta.add("§7Cost: " + ChatColor.AQUA + "5 Diamonds");
                meta.add("");
                meta.add("§eClick to purchase!");

                dragonbuff = CreateItem.createitem("§aDragon Buff", Material.DRAGON_EGG, meta, 1, true, true);
            }

            else {
                List<String> meta = new ArrayList<>();

                meta.add("§7Your team will have 2 dragons");
                meta.add("§7instead of 1 during deathmatch!");
                meta.add("");
                meta.add("§7Cost: " + ChatColor.AQUA + "5 Diamonds");
                meta.add("");
                meta.add("§cYou don't have enough Diamonds!");

                dragonbuff = CreateItem.createitem("§cDragon Buff", Material.DRAGON_EGG, meta, 1, true, true);
            }

            List<String> trapsmeta = new ArrayList<>();
            List<String> trap1meta = new ArrayList<>();
            List<String> trap2meta = new ArrayList<>();
            List<String> trap3meta = new ArrayList<>();

            trapsmeta.add("§7Purchased traps will be");
            trapsmeta.add("§7queued on the right.");
            trapsmeta.add("");
            trapsmeta.add("§eClick to browse!");

            trap1meta.add("§7The first enemy to walk");
            trap1meta.add("§7into your base will trigger");
            trap1meta.add("§7this trap!");
            trap1meta.add("");
            trap1meta.add("§7Purchasing a trap will");
            trap1meta.add("§7queue it here. Its cost");
            trap1meta.add("§7will scale based on the");
            trap1meta.add("§7number of traps queued.");
            trap1meta.add("");
            trap1meta.add("§7Next trap: " + ChatColor.AQUA + "1 Diamond");

            trap2meta.add("§7The second enemy to walk");
            trap2meta.add("§7into your base will trigger");
            trap2meta.add("§7this trap!");
            trap2meta.add("");
            trap2meta.add("§7Purchasing a trap will");
            trap2meta.add("§7queue it here. Its cost");
            trap2meta.add("§7will scale based on the");
            trap2meta.add("§7number of traps queued.");
            trap2meta.add("");
            trap2meta.add("§7Next trap: " + ChatColor.AQUA + "1 Diamond");


            trap3meta.add("§7The third enemy to walk");
            trap3meta.add("§7into your base will trigger");
            trap3meta.add("§7this trap!");
            trap3meta.add("");
            trap3meta.add("§7Purchasing a trap will");
            trap3meta.add("§7queue it here. Its cost");
            trap3meta.add("§7will scale based on the");
            trap3meta.add("§7number of traps queued.");
            trap3meta.add("");
            trap3meta.add("§7Next trap: " + ChatColor.AQUA + "1 Diamond");

            List<String> trap1minerfatiguemeta = new ArrayList<>();

            trap1minerfatiguemeta.add("§7Inflict Mining Fatigue for");
            trap1minerfatiguemeta.add("§710 seconds.");
            trap1minerfatiguemeta.add("");
            trap1minerfatiguemeta.add("§7The first enemy to walk");
            trap1minerfatiguemeta.add("§7into your base will trigger");
            trap1minerfatiguemeta.add("§7this trap!");
            trap1minerfatiguemeta.add("");
            trap1minerfatiguemeta.add("§7Next trap: " + ChatColor.AQUA + "2 Diamonds");



            traps = CreateItem.createitem("§eBuy a trap", Material.LEATHER, trapsmeta, 1, true, true);
            trap1empty = CreateItem.createitem("§cTrap #1: No Trap!", Material.LIGHT_GRAY_STAINED_GLASS, trap1meta, 1, true, true);
            trap2empty = CreateItem.createitem("§cTrap #2: No Trap!", Material.LIGHT_GRAY_STAINED_GLASS, trap2meta, 2, true, true);
            trap3empty = CreateItem.createitem("§cTrap #3: No Trap!", Material.LIGHT_GRAY_STAINED_GLASS, trap3meta, 3, true, true);

            trap1minerfatigue = CreateItem.createitem("§aTrap #1: Miner Fatigue Trap", Material.IRON_PICKAXE, trap1minerfatiguemeta, 1, true, true);

        }

        inv.setItem(10, sharpness);
        inv.setItem(11, prot);
        inv.setItem(12, maniacminer);
        inv.setItem(13, forgeupgrades);
        inv.setItem(14, healpool);
        inv.setItem(15, dragonbuff);
        inv.setItem(16, traps);

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!TrapsInventory.hasMinerFatigue.containsKey(player) || !TrapsInventory.hasAlarmTrap.containsKey(player) || !TrapsInventory.hasItsaTrap.containsKey(player) || !TrapsInventory.hasCounteroffensive.containsKey(player)) {
                inv.setItem(30, trap1empty);
            }

            else if (TrapsInventory.hasMinerFatigue.containsKey(player)) {
                inv.setItem(30, trap1minerfatigue);
            }
        }
        inv.setItem(31, trap2empty);
        inv.setItem(32, trap3empty);

        for (int i = 18; i <= 26; i++) {
            inv.setItem(i, graypanes);
        }

    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
