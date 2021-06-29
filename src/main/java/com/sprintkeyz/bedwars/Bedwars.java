package com.sprintkeyz.bedwars;

import com.mojang.datafixers.util.Pair;
import com.sprintkeyz.bedwars.commands.BedwarsCommands;
import com.sprintkeyz.bedwars.events.BedwarsEvents;
import com.sprintkeyz.bedwars.inventories.itemshops.QuickBuy;
import com.sprintkeyz.bedwars.inventories.itemshops.SoloUpgrades;
import com.sprintkeyz.bedwars.inventories.traps.TrapsInventory;
import com.sprintkeyz.bedwars.managers.CreateItem;
import com.sprintkeyz.bedwars.managers.Generators;
import com.sprintkeyz.bedwars.managers.Teams;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.network.protocol.game.PacketPlayOutEntityEquipment;
import net.minecraft.world.entity.EnumItemSlot;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Bedwars extends JavaPlugin {

    GameMode gameMode;
    Teams teams;
    QuickBuy itemshop;

    public static HashMap<Player, Boolean> isInvis = new HashMap<Player, Boolean>();

    // Player teams:
    public static Player redPlayer;
    public static Player bluePlayer;
    public static Player greenPlayer;
    public static Player yellowPlayer;
    public static Player aquaPlayer;
    public static Player whitePlayer;
    public static Player pinkPlayer;
    public static Player grayPlayer;

    public static HashMap<Player, Boolean> isSpectating = new HashMap<Player, Boolean>();
    public static HashMap<Player, Boolean> doesntNeedArmor = new HashMap<Player, Boolean>();

    // ARMOR
    ItemStack redHelmet = new ItemStack(Material.LEATHER_HELMET);
    ItemStack redChestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
    ItemStack redLeggings = new ItemStack(Material.LEATHER_LEGGINGS);
    ItemStack redBoots = new ItemStack(Material.LEATHER_BOOTS);

    LeatherArmorMeta redHelmetMeta = (LeatherArmorMeta) redHelmet.getItemMeta();
    LeatherArmorMeta redChestplateMeta = (LeatherArmorMeta) redChestplate.getItemMeta();
    LeatherArmorMeta redLeggingsMeta = (LeatherArmorMeta) redLeggings.getItemMeta();
    LeatherArmorMeta redBootsMeta = (LeatherArmorMeta) redBoots.getItemMeta();

    ItemStack blueHelmet = new ItemStack(Material.LEATHER_HELMET);
    ItemStack blueChestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
    ItemStack blueLeggings = new ItemStack(Material.LEATHER_LEGGINGS);
    ItemStack blueBoots = new ItemStack(Material.LEATHER_BOOTS);

    LeatherArmorMeta blueHelmetMeta = (LeatherArmorMeta) blueHelmet.getItemMeta();
    LeatherArmorMeta blueChestplateMeta = (LeatherArmorMeta) blueChestplate.getItemMeta();
    LeatherArmorMeta blueLeggingsMeta = (LeatherArmorMeta) blueLeggings.getItemMeta();
    LeatherArmorMeta blueBootsMeta = (LeatherArmorMeta) blueBoots.getItemMeta();

    ItemStack greenHelmet = new ItemStack(Material.LEATHER_HELMET);
    ItemStack greenChestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
    ItemStack greenLeggings = new ItemStack(Material.LEATHER_LEGGINGS);
    ItemStack greenBoots = new ItemStack(Material.LEATHER_BOOTS);

    LeatherArmorMeta greenHelmetMeta = (LeatherArmorMeta) greenHelmet.getItemMeta();
    LeatherArmorMeta greenChestplateMeta = (LeatherArmorMeta) greenChestplate.getItemMeta();
    LeatherArmorMeta greenLeggingsMeta = (LeatherArmorMeta) greenLeggings.getItemMeta();
    LeatherArmorMeta greenBootsMeta = (LeatherArmorMeta) greenBoots.getItemMeta();

    ItemStack yellowHelmet = new ItemStack(Material.LEATHER_HELMET);
    ItemStack yellowChestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
    ItemStack yellowLeggings = new ItemStack(Material.LEATHER_LEGGINGS);
    ItemStack yellowBoots = new ItemStack(Material.LEATHER_BOOTS);

    LeatherArmorMeta yellowHelmetMeta = (LeatherArmorMeta) yellowHelmet.getItemMeta();
    LeatherArmorMeta yellowChestplateMeta = (LeatherArmorMeta) yellowChestplate.getItemMeta();
    LeatherArmorMeta yellowLeggingsMeta = (LeatherArmorMeta) yellowLeggings.getItemMeta();
    LeatherArmorMeta yellowBootsMeta = (LeatherArmorMeta) yellowBoots.getItemMeta();

    ItemStack aquaHelmet = new ItemStack(Material.LEATHER_HELMET);
    ItemStack aquaChestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
    ItemStack aquaLeggings = new ItemStack(Material.LEATHER_LEGGINGS);
    ItemStack aquaBoots = new ItemStack(Material.LEATHER_BOOTS);

    LeatherArmorMeta aquaHelmetMeta = (LeatherArmorMeta) aquaHelmet.getItemMeta();
    LeatherArmorMeta aquaChestplateMeta = (LeatherArmorMeta) aquaChestplate.getItemMeta();
    LeatherArmorMeta aquaLeggingsMeta = (LeatherArmorMeta) aquaLeggings.getItemMeta();
    LeatherArmorMeta aquaBootsMeta = (LeatherArmorMeta) aquaBoots.getItemMeta();

    ItemStack whiteHelmet = new ItemStack(Material.LEATHER_HELMET);
    ItemStack whiteChestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
    ItemStack whiteLeggings = new ItemStack(Material.LEATHER_LEGGINGS);
    ItemStack whiteBoots = new ItemStack(Material.LEATHER_BOOTS);

    LeatherArmorMeta whiteHelmetMeta = (LeatherArmorMeta) whiteHelmet.getItemMeta();
    LeatherArmorMeta whiteChestplateMeta = (LeatherArmorMeta) whiteChestplate.getItemMeta();
    LeatherArmorMeta whiteLeggingsMeta = (LeatherArmorMeta) whiteLeggings.getItemMeta();
    LeatherArmorMeta whiteBootsMeta = (LeatherArmorMeta) whiteBoots.getItemMeta();

    ItemStack pinkHelmet = new ItemStack(Material.LEATHER_HELMET);
    ItemStack pinkChestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
    ItemStack pinkLeggings = new ItemStack(Material.LEATHER_LEGGINGS);
    ItemStack pinkBoots = new ItemStack(Material.LEATHER_BOOTS);

    LeatherArmorMeta pinkHelmetMeta = (LeatherArmorMeta) pinkHelmet.getItemMeta();
    LeatherArmorMeta pinkChestplateMeta = (LeatherArmorMeta) pinkChestplate.getItemMeta();
    LeatherArmorMeta pinkLeggingsMeta = (LeatherArmorMeta) pinkLeggings.getItemMeta();
    LeatherArmorMeta pinkBootsMeta = (LeatherArmorMeta) pinkBoots.getItemMeta();

    ItemStack grayHelmet = new ItemStack(Material.LEATHER_HELMET);
    ItemStack grayChestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
    ItemStack grayLeggings = new ItemStack(Material.LEATHER_LEGGINGS);
    ItemStack grayBoots = new ItemStack(Material.LEATHER_BOOTS);

    LeatherArmorMeta grayHelmetMeta = (LeatherArmorMeta) grayHelmet.getItemMeta();
    LeatherArmorMeta grayChestplateMeta = (LeatherArmorMeta) grayChestplate.getItemMeta();
    LeatherArmorMeta grayLeggingsMeta = (LeatherArmorMeta) grayLeggings.getItemMeta();
    LeatherArmorMeta grayBootsMeta = (LeatherArmorMeta) grayBoots.getItemMeta();


    @Override
    public void onEnable() {

        BedwarsEvents.playersJoined = 0;

        redHelmetMeta.setColor(Color.RED);
        redChestplateMeta.setColor(Color.RED);
        redLeggingsMeta.setColor(Color.RED);
        redBootsMeta.setColor(Color.RED);
        redHelmet.setItemMeta(redHelmetMeta);
        redChestplate.setItemMeta(redChestplateMeta);
        redLeggings.setItemMeta(redLeggingsMeta);
        redBoots.setItemMeta(redBootsMeta);

        blueHelmetMeta.setColor(Color.BLUE);
        blueChestplateMeta.setColor(Color.BLUE);
        blueLeggingsMeta.setColor(Color.BLUE);
        blueBootsMeta.setColor(Color.BLUE);
        blueHelmet.setItemMeta(blueHelmetMeta);
        blueChestplate.setItemMeta(blueChestplateMeta);
        blueLeggings.setItemMeta(blueLeggingsMeta);
        blueBoots.setItemMeta(blueBootsMeta);

        greenHelmetMeta.setColor(Color.GREEN);
        greenChestplateMeta.setColor(Color.GREEN);
        greenLeggingsMeta.setColor(Color.GREEN);
        greenBootsMeta.setColor(Color.GREEN);
        greenHelmet.setItemMeta(greenHelmetMeta);
        greenChestplate.setItemMeta(greenChestplateMeta);
        greenLeggings.setItemMeta(greenLeggingsMeta);
        greenBoots.setItemMeta(greenBootsMeta);

        yellowHelmetMeta.setColor(Color.YELLOW);
        yellowChestplateMeta.setColor(Color.YELLOW);
        yellowLeggingsMeta.setColor(Color.YELLOW);
        yellowBootsMeta.setColor(Color.YELLOW);
        yellowHelmet.setItemMeta(yellowHelmetMeta);
        yellowChestplate.setItemMeta(yellowChestplateMeta);
        yellowLeggings.setItemMeta(yellowLeggingsMeta);
        yellowBoots.setItemMeta(yellowBootsMeta);

        aquaHelmetMeta.setColor(Color.AQUA);
        aquaChestplateMeta.setColor(Color.AQUA);
        aquaLeggingsMeta.setColor(Color.AQUA);
        aquaBootsMeta.setColor(Color.AQUA);
        aquaHelmet.setItemMeta(aquaHelmetMeta);
        aquaChestplate.setItemMeta(aquaChestplateMeta);
        aquaLeggings.setItemMeta(aquaLeggingsMeta);
        aquaBoots.setItemMeta(aquaBootsMeta);

        whiteHelmetMeta.setColor(Color.WHITE);
        whiteChestplateMeta.setColor(Color.WHITE);
        whiteLeggingsMeta.setColor(Color.WHITE);
        whiteBootsMeta.setColor(Color.WHITE);
        whiteHelmet.setItemMeta(whiteHelmetMeta);
        whiteChestplate.setItemMeta(whiteChestplateMeta);
        whiteLeggings.setItemMeta(whiteLeggingsMeta);
        whiteBoots.setItemMeta(whiteBootsMeta);

        pinkHelmetMeta.setColor(Color.FUCHSIA);
        pinkChestplateMeta.setColor(Color.FUCHSIA);
        pinkLeggingsMeta.setColor(Color.FUCHSIA);
        pinkBootsMeta.setColor(Color.FUCHSIA);
        pinkHelmet.setItemMeta(pinkHelmetMeta);
        pinkChestplate.setItemMeta(pinkChestplateMeta);
        pinkLeggings.setItemMeta(pinkLeggingsMeta);
        pinkBoots.setItemMeta(pinkBootsMeta);

        grayHelmetMeta.setColor(Color.GRAY);
        grayChestplateMeta.setColor(Color.GRAY);
        grayLeggingsMeta.setColor(Color.GRAY);
        grayBootsMeta.setColor(Color.GRAY);
        grayHelmet.setItemMeta(grayHelmetMeta);
        grayChestplate.setItemMeta(grayChestplateMeta);
        grayLeggings.setItemMeta(grayLeggingsMeta);
        grayBoots.setItemMeta(grayBootsMeta);


        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.setFoodLevel(20);
        }

        BedwarsEvents.prevgm = GameMode.SURVIVAL;

        getServer().getWorld("world").setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        getServer().getWorld("world").setGameRule(GameRule.DO_FIRE_TICK, false);
        getServer().getWorld("world").setGameRule(GameRule.DO_MOB_SPAWNING, false);
        getServer().getWorld("world").setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        getServer().getWorld("world").setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        getServer().getWorld("world").setGameRule(GameRule.NATURAL_REGENERATION, true);
        getServer().getWorld("world").setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
        getServer().getWorld("world").setGameRule(GameRule.KEEP_INVENTORY, true);

        BedwarsCommands commands = new BedwarsCommands();
        Generators generators = new Generators();
        getCommand("revive").setExecutor(commands);
        getCommand("stopbasegens").setExecutor(commands);
        getCommand("startbasegens").setExecutor(commands);
        getCommand("stopdiamonds").setExecutor(commands);
        getCommand("startdiamonds").setExecutor(commands);
        getCommand("stopemeralds").setExecutor(commands);
        getCommand("startemeralds").setExecutor(commands);
        getCommand("vanish").setExecutor(commands);
        getCommand("nick").setExecutor(commands);
        getCommand("nickreset").setExecutor(commands);
        getCommand("rlc").setExecutor(commands);
        getCommand("i").setExecutor(commands);
        getCommand("clearconsole").setExecutor(commands);
        getCommand("cleargens").setExecutor(commands);
        getCommand("stopgens").setExecutor(commands);
        getCommand("startgens").setExecutor(commands);
        getCommand("ready").setExecutor(commands);
        getCommand("lobby").setExecutor(commands);
        getCommand("send").setExecutor(commands);
        getCommand("gmc").setExecutor(commands);
        getCommand("gms").setExecutor(commands);
        getCommand("gmsp").setExecutor(commands);
        getCommand("gma").setExecutor(commands);
        getCommand("fly").setExecutor(commands);
        getCommand("clearblocks").setExecutor(commands);
        //getCommand("setinv").setExecutor(commands);


        getServer().getPluginManager().registerEvents(new BedwarsEvents(this), this);

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        getPlayers();

        checkYLevel();
        generators.GenerateIron();
        generators.GenerateGold();
        generators.GenerateDiamonds();
        generators.GenerateEmeralds();
        //mapResetChecker();
        checkMinerFatigue();
        potChecker();
        regenHealth();
        checkHealPool();
        checkSword();
        Teams.scoreboard();
        checkArmor();
        checkUpgradeEffects();


        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Bedwars] Plugin enabled!");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Bedwars] Plugin disabled!");
    }

    /*public void mapResetChecker() {
        if (Bukkit.getServer().getOnlinePlayers().size() == 0) {
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                @Override
                public void run() {
                    try {
                        Bukkit.getServer().unloadWorld("world", true);
                        //copyDirectory("D:\\minecraft\\plugins\\server\\backup\\world", "D:\\minecraft\\plugins\\server");
                        Bukkit.getServer().createWorld(new WorldCreator("world"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, 20L, 20L); // after 5 mins, check if a player is on. if not, reset map. 6000L 6000L default.
        }
    }*/


    public void checkUpgradeEffects() {
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (SoloUpgrades.hastelevel.get(player) == 1 && !isSpectating.get(player)) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 0, false, false));
                    }

                    if (SoloUpgrades.hastelevel.get(player) == 2 && !isSpectating.get(player)) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1, false, false));
                    }

                    if (SoloUpgrades.protlevel.get(player) == 1 && !isSpectating.get(player)) {
                        for (ItemStack item : player.getInventory().getArmorContents()) {
                            try {
                                item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                            }

                            catch (Exception e) {

                            }
                        }
                    }

                    if (SoloUpgrades.protlevel.get(player) == 2 && !isSpectating.get(player)) {
                        for (ItemStack item : player.getInventory().getArmorContents()) {
                            try {
                                item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                            }

                            catch (Exception e) {

                            }
                        }
                    }

                    if (SoloUpgrades.protlevel.get(player) == 3 && !isSpectating.get(player)) {
                        for (ItemStack item : player.getInventory().getArmorContents()) {
                            try {
                                item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
                            }

                            catch (Exception e) {

                            }
                        }
                    }

                    if (SoloUpgrades.protlevel.get(player) == 4 && !isSpectating.get(player)) {
                        for (ItemStack item : player.getInventory().getArmorContents()) {
                            try {
                                item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                            }

                            catch (Exception e) {

                            }
                        }
                    }
                }
            }
        }, 300L, 5L);
    }

    // Stupid function because of bad code, may redo later
    public void getPlayers() {
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (Teams.red.getEntries().contains(player.toString())) {
                        redPlayer = player;
                    }

                    if (Teams.blue.getEntries().contains(player.toString())) {
                        bluePlayer = player;
                    }

                    if (Teams.green.getEntries().contains(player.toString())) {
                        greenPlayer = player;
                    }

                    if (Teams.yellow.getEntries().contains(player.toString())) {
                        yellowPlayer = player;
                    }

                    if (Teams.aqua.getEntries().contains(player.toString())) {
                        aquaPlayer = player;
                    }

                    if (Teams.white.getEntries().contains(player.toString())) {
                        whitePlayer = player;
                    }

                    if (Teams.pink.getEntries().contains(player.toString())) {
                        pinkPlayer = player;
                    }

                    if (Teams.gray.getEntries().contains(player.toString())) {
                        grayPlayer = player;
                    }
                }
            }
        }, 400L, 5L);
    }

    public void checkYLevel() {
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (Player player: Bukkit.getOnlinePlayers()) {
                    if (player.getLocation().getY() < -53) {

                        if (BedwarsEvents.picklevel.containsKey(player)) {
                            if (BedwarsEvents.picklevel.get(player) > 1) {
                                BedwarsEvents.picklevel.put(player, BedwarsEvents.picklevel.get(player) - 1);
                            }
                        }

                        if (BedwarsEvents.axelevel.containsKey(player)) {
                            if (BedwarsEvents.axelevel.get(player) > 1) {
                                BedwarsEvents.axelevel.put(player, BedwarsEvents.axelevel.get(player) - 1);
                            }
                        }

                        Player pl = player;
                        if (Teams.red.getEntries().contains(pl.toString())) {
                            if (BedwarsEvents.RedCanDie == true) {
                                assert player != null;

                                if (BedwarsEvents.lastAttacker.containsKey(pl)) {
                                    Player lastAttacker = BedwarsEvents.lastAttacker.get(pl);



                                    lastAttacker.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§6+8 coins"));
                                    lastAttacker.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);

                                    if (Teams.blue.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.RED + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§9" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.green.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.RED + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§a" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.yellow.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.RED + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§e" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.aqua.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.RED + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§b" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.white.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.RED + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§f" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.pink.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.RED + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§d" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }
                                    if (Teams.gray.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.RED + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§8" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                }

                                else {
                                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                                        player1.sendMessage(ChatColor.RED + player.getPlayerListName() + ChatColor.GRAY + " fell into the void.");
                                    }
                                }

                                isSpectating.put(pl, true);
                                doesntNeedArmor.put(pl, true);
                                player.getInventory().clear();
                                player.getInventory().clear();
                                gameMode = player.getGameMode();
                                player.setGameMode(GameMode.SPECTATOR);
                                player.setAllowFlight(true);
                                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10000000, 1, true));
                                player.teleport(new Location(BedwarsEvents.world, 0, 105, 0));

                                new BukkitRunnable() {

                                    private int seconds = 5;

                                    @Override
                                    public void run() {
                                        if (seconds > 1) {
                                            player.sendTitle(ChatColor.RED + "YOU DIED!", ChatColor.YELLOW + "You will respawn in "
                                                            + ChatColor.RED + seconds
                                                            + ChatColor.YELLOW + " seconds!"
                                                    , 0, 21, 0);
                                            seconds--;
                                        } else if (seconds == 1) {
                                            player.sendTitle(ChatColor.RED + "YOU DIED!", ChatColor.YELLOW + "You will respawn in "
                                                            + ChatColor.RED + seconds
                                                            + ChatColor.YELLOW + " second!"
                                                    , 0, 21, 0);
                                            seconds--;
                                        } else {
                                            player.setVelocity(new Vector(0, 0, 0));
                                            player.setFallDistance(0);
                                            player.setAllowFlight(false);
                                            for (PotionEffect effect : player.getActivePotionEffects()) {
                                                player.removePotionEffect(effect.getType());
                                            }
                                            player.setGameMode(gameMode);
                                            player.removePotionEffect(PotionEffectType.INVISIBILITY);
                                            player.setHealth(20);
                                            player.teleport(BedwarsEvents.redteam);
                                            player.getInventory().setItem(player.getInventory().firstEmpty(), new ItemStack(Material.WOODEN_SWORD, 1));
                                            isSpectating.put(pl, false);
                                            doesntNeedArmor.put(pl, false);
                                            this.cancel();
                                        }
                                    }
                                }.runTaskTimer(JavaPlugin.getProvidingPlugin(Bedwars.class), 0, 20);
                            }

                            else {
                                if (BedwarsEvents.lastAttacker.containsKey(pl)) {
                                    Player lastAttacker = BedwarsEvents.lastAttacker.get(pl);



                                    lastAttacker.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§6+8 coins"));
                                    lastAttacker.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);

                                    if (Teams.blue.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.RED + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§9" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.green.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.RED + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§a" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.yellow.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.RED + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§e" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.aqua.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.RED + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§b" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.white.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.RED + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§f" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.pink.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.RED + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§d" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }
                                    if (Teams.gray.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.RED + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§8" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                }

                                else {
                                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                                        player1.sendMessage(ChatColor.RED + player.getPlayerListName() + ChatColor.GRAY + " fell into the void. " + ChatColor.AQUA + "§lFINAL KILL!");
                                    }
                                }

                                player.getWorld().strikeLightning(player.getLocation());
                                player.setGameMode(GameMode.SPECTATOR);
                                player.teleport(new Location(BedwarsEvents.world, 0, 105, 0));
                                player.sendMessage(ChatColor.RED + "You lost the game!");
                                Teams.objective.getScoreboard().resetScores("§cR " + "§fRed: " + Teams.redStr);
                                Teams.redStr = "§c✗";
                                Teams.objective.getScore("§cR " + "§fRed: " + Teams.redStr).setScore(10);
                            }
                        }










                        // BLUE team
                        if (Teams.blue.getEntries().contains(pl.toString())) {
                            if (BedwarsEvents.BlueCanDie == true) {
                                assert player != null;

                                if (BedwarsEvents.lastAttacker.containsKey(pl)) {
                                    Player lastAttacker = BedwarsEvents.lastAttacker.get(pl);



                                    lastAttacker.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§6+8 coins"));
                                    lastAttacker.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);

                                    if (Teams.red.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.BLUE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§c" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.green.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.BLUE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§a" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.yellow.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.BLUE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§e" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.aqua.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.BLUE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§b" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.white.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.BLUE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§f" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.pink.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.BLUE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§d" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }
                                    if (Teams.gray.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.BLUE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§8" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                }

                                else {
                                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                                        player1.sendMessage(ChatColor.BLUE + player.getPlayerListName() + ChatColor.GRAY + " fell into the void.");
                                    }
                                }

                                isSpectating.put(pl, true);
                                doesntNeedArmor.put(pl, true);
                                player.getInventory().clear();
                                gameMode = player.getGameMode();
                                player.setGameMode(GameMode.SPECTATOR);
                                player.setAllowFlight(true);
                                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10000000, 1, true));
                                player.teleport(new Location(BedwarsEvents.world, 0, 105, 0));

                                new BukkitRunnable() {

                                    private int seconds = 5;

                                    @Override
                                    public void run() {
                                        if (seconds > 1) {
                                            player.sendTitle(ChatColor.RED + "YOU DIED!", ChatColor.YELLOW + "You will respawn in "
                                                            + ChatColor.RED + seconds
                                                            + ChatColor.YELLOW + " seconds!"
                                                    , 0, 21, 0);
                                            seconds--;
                                        } else if (seconds == 1) {
                                            player.sendTitle(ChatColor.RED + "YOU DIED!", ChatColor.YELLOW + "You will respawn in "
                                                            + ChatColor.RED + seconds
                                                            + ChatColor.YELLOW + " second!"
                                                    , 0, 21, 0);
                                            seconds--;
                                        } else {
                                            player.setAllowFlight(false);
                                            player.setGameMode(gameMode);
                                            player.removePotionEffect(PotionEffectType.INVISIBILITY);
                                            player.setHealth(20);
                                            player.teleport(BedwarsEvents.blueteam);
                                            for (PotionEffect effect : player.getActivePotionEffects()) {
                                                player.removePotionEffect(effect.getType());
                                            }
                                            player.getInventory().setItem(player.getInventory().firstEmpty(), new ItemStack(Material.WOODEN_SWORD, 1));
                                            isSpectating.put(pl, false);
                                            doesntNeedArmor.put(pl, false);
                                            this.cancel();
                                        }
                                    }
                                }.runTaskTimer(JavaPlugin.getProvidingPlugin(Bedwars.class), 0, 20);
                            }

                            else {
                                if (BedwarsEvents.lastAttacker.containsKey(pl)) {
                                    Player lastAttacker = BedwarsEvents.lastAttacker.get(pl);



                                    lastAttacker.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§6+8 coins"));
                                    lastAttacker.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);

                                    if (Teams.red.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.BLUE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§c" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.green.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.BLUE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§a" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.yellow.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.BLUE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§e" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.aqua.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.BLUE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§b" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.white.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.BLUE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§f" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.pink.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.BLUE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§d" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }
                                    if (Teams.gray.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.BLUE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§8" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                }

                                else {
                                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                                        player1.sendMessage(ChatColor.BLUE + player.getPlayerListName() + ChatColor.GRAY + " fell into the void. " + ChatColor.AQUA + "§lFINAL KILL!");
                                    }
                                }
                                player.getWorld().strikeLightning(player.getLocation());
                                player.setGameMode(GameMode.SPECTATOR);
                                player.teleport(new Location(BedwarsEvents.world, 0, 105, 0));
                                player.sendMessage(ChatColor.RED + "You lost the game!");
                                Teams.objective.getScoreboard().resetScores("§9B " + "§fBlue: " + Teams.blueStr);
                                Teams.blueStr = "§c✗";
                                Teams.objective.getScore("§9B " + "§fBlue: " + Teams.blueStr).setScore(9);
                            }
                        }










                        // GREEN team
                        if (Teams.green.getEntries().contains(pl.toString())) {
                            if (BedwarsEvents.GreenCanDie == true) {
                                assert player != null;

                                if (BedwarsEvents.lastAttacker.containsKey(pl)) {
                                    Player lastAttacker = BedwarsEvents.lastAttacker.get(pl);



                                    lastAttacker.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§6+8 coins"));
                                    lastAttacker.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);

                                    if (Teams.red.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.GREEN + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§c" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.blue.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.GREEN + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§9" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.yellow.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.GREEN + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§e" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.aqua.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.GREEN + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§b" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.white.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.GREEN + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§f" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.pink.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.GREEN + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§d" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }
                                    if (Teams.gray.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.GREEN + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§8" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                }

                                else {
                                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                                        player1.sendMessage(ChatColor.GREEN + player.getPlayerListName() + ChatColor.GRAY + " fell into the void.");
                                    }
                                }

                                isSpectating.put(pl, true);
                                doesntNeedArmor.put(pl, true);
                                player.getInventory().clear();
                                gameMode = player.getGameMode();
                                player.setGameMode(GameMode.SPECTATOR);
                                player.setAllowFlight(true);
                                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10000000, 1, true));
                                player.teleport(new Location(BedwarsEvents.world, 0, 105, 0));

                                new BukkitRunnable() {

                                    private int seconds = 5;

                                    @Override
                                    public void run() {
                                        if (seconds > 1) {
                                            player.sendTitle(ChatColor.RED + "YOU DIED!", ChatColor.YELLOW + "You will respawn in "
                                                            + ChatColor.RED + seconds
                                                            + ChatColor.YELLOW + " seconds!"
                                                    , 0, 21, 0);
                                            seconds--;
                                        } else if (seconds == 1) {
                                            player.sendTitle(ChatColor.RED + "YOU DIED!", ChatColor.YELLOW + "You will respawn in "
                                                            + ChatColor.RED + seconds
                                                            + ChatColor.YELLOW + " second!"
                                                    , 0, 21, 0);
                                            seconds--;
                                        } else {
                                            player.setAllowFlight(false);
                                            player.setGameMode(gameMode);
                                            player.removePotionEffect(PotionEffectType.INVISIBILITY);
                                            player.teleport(BedwarsEvents.greenteam);
                                            for (PotionEffect effect : player.getActivePotionEffects()) {
                                                player.removePotionEffect(effect.getType());
                                            }
                                            player.setHealth(20);
                                            player.getInventory().setItem(player.getInventory().firstEmpty(), new ItemStack(Material.WOODEN_SWORD, 1));
                                            isSpectating.put(pl, false);
                                            doesntNeedArmor.put(pl, false);
                                            this.cancel();
                                        }
                                    }
                                }.runTaskTimer(JavaPlugin.getProvidingPlugin(Bedwars.class), 0, 20);
                            }

                            else {
                                if (BedwarsEvents.lastAttacker.containsKey(pl)) {
                                    Player lastAttacker = BedwarsEvents.lastAttacker.get(pl);



                                    lastAttacker.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§6+8 coins"));
                                    lastAttacker.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);

                                    if (Teams.red.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.GREEN + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§c" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.blue.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.GREEN + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§9" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.yellow.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.GREEN + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§e" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.aqua.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.GREEN + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§b" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.white.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.GREEN + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§f" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.pink.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.GREEN + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§d" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }
                                    if (Teams.gray.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.GREEN + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§8" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                }

                                else {
                                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                                        player1.sendMessage(ChatColor.GREEN + player.getPlayerListName() + ChatColor.GRAY + " fell into the void. " + ChatColor.AQUA + "§lFINAL KILL!");
                                    }
                                }
                                player.getWorld().strikeLightning(player.getLocation());
                                player.setGameMode(GameMode.SPECTATOR);
                                player.teleport(new Location(BedwarsEvents.world, 0, 105, 0));
                                player.sendMessage(ChatColor.RED + "You lost the game!");
                                Teams.objective.getScoreboard().resetScores("§aG " + "§fGreen: " + Teams.greenStr);
                                Teams.greenStr = "§c✗";
                                Teams.objective.getScore("§aG " + "§fGreen: " + Teams.greenStr).setScore(8);
                            }
                        }










                        // YELLOW team
                        if (Teams.yellow.getEntries().contains(pl.toString())) {
                            if (BedwarsEvents.YellowCanDie == true) {
                                assert player != null;

                                if (BedwarsEvents.lastAttacker.containsKey(pl)) {
                                    Player lastAttacker = BedwarsEvents.lastAttacker.get(pl);



                                    lastAttacker.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§6+8 coins"));
                                    lastAttacker.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);

                                    if (Teams.red.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.YELLOW + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§c" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.blue.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.YELLOW + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§9" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.green.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.YELLOW + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§a" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.aqua.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.YELLOW + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§b" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.white.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.YELLOW + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§f" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.pink.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.YELLOW + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§d" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }
                                    if (Teams.gray.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.YELLOW + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§8" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                }

                                else {
                                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                                        player1.sendMessage(ChatColor.YELLOW + player.getPlayerListName() + ChatColor.GRAY + " fell into the void.");
                                    }
                                }

                                isSpectating.put(pl, true);
                                doesntNeedArmor.put(pl, true);
                                player.getInventory().clear();
                                gameMode = player.getGameMode();
                                player.setGameMode(GameMode.SPECTATOR);
                                player.setAllowFlight(true);
                                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10000000, 1, true));
                                player.teleport(new Location(BedwarsEvents.world, 0, 105, 0));

                                new BukkitRunnable() {

                                    private int seconds = 5;

                                    @Override
                                    public void run() {
                                        if (seconds > 1) {
                                            player.sendTitle(ChatColor.RED + "YOU DIED!", ChatColor.YELLOW + "You will respawn in "
                                                            + ChatColor.RED + seconds
                                                            + ChatColor.YELLOW + " seconds!"
                                                    , 0, 21, 0);
                                            seconds--;
                                        } else if (seconds == 1) {
                                            player.sendTitle(ChatColor.RED + "YOU DIED!", ChatColor.YELLOW + "You will respawn in "
                                                            + ChatColor.RED + seconds
                                                            + ChatColor.YELLOW + " second!"
                                                    , 0, 21, 0);
                                            seconds--;
                                        } else {
                                            player.setAllowFlight(false);
                                            player.setGameMode(gameMode);
                                            player.removePotionEffect(PotionEffectType.INVISIBILITY);
                                            player.teleport(BedwarsEvents.yellowteam);
                                            for (PotionEffect effect : player.getActivePotionEffects()) {
                                                player.removePotionEffect(effect.getType());
                                            }
                                            player.setHealth(20);
                                            player.getInventory().setItem(player.getInventory().firstEmpty(), new ItemStack(Material.WOODEN_SWORD, 1));
                                            isSpectating.put(pl, false);
                                            doesntNeedArmor.put(pl, false);
                                            this.cancel();
                                        }
                                    }
                                }.runTaskTimer(JavaPlugin.getProvidingPlugin(Bedwars.class), 0, 20);
                            }

                            else {
                                if (BedwarsEvents.lastAttacker.containsKey(pl)) {
                                    Player lastAttacker = BedwarsEvents.lastAttacker.get(pl);



                                    lastAttacker.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§6+8 coins"));
                                    lastAttacker.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);

                                    if (Teams.red.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.YELLOW + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§c" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.blue.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.YELLOW + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§9" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.green.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.YELLOW + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§a" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.aqua.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.YELLOW + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§b" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.white.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.YELLOW + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§f" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.pink.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.YELLOW + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§d" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }
                                    if (Teams.gray.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.YELLOW + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§8" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                }

                                else {
                                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                                        player1.sendMessage(ChatColor.YELLOW + player.getPlayerListName() + ChatColor.GRAY + " fell into the void. " + ChatColor.AQUA + "§lFINAL KILL!");
                                    }
                                }
                                player.getWorld().strikeLightning(player.getLocation());
                                player.setGameMode(GameMode.SPECTATOR);
                                player.teleport(new Location(BedwarsEvents.world, 0, 105, 0));
                                player.sendMessage(ChatColor.RED + "You lost the game!");
                                Teams.objective.getScoreboard().resetScores("§eY " + "§fYellow: " + Teams.yellowStr);
                                Teams.yellowStr = "§c✗";
                                Teams.objective.getScore("§eY " + "§fYellow: " + Teams.yellowStr).setScore(7);
                            }
                        }











                        // AQUA team
                        if (Teams.aqua.getEntries().contains(pl.toString())) {
                            if (BedwarsEvents.AquaCanDie == true) {
                                assert player != null;

                                if (BedwarsEvents.lastAttacker.containsKey(pl)) {
                                    Player lastAttacker = BedwarsEvents.lastAttacker.get(pl);



                                    lastAttacker.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§6+8 coins"));
                                    lastAttacker.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);

                                    if (Teams.red.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.AQUA + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§c" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.blue.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.AQUA + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§9" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.green.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.AQUA + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§a" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.yellow.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.AQUA + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§e" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.white.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.AQUA + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§f" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.pink.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.AQUA + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§d" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }
                                    if (Teams.gray.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.AQUA + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§8" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                }

                                else {
                                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                                        player1.sendMessage(ChatColor.AQUA + player.getPlayerListName() + ChatColor.GRAY + " fell into the void.");
                                    }
                                }

                                isSpectating.put(pl, true);
                                player.getInventory().clear();
                                gameMode = player.getGameMode();
                                doesntNeedArmor.put(pl, true);
                                player.setGameMode(GameMode.SPECTATOR);
                                player.setAllowFlight(true);
                                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10000000, 1, true));
                                player.teleport(new Location(BedwarsEvents.world, 0, 105, 0));

                                new BukkitRunnable() {

                                    private int seconds = 5;

                                    @Override
                                    public void run() {
                                        if (seconds > 1) {
                                            player.sendTitle(ChatColor.RED + "YOU DIED!", ChatColor.YELLOW + "You will respawn in "
                                                            + ChatColor.RED + seconds
                                                            + ChatColor.YELLOW + " seconds!"
                                                    , 0, 21, 0);
                                            seconds--;
                                        } else if (seconds == 1) {
                                            player.sendTitle(ChatColor.RED + "YOU DIED!", ChatColor.YELLOW + "You will respawn in "
                                                            + ChatColor.RED + seconds
                                                            + ChatColor.YELLOW + " second!"
                                                    , 0, 21, 0);
                                            seconds--;
                                        } else {
                                            player.setAllowFlight(false);
                                            player.setGameMode(gameMode);
                                            player.removePotionEffect(PotionEffectType.INVISIBILITY);
                                            player.teleport(BedwarsEvents.aquateam);
                                            for (PotionEffect effect : player.getActivePotionEffects()) {
                                                player.removePotionEffect(effect.getType());
                                            }
                                            player.setHealth(20);
                                            player.getInventory().setItem(player.getInventory().firstEmpty(), new ItemStack(Material.WOODEN_SWORD, 1));
                                            isSpectating.put(pl, false);
                                            this.cancel();
                                        }
                                    }
                                }.runTaskTimer(JavaPlugin.getProvidingPlugin(Bedwars.class), 0, 20);
                            }

                            else {
                                if (BedwarsEvents.lastAttacker.containsKey(pl)) {
                                    Player lastAttacker = BedwarsEvents.lastAttacker.get(pl);



                                    lastAttacker.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§6+8 coins"));
                                    lastAttacker.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);

                                    if (Teams.red.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.AQUA + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§c" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.blue.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.AQUA + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§9" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.green.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.AQUA + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§a" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.yellow.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.AQUA + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§e" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.white.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.AQUA + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§f" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.pink.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.AQUA + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§d" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }
                                    if (Teams.gray.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.AQUA + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§8" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                }

                                else {
                                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                                        player1.sendMessage(ChatColor.AQUA + player.getPlayerListName() + ChatColor.GRAY + " fell into the void. " + ChatColor.AQUA + "§lFINAL KILL!");
                                    }
                                }
                                player.getWorld().strikeLightning(player.getLocation());
                                player.setGameMode(GameMode.SPECTATOR);
                                player.teleport(new Location(BedwarsEvents.world, 0, 105, 0));
                                player.sendMessage(ChatColor.RED + "You lost the game!");
                                Teams.objective.getScoreboard().resetScores("§bA " + "§fAqua: " + Teams.aquaStr);
                                Teams.aquaStr = "§c✗";
                                Teams.objective.getScore("§bA " + "§fAqua: " + Teams.aquaStr).setScore(6);
                            }
                        }











                        // WHITE team
                        if (Teams.white.getEntries().contains(pl.toString())) {
                            if (BedwarsEvents.WhiteCanDie == true) {
                                assert player != null;

                                if (BedwarsEvents.lastAttacker.containsKey(pl)) {
                                    Player lastAttacker = BedwarsEvents.lastAttacker.get(pl);



                                    lastAttacker.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§6+8 coins"));
                                    lastAttacker.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);

                                    if (Teams.red.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.WHITE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§c" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.blue.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.WHITE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§9" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.green.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.WHITE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§a" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.yellow.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.WHITE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§e" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.aqua.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.WHITE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§b" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.pink.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.WHITE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§d" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }
                                    if (Teams.gray.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.WHITE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§8" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                }

                                else {
                                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                                        player1.sendMessage(ChatColor.WHITE + player.getPlayerListName() + ChatColor.GRAY + " fell into the void.");
                                    }
                                }

                                isSpectating.put(pl, true);
                                player.getInventory().clear();
                                doesntNeedArmor.put(pl, true);
                                gameMode = player.getGameMode();
                                player.setGameMode(GameMode.SPECTATOR);
                                player.setAllowFlight(true);
                                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10000000, 1, true));
                                player.teleport(new Location(BedwarsEvents.world, 0, 105, 0));

                                new BukkitRunnable() {

                                    private int seconds = 5;

                                    @Override
                                    public void run() {
                                        if (seconds > 1) {
                                            player.sendTitle(ChatColor.RED + "YOU DIED!", ChatColor.YELLOW + "You will respawn in "
                                                            + ChatColor.RED + seconds
                                                            + ChatColor.YELLOW + " seconds!"
                                                    , 0, 21, 0);
                                            seconds--;
                                        } else if (seconds == 1) {
                                            player.sendTitle(ChatColor.RED + "YOU DIED!", ChatColor.YELLOW + "You will respawn in "
                                                            + ChatColor.RED + seconds
                                                            + ChatColor.YELLOW + " second!"
                                                    , 0, 21, 0);
                                            seconds--;
                                        } else {
                                            player.setAllowFlight(false);
                                            player.setGameMode(gameMode);
                                            player.removePotionEffect(PotionEffectType.INVISIBILITY);
                                            player.teleport(BedwarsEvents.whiteteam);
                                            for (PotionEffect effect : player.getActivePotionEffects()) {
                                                player.removePotionEffect(effect.getType());
                                            }
                                            player.setHealth(20);
                                            player.getInventory().setItem(player.getInventory().firstEmpty(), new ItemStack(Material.WOODEN_SWORD, 1));
                                            isSpectating.put(pl, false);
                                            doesntNeedArmor.put(pl, false);
                                            this.cancel();
                                        }
                                    }
                                }.runTaskTimer(JavaPlugin.getProvidingPlugin(Bedwars.class), 0, 20);
                            }

                            else {
                                if (BedwarsEvents.lastAttacker.containsKey(pl)) {
                                    Player lastAttacker = BedwarsEvents.lastAttacker.get(pl);



                                    lastAttacker.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§6+8 coins"));
                                    lastAttacker.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);

                                    if (Teams.red.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.WHITE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§c" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.blue.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.WHITE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§9" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.green.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.WHITE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§a" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.yellow.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.WHITE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§e" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.aqua.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.WHITE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§b" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.pink.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.WHITE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§d" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }
                                    if (Teams.gray.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.WHITE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§8" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                }

                                else {
                                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                                        player1.sendMessage(ChatColor.WHITE + player.getPlayerListName() + ChatColor.GRAY + " fell into the void. " + ChatColor.AQUA + "§lFINAL KILL!");
                                    }
                                }
                                player.getWorld().strikeLightning(player.getLocation());
                                player.setGameMode(GameMode.SPECTATOR);
                                player.teleport(new Location(BedwarsEvents.world, 0, 105, 0));
                                player.sendMessage(ChatColor.RED + "You lost the game!");
                                Teams.objective.getScoreboard().resetScores("§fW " + "§fWhite: " + Teams.whiteStr);
                                Teams.whiteStr = "§c✗";
                                Teams.objective.getScore("§fW " + "§fWhite: " + Teams.whiteStr).setScore(5);
                            }
                        }










                        // PINK team
                        if (Teams.pink.getEntries().contains(pl.toString())) {
                            if (BedwarsEvents.PinkCanDie == true) {
                                assert player != null;

                                if (BedwarsEvents.lastAttacker.containsKey(pl)) {
                                    Player lastAttacker = BedwarsEvents.lastAttacker.get(pl);



                                    lastAttacker.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§6+8 coins"));
                                    lastAttacker.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);

                                    if (Teams.red.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.LIGHT_PURPLE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§c" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.blue.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.LIGHT_PURPLE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§9" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.green.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.LIGHT_PURPLE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§a" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.yellow.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.LIGHT_PURPLE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§e" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.aqua.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.LIGHT_PURPLE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§b" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.white.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.LIGHT_PURPLE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§f" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }
                                    if (Teams.gray.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.LIGHT_PURPLE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§8" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                }

                                else {
                                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                                        player1.sendMessage(ChatColor.LIGHT_PURPLE + player.getPlayerListName() + ChatColor.GRAY + " fell into the void.");
                                    }
                                }

                                isSpectating.put(pl, true);
                                player.getInventory().clear();
                                doesntNeedArmor.put(pl, true);
                                gameMode = player.getGameMode();
                                player.setGameMode(GameMode.SPECTATOR);
                                player.setAllowFlight(true);
                                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10000000, 1, true));
                                player.teleport(new Location(BedwarsEvents.world, 0, 105, 0));

                                new BukkitRunnable() {

                                    private int seconds = 5;

                                    @Override
                                    public void run() {
                                        if (seconds > 1) {
                                            player.sendTitle(ChatColor.RED + "YOU DIED!", ChatColor.YELLOW + "You will respawn in "
                                                            + ChatColor.RED + seconds
                                                            + ChatColor.YELLOW + " seconds!"
                                                    , 0, 21, 0);
                                            seconds--;
                                        } else if (seconds == 1) {
                                            player.sendTitle(ChatColor.RED + "YOU DIED!", ChatColor.YELLOW + "You will respawn in "
                                                            + ChatColor.RED + seconds
                                                            + ChatColor.YELLOW + " second!"
                                                    , 0, 21, 0);
                                            seconds--;
                                        } else {
                                            player.setAllowFlight(false);
                                            player.setGameMode(gameMode);
                                            player.removePotionEffect(PotionEffectType.INVISIBILITY);
                                            player.teleport(BedwarsEvents.pinkteam);
                                            for (PotionEffect effect : player.getActivePotionEffects()) {
                                                player.removePotionEffect(effect.getType());
                                            }
                                            player.setHealth(20);
                                            player.getInventory().setItem(player.getInventory().firstEmpty(), new ItemStack(Material.WOODEN_SWORD, 1));
                                            isSpectating.put(pl, false);
                                            doesntNeedArmor.put(pl, false);
                                            this.cancel();
                                        }
                                    }
                                }.runTaskTimer(JavaPlugin.getProvidingPlugin(Bedwars.class), 0, 20);
                            }

                            else {
                                if (BedwarsEvents.lastAttacker.containsKey(pl)) {
                                    Player lastAttacker = BedwarsEvents.lastAttacker.get(pl);



                                    lastAttacker.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§6+8 coins"));
                                    lastAttacker.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);

                                    if (Teams.red.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.LIGHT_PURPLE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§c" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.blue.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.LIGHT_PURPLE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§9" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.green.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.LIGHT_PURPLE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§a" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.yellow.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.LIGHT_PURPLE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§e" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.aqua.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.LIGHT_PURPLE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§b" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.white.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.LIGHT_PURPLE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§f" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }
                                    if (Teams.gray.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.LIGHT_PURPLE + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§8" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                }

                                else {
                                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                                        player1.sendMessage(ChatColor.LIGHT_PURPLE + player.getPlayerListName() + ChatColor.GRAY + " fell into the void. " + ChatColor.AQUA + "§lFINAL KILL!");
                                    }
                                }
                                player.getWorld().strikeLightning(player.getLocation());
                                player.setGameMode(GameMode.SPECTATOR);
                                player.teleport(new Location(BedwarsEvents.world, 0, 105, 0));
                                player.sendMessage(ChatColor.RED + "You lost the game!");
                                Teams.objective.getScoreboard().resetScores("§dP " + "§fPink: " + Teams.pinkStr);
                                Teams.pinkStr = "§c✗";
                                Teams.objective.getScore("§dP " + "§fPink: " + Teams.pinkStr).setScore(4);
                            }
                        }










                        // GRAY team
                        if (Teams.gray.getEntries().contains(pl.toString())) {
                            if (BedwarsEvents.GrayCanDie == true) {
                                assert player != null;

                                if (BedwarsEvents.lastAttacker.containsKey(pl)) {
                                    Player lastAttacker = BedwarsEvents.lastAttacker.get(pl);

                                    lastAttacker.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§6+8 coins"));
                                    lastAttacker.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);

                                    if (Teams.red.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.DARK_GRAY + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§c" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.blue.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.DARK_GRAY + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§9" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.green.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.DARK_GRAY + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§a" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.yellow.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.DARK_GRAY + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§e" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.aqua.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.DARK_GRAY + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§b" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                    if (Teams.white.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.DARK_GRAY + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§f" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }
                                    if (Teams.pink.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.DARK_GRAY + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§d" + lastAttacker.getDisplayName() + "§7.");
                                        }
                                    }

                                }

                                else {
                                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                                        player1.sendMessage(ChatColor.DARK_GRAY + player.getPlayerListName() + ChatColor.GRAY + " fell into the void.");
                                    }
                                }

                                isSpectating.put(pl, true);
                                player.getInventory().clear();
                                gameMode = player.getGameMode();
                                player.setGameMode(GameMode.SPECTATOR);
                                doesntNeedArmor.put(pl, true);
                                player.setAllowFlight(true);
                                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10000000, 1, true));
                                player.teleport(new Location(BedwarsEvents.world, 0, 105, 0));

                                new BukkitRunnable() {

                                    private int seconds = 5;

                                    @Override
                                    public void run() {
                                        if (seconds > 1) {
                                            player.sendTitle(ChatColor.RED + "YOU DIED!", ChatColor.YELLOW + "You will respawn in "
                                                            + ChatColor.RED + seconds
                                                            + ChatColor.YELLOW + " seconds!"
                                                    , 0, 21, 0);
                                            seconds--;
                                        } else if (seconds == 1) {
                                            player.sendTitle(ChatColor.RED + "YOU DIED!", ChatColor.YELLOW + "You will respawn in "
                                                            + ChatColor.RED + seconds
                                                            + ChatColor.YELLOW + " second!"
                                                    , 0, 21, 0);
                                            seconds--;
                                        } else {
                                            player.setAllowFlight(false);
                                            player.setGameMode(gameMode);
                                            player.removePotionEffect(PotionEffectType.INVISIBILITY);
                                            player.teleport(BedwarsEvents.grayteam);
                                            for (PotionEffect effect : player.getActivePotionEffects()) {
                                                player.removePotionEffect(effect.getType());
                                            }
                                            player.setHealth(20);
                                            player.getInventory().setItem(player.getInventory().firstEmpty(), new ItemStack(Material.WOODEN_SWORD, 1));
                                            isSpectating.put(pl, false);
                                            doesntNeedArmor.put(pl, false);
                                            this.cancel();
                                        }
                                    }
                                }.runTaskTimer(JavaPlugin.getProvidingPlugin(Bedwars.class), 0, 20);
                            }

                            else {
                                if (BedwarsEvents.lastAttacker.containsKey(pl)) {
                                    Player lastAttacker = BedwarsEvents.lastAttacker.get(pl);



                                    lastAttacker.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§6+8 coins"));
                                    lastAttacker.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);

                                    if (Teams.red.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.DARK_GRAY + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§c" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.blue.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.DARK_GRAY + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§9" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.green.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.DARK_GRAY + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§a" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.yellow.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.DARK_GRAY + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§e" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.aqua.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.DARK_GRAY + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§b" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                    if (Teams.white.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.DARK_GRAY + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§f" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }
                                    if (Teams.pink.getEntries().contains(lastAttacker.toString())) {
                                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                                            player1.sendMessage(ChatColor.DARK_GRAY + player.getPlayerListName() + ChatColor.GRAY + " was knocked into the void by " + "§d" + lastAttacker.getDisplayName() + "§7. " + ChatColor.AQUA + "§lFINAL KILL!");
                                        }
                                    }

                                }

                                else {
                                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                                        player1.sendMessage(ChatColor.DARK_GRAY + player.getPlayerListName() + ChatColor.GRAY + " fell into the void. " + ChatColor.AQUA + "§lFINAL KILL!");
                                    }
                                }
                                player.getWorld().strikeLightning(player.getLocation());
                                player.setGameMode(GameMode.SPECTATOR);
                                player.teleport(new Location(BedwarsEvents.world, 0, 105, 0));
                                player.sendMessage(ChatColor.RED + "You lost the game!");
                                Teams.objective.getScoreboard().resetScores("§8S " + "§fGray: " + Teams.grayStr);
                                Teams.grayStr = "§c✗";
                                Teams.objective.getScore("§8S " + "§fGray: " + Teams.grayStr).setScore(3);
                            }
                        }


                    }
                }
            }
        }, 20L, 20L);
    }

    public void regenHealth() {
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 0, false, false));
                }
            }
        }, 400L, 5L);
    }

    public void potChecker() {
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {

                        isInvis.put(player, true);

                        // changes to packet: https://www.spigotmc.org/threads/changes-to-packetplayoutentityequipment-in-1-16.452599/
                        // thread where i got help: https://www.spigotmc.org/threads/make-invisible-players-armor-invisible-too.248825/


                        // because of changes, I need lists for all the items i want to hide.
                        final List<Pair<EnumItemSlot, net.minecraft.world.item.ItemStack>> handList = new ArrayList<>();
                        final List<Pair<EnumItemSlot, net.minecraft.world.item.ItemStack>> offHandList = new ArrayList<>();
                        final List<Pair<EnumItemSlot, net.minecraft.world.item.ItemStack>> helmetList = new ArrayList<>();
                        final List<Pair<EnumItemSlot, net.minecraft.world.item.ItemStack>> chestplateList = new ArrayList<>();
                        final List<Pair<EnumItemSlot, net.minecraft.world.item.ItemStack>> leggingsList = new ArrayList<>();
                        final List<Pair<EnumItemSlot, net.minecraft.world.item.ItemStack>> bootsList = new ArrayList<>();

                        // Now, I set the itemstack for other players to null so they can't see hand/offhand.
                        handList.add(new Pair<EnumItemSlot, net.minecraft.world.item.ItemStack>(EnumItemSlot.fromName("mainhand"), CraftItemStack.asNMSCopy(null)));
                        offHandList.add(new Pair<EnumItemSlot, net.minecraft.world.item.ItemStack>(EnumItemSlot.fromName("offhand"), CraftItemStack.asNMSCopy(null)));

                        // then I do the same for the armor.
                        helmetList.add(new Pair<EnumItemSlot, net.minecraft.world.item.ItemStack>(EnumItemSlot.fromName("head"), CraftItemStack.asNMSCopy(null)));
                        chestplateList.add(new Pair<EnumItemSlot, net.minecraft.world.item.ItemStack>(EnumItemSlot.fromName("chest"), CraftItemStack.asNMSCopy(null)));
                        leggingsList.add(new Pair<EnumItemSlot, net.minecraft.world.item.ItemStack>(EnumItemSlot.fromName("legs"), CraftItemStack.asNMSCopy(null)));
                        bootsList.add(new Pair<EnumItemSlot, net.minecraft.world.item.ItemStack>(EnumItemSlot.fromName("feet"), CraftItemStack.asNMSCopy(null)));

                        // define the packets by getting player entity ID and then the list that I want to set it to
                        PacketPlayOutEntityEquipment handPacket = new PacketPlayOutEntityEquipment(player.getEntityId(), handList);
                        PacketPlayOutEntityEquipment offHandPacket = new PacketPlayOutEntityEquipment(player.getEntityId(), offHandList);

                        // same as above
                        PacketPlayOutEntityEquipment helmetPacket = new PacketPlayOutEntityEquipment(player.getEntityId(), helmetList);
                        PacketPlayOutEntityEquipment chestplatePacket = new PacketPlayOutEntityEquipment(player.getEntityId(), chestplateList);
                        PacketPlayOutEntityEquipment leggingsPacket = new PacketPlayOutEntityEquipment(player.getEntityId(), leggingsList);
                        PacketPlayOutEntityEquipment bootsPacket = new PacketPlayOutEntityEquipment(player.getEntityId(), bootsList);

                        // for nearby entities:
                        for(Entity ent : player.getNearbyEntities(100, 100, 100)) {
                            if(!(ent instanceof Player) || ent == player) continue; // check if entity is player

                            // get reciever of packets
                            Player reciever = (Player) ent;

                            // finally, send the packets!
                            //If I wanted to, I could hide the hand packets with this:
                            //((CraftPlayer)reciever).getHandle().playerConnection.sendPacket(handPacket);
                            //((CraftPlayer)reciever).getHandle().playerConnection.sendPacket(offHandPacket);

                            ((CraftPlayer)reciever).getHandle().b.sendPacket(helmetPacket);
                            ((CraftPlayer)reciever).getHandle().b.sendPacket(chestplatePacket);
                            ((CraftPlayer)reciever).getHandle().b.sendPacket(leggingsPacket);
                            ((CraftPlayer)reciever).getHandle().b.sendPacket(bootsPacket);
                        }
                    }

                    else {
                        // when I get a chance, make it add back armor
                        // for now, it's fine as they still have armor, it just appears they don't
                        isInvis.put(player, false);
                    }
                }
            }
        }, 300L, 1L);
    }

    public static ItemStack woodPickaxe = CreateItem.createItemEnchanted("§fWood Pickaxe", Material.WOODEN_PICKAXE, Collections.singletonList("§9Efficiency I"), 1, true, true, Collections.singletonList(Enchantment.DIG_SPEED), 1);
    public static ItemStack ironPickaxe = CreateItem.createItemEnchanted("§fIron Pickaxe", Material.IRON_PICKAXE, Collections.singletonList("§9Efficiency II"), 1, true, true, Collections.singletonList(Enchantment.DIG_SPEED), 2);
    public static ItemStack goldPickaxe = CreateItem.createItemEnchanted("§fGold Pickaxe", Material.GOLDEN_PICKAXE, Collections.singletonList("§9Efficiency III"), 1, true, true, Collections.singletonList(Enchantment.DIG_SPEED), 3);
    public static ItemStack diamondPickaxe = CreateItem.createItemEnchanted("§fDiamond Pickaxe", Material.DIAMOND_PICKAXE, Collections.singletonList("§9Efficiency III"), 1, true, true, Collections.singletonList(Enchantment.DIG_SPEED), 3);

    public static ItemStack woodAxe = CreateItem.createItemEnchanted("§fWood Axe", Material.WOODEN_AXE, Collections.singletonList("§9Efficiency I"), 1, true, true, Collections.singletonList(Enchantment.DIG_SPEED), 1);
    public static ItemStack stoneAxe = CreateItem.createItemEnchanted("§fStone Axe", Material.STONE_AXE, Collections.singletonList("§9Efficiency I"), 1, true, true, Collections.singletonList(Enchantment.DIG_SPEED), 1);
    public static ItemStack ironAxe = CreateItem.createItemEnchanted("§fIron Axe", Material.IRON_AXE, Collections.singletonList("§9Efficiency II"), 1, true, true, Collections.singletonList(Enchantment.DIG_SPEED), 2);
    public static ItemStack diamondAxe = CreateItem.createItemEnchanted("§fDiamond Axe", Material.DIAMOND_AXE, Collections.singletonList("§9Efficiency III"), 1, true, true, Collections.singletonList(Enchantment.DIG_SPEED), 3);


    public void checkSword() {
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (Player player: Bukkit.getOnlinePlayers()) {
                    try {
                        if (BedwarsEvents.shears.get(player) == true) {
                            if (!(player.getInventory().contains(Material.SHEARS))) {
                                if (isSpectating.get(player) == false) {
                                    player.getInventory().addItem(new ItemStack(Material.SHEARS));
                                }
                            }
                        }

                        if (BedwarsEvents.picklevel.get(player) == 1) {
                            if (!(player.getInventory().contains(Material.WOODEN_PICKAXE))) {
                                if (isSpectating.get(player) == false) {
                                    player.getInventory().addItem(woodPickaxe);
                                }
                            }
                        }

                        else if (BedwarsEvents.picklevel.get(player) == 2) {
                            if (!(player.getInventory().contains(Material.IRON_PICKAXE))) {
                                if (isSpectating.get(player) == false) {
                                    player.getInventory().addItem(ironPickaxe);
                                }
                            }
                        }

                        else if (BedwarsEvents.picklevel.get(player) == 3) {
                            if (!(player.getInventory().contains(Material.GOLDEN_PICKAXE))) {
                                if (isSpectating.get(player) == false) {
                                    player.getInventory().addItem(goldPickaxe);
                                }
                            }
                        }

                        else if (BedwarsEvents.picklevel.get(player) == 4) {
                            if (!(player.getInventory().contains(Material.DIAMOND_PICKAXE))) {
                                if (isSpectating.get(player) == false) {
                                    player.getInventory().addItem(diamondPickaxe);
                                }
                            }
                        }

                        if (player.getInventory().contains(Material.GLASS_BOTTLE)) {
                            player.getInventory().remove(Material.GLASS_BOTTLE);
                        }

                        if (BedwarsEvents.axelevel.get(player) == 1) {
                            if (!(player.getInventory().contains(Material.WOODEN_AXE))) {
                                if (isSpectating.get(player) == false) {
                                    player.getInventory().addItem(woodAxe);
                                }
                            }
                        }

                        else if (BedwarsEvents.axelevel.get(player) == 2) {
                            if (!(player.getInventory().contains(Material.STONE_AXE))) {
                                if (isSpectating.get(player) == false) {
                                    player.getInventory().addItem(stoneAxe);
                                }
                            }
                        }

                        else if (BedwarsEvents.axelevel.get(player) == 3) {
                            if (!(player.getInventory().contains(Material.IRON_AXE))) {
                                if (isSpectating.get(player) == false) {
                                    player.getInventory().addItem(ironAxe);
                                }
                            }
                        }

                        else if (BedwarsEvents.axelevel.get(player) == 4) {
                            if (!(player.getInventory().contains(Material.DIAMOND_AXE))) {
                                if (isSpectating.get(player) == false) {
                                    player.getInventory().addItem(diamondAxe);
                                }
                            }
                        }
                    }

                    catch (NullPointerException n) {
                        BedwarsCommands commands = new BedwarsCommands();
                        player.kickPlayer(ChatColor.RED + "Server reloaded, so you were kicked to avoid errors!");
                    }

                    if (BedwarsEvents.hasSharpness.get(player)) {
                        if (!isSpectating.get(player)) {
                            for (ItemStack item : player.getInventory().getContents()) {
                                if (item != null) {
                                    try {
                                        item.addEnchantment(Enchantment.DAMAGE_ALL, 1);
                                    } catch (IllegalArgumentException e) {
                                        // do nothing
                                    }
                                }
                            }
                        }

                    }

                    if (!(player.getInventory().contains(Material.WOODEN_SWORD)) && !isSpectating.get(player)) {
                        if (player.getInventory().contains(Material.STONE_SWORD) || player.getInventory().contains(Material.IRON_SWORD) || player.getInventory().contains(Material.DIAMOND_SWORD)) {
                            return;
                        } else {
                            player.getInventory().setItem(player.getInventory().firstEmpty(), new ItemStack(Material.WOODEN_SWORD));
                        }
                    }
                }
            }
        }, 0L, 5L);
    }

    public void checkHealPool() {
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (BedwarsEvents.healPool.get(player)) {
                        if (Teams.red.getEntries().contains(player.toString())) {
                            if (player.getLocation().getX() >= -47 && player.getLocation().getX() <= -31) {
                                if (player.getLocation().getZ() <= -69 && player.getLocation().getZ() >= -104) {
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 2, false, false));
                                    player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 2);
                                }
                            }
                        }

                        if (Teams.blue.getEntries().contains(player.toString())) {
                            if (player.getLocation().getX() >= 32 && player.getLocation().getX() <= 50) {
                                if (player.getLocation().getZ() <= -69 && player.getLocation().getZ() >= -104) {
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 2, false, false));
                                    player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 2);
                                }
                            }
                        }

                        if (Teams.green.getEntries().contains(player.toString())) {
                            if (player.getLocation().getX() >= 70 && player.getLocation().getX() <= 105) {
                                if (player.getLocation().getZ() <= -31 && player.getLocation().getZ() >= -47) {
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 2, false, false));
                                    player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 2);
                                }
                            }
                        }

                        if (Teams.yellow.getEntries().contains(player.toString())) {
                            if (player.getLocation().getX() >= 70 && player.getLocation().getX() <= 105) {
                                if (player.getLocation().getZ() <= 48 && player.getLocation().getZ() >= 33) {
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 2, false, false));
                                    player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 2);
                                }
                            }
                        }

                        if (Teams.aqua.getEntries().contains(player.toString())) {
                            if (player.getLocation().getX() >= 32 && player.getLocation().getX() <= 48) {
                                if (player.getLocation().getZ() <= 105 && player.getLocation().getZ() >= 71) {
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 2, false, false));
                                    player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 2);
                                }
                            }
                        }

                        if (Teams.white.getEntries().contains(player.toString())) {
                            if (player.getLocation().getX() >= -47 && player.getLocation().getX() <= -31) {
                                if (player.getLocation().getZ() <= 105 && player.getLocation().getZ() >= 70) {
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 2, false, false));
                                    player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 2);
                                }
                            }
                        }

                        if (Teams.pink.getEntries().contains(player.toString())) {
                            if (player.getLocation().getX() >= -104 && player.getLocation().getX() <= -69) {
                                if (player.getLocation().getZ() <= 48 && player.getLocation().getZ() >= 32) {
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 2, false, false));
                                    player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 2);
                                }
                            }
                        }

                        if (Teams.gray.getEntries().contains(player.toString())) {
                            if (player.getLocation().getX() >= -104 && player.getLocation().getX() <= -69) {
                                if (player.getLocation().getZ() <= -31 && player.getLocation().getZ() >= -47) {
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 2, false, false));
                                    player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 2);
                                }
                            }
                        }

                    }
                }
            }
        }, 0L, 5L);
    }

    int TaskID;

    public void checkMinerFatigue() {
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {

                for (Player player : Bukkit.getOnlinePlayers()) {
                    // check if at red team
                    if (player.getLocation().getX() >= -47 && player.getLocation().getX() <= -31) {
                        if (player.getLocation().getZ() <= -69 && player.getLocation().getZ() >= -104) {
                            if (!Teams.red.getEntries().contains(player.toString())) {
                                if (redPlayer != null) {
                                    if (TrapsInventory.hasMinerFatigue.get(redPlayer) == true) {
                                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 0));
                                        redPlayer.sendTitle(ChatColor.RED + "TRAP TRIGGERED!", ChatColor.WHITE + "Your Miner Fatigue Trap has been set off!", 5, 60, 5);
                                        redPlayer.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                                        redPlayer.sendMessage(ChatColor.RED + "Miner Fatigue Trap was set off!");
                                        TrapsInventory.hasMinerFatigue.put(redPlayer, false);
                                    }
                                }
                            }
                        }
                    }

                    if (player.getLocation().getX() >= 32 && player.getLocation().getX() <= 50) {
                        if (player.getLocation().getZ() <= -69 && player.getLocation().getZ() >= -104) {
                            if (!Teams.blue.getEntries().contains(player.toString())) {
                                if (bluePlayer != null) {
                                    if (TrapsInventory.hasMinerFatigue.get(bluePlayer) == true) {
                                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 0));
                                        bluePlayer.sendTitle(ChatColor.RED + "TRAP TRIGGERED!", ChatColor.WHITE + "Your Miner Fatigue Trap has been set off!", 5, 60, 5);
                                        bluePlayer.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                                        bluePlayer.sendMessage(ChatColor.RED + "Miner Fatigue Trap was set off!");
                                        TrapsInventory.hasMinerFatigue.put(bluePlayer, false);
                                    }
                                }
                            }
                        }
                    }

                    if (player.getLocation().getX() >= 70 && player.getLocation().getX() <= 105) {
                        if (player.getLocation().getZ() <= -31 && player.getLocation().getZ() >= -47) {
                            if (!Teams.green.getEntries().contains(player.toString())) {
                                if (greenPlayer != null) {
                                    if (TrapsInventory.hasMinerFatigue.get(greenPlayer) == true) {
                                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 0));
                                        greenPlayer.sendTitle(ChatColor.RED + "TRAP TRIGGERED!", ChatColor.WHITE + "Your Miner Fatigue Trap has been set off!", 5, 60, 5);
                                        greenPlayer.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                                        greenPlayer.sendMessage(ChatColor.RED + "Miner Fatigue Trap was set off!");
                                        TrapsInventory.hasMinerFatigue.put(greenPlayer, false);
                                    }
                                }
                            }
                        }
                    }

                    if (player.getLocation().getX() >= 70 && player.getLocation().getX() <= 105) {
                        if (player.getLocation().getZ() <= 48 && player.getLocation().getZ() >= 33) {
                            if (!Teams.yellow.getEntries().contains(player.toString())) {
                                if (yellowPlayer != null) {
                                    if (TrapsInventory.hasMinerFatigue.get(yellowPlayer) == true) {
                                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 0));
                                        yellowPlayer.sendTitle(ChatColor.RED + "TRAP TRIGGERED!", ChatColor.WHITE + "Your Miner Fatigue Trap has been set off!", 5, 60, 5);
                                        yellowPlayer.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                                        yellowPlayer.sendMessage(ChatColor.RED + "Miner Fatigue Trap was set off!");
                                        TrapsInventory.hasMinerFatigue.put(yellowPlayer, false);
                                    }
                                }
                            }
                        }
                    }

                    if (player.getLocation().getX() >= 32 && player.getLocation().getX() <= 48) {
                        if (player.getLocation().getZ() <= 105 && player.getLocation().getZ() >= 71) {
                            if (!Teams.aqua.getEntries().contains(player.toString())) {
                                if (aquaPlayer != null) {
                                    if (TrapsInventory.hasMinerFatigue.get(aquaPlayer) == true) {
                                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 0));
                                        aquaPlayer.sendTitle(ChatColor.RED + "TRAP TRIGGERED!", ChatColor.WHITE + "Your Miner Fatigue Trap has been set off!", 5, 60, 5);
                                        aquaPlayer.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                                        aquaPlayer.sendMessage(ChatColor.RED + "Miner Fatigue Trap was set off!");
                                        TrapsInventory.hasMinerFatigue.put(aquaPlayer, false);
                                    }
                                }
                            }
                        }
                    }

                    if (player.getLocation().getX() >= -47 && player.getLocation().getX() <= -31) {
                        if (player.getLocation().getZ() <= 105 && player.getLocation().getZ() >= 70) {
                            if (!Teams.white.getEntries().contains(player.toString())) {
                                if (whitePlayer != null) {
                                    if (TrapsInventory.hasMinerFatigue.get(whitePlayer) == true) {
                                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 0));
                                        whitePlayer.sendTitle(ChatColor.RED + "TRAP TRIGGERED!", ChatColor.WHITE + "Your Miner Fatigue Trap has been set off!", 5, 60, 5);
                                        whitePlayer.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                                        whitePlayer.sendMessage(ChatColor.RED + "Miner Fatigue Trap was set off!");
                                        TrapsInventory.hasMinerFatigue.put(whitePlayer, false);
                                    }
                                }
                            }
                        }
                    }

                    if (player.getLocation().getX() >= -104 && player.getLocation().getX() <= -69) {
                        if (player.getLocation().getZ() <= 48 && player.getLocation().getZ() >= 32) {
                            if (!Teams.pink.getEntries().contains(player.toString())) {
                                if (pinkPlayer != null) {
                                    if (TrapsInventory.hasMinerFatigue.get(pinkPlayer) == true) {
                                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 0));
                                        pinkPlayer.sendTitle(ChatColor.RED + "TRAP TRIGGERED!", ChatColor.WHITE + "Your Miner Fatigue Trap has been set off!", 5, 60, 5);
                                        pinkPlayer.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                                        pinkPlayer.sendMessage(ChatColor.RED + "Miner Fatigue Trap was set off!");
                                        TrapsInventory.hasMinerFatigue.put(pinkPlayer, false);
                                    }
                                }
                            }
                        }
                    }

                    if (player.getLocation().getX() >= -104 && player.getLocation().getX() <= -69) {
                        if (player.getLocation().getZ() <= -31 && player.getLocation().getZ() >= -47) {
                            if (!Teams.gray.getEntries().contains(player.toString())) {
                                if (grayPlayer != null) {
                                    if (TrapsInventory.hasMinerFatigue.get(grayPlayer) == true && grayPlayer != null) {
                                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 0));
                                        grayPlayer.sendTitle(ChatColor.RED + "TRAP TRIGGERED!", ChatColor.WHITE + "Your Miner Fatigue Trap has been set off!", 5, 60, 5);
                                        grayPlayer.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                                        grayPlayer.sendMessage(ChatColor.RED + "Miner Fatigue Trap was set off!");
                                        TrapsInventory.hasMinerFatigue.put(grayPlayer, false);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }, 20L, 5L);
    }

    public void checkArmor() {
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {

                    if (player.getInventory().contains(new ItemStack(Material.CHAINMAIL_LEGGINGS))) {
                        player.getInventory().removeItem(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                    }

                    else if (player.getInventory().contains(new ItemStack(Material.CHAINMAIL_BOOTS))) {
                        player.getInventory().removeItem(new ItemStack(Material.CHAINMAIL_BOOTS));
                    }

                    else if (player.getInventory().contains(new ItemStack(Material.IRON_LEGGINGS))) {
                        player.getInventory().removeItem(new ItemStack(Material.IRON_LEGGINGS));
                    }

                    else if (player.getInventory().contains(new ItemStack(Material.IRON_BOOTS))) {
                        player.getInventory().removeItem(new ItemStack(Material.IRON_BOOTS));
                    }

                    else if (player.getInventory().contains(new ItemStack(Material.DIAMOND_LEGGINGS))) {
                        player.getInventory().removeItem(new ItemStack(Material.DIAMOND_LEGGINGS));
                    }

                    else if (player.getInventory().contains(new ItemStack(Material.DIAMOND_BOOTS))) {
                        player.getInventory().removeItem(new ItemStack(Material.DIAMOND_BOOTS));
                    }

                    try {
                        if (!doesntNeedArmor.get(player)) {
                            if (player.getInventory().getHelmet() == null || player.getInventory().getChestplate() == null || player.getInventory().getLeggings() == null || player.getInventory().getBoots() == null) {
                                if (Teams.red.getEntries().contains(player.toString())) {
                                    if (BedwarsEvents.hasBetterArmor.get(player)) {
                                        player.getInventory().setHelmet(redHelmet);
                                        player.getInventory().setChestplate(redChestplate);
                                        player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                                        player.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                                    } else if (BedwarsEvents.hasIronArmor.get(player)) {
                                        player.getInventory().setHelmet(redHelmet);
                                        player.getInventory().setChestplate(redChestplate);
                                        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                                        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
                                    } else if (BedwarsEvents.hasDiamondArmor.get(player)) {
                                        player.getInventory().setHelmet(redHelmet);
                                        player.getInventory().setChestplate(redChestplate);
                                        player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                                        player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                                    } else {
                                        player.getInventory().setHelmet(redHelmet);
                                        player.getInventory().setChestplate(redChestplate);
                                        player.getInventory().setLeggings(redLeggings);
                                        player.getInventory().setBoots(redBoots);
                                    }
                                }

                                if (Teams.blue.getEntries().contains(player.toString())) {
                                    if (BedwarsEvents.hasBetterArmor.get(player)) {
                                        player.getInventory().setHelmet(blueHelmet);
                                        player.getInventory().setChestplate(blueChestplate);
                                        player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                                        player.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                                    } else if (BedwarsEvents.hasIronArmor.get(player)) {
                                        player.getInventory().setHelmet(blueHelmet);
                                        player.getInventory().setChestplate(blueChestplate);
                                        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                                        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
                                    } else if (BedwarsEvents.hasDiamondArmor.get(player)) {
                                        player.getInventory().setHelmet(blueHelmet);
                                        player.getInventory().setChestplate(blueChestplate);
                                        player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                                        player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                                    } else {
                                        player.getInventory().setHelmet(blueHelmet);
                                        player.getInventory().setChestplate(blueChestplate);
                                        player.getInventory().setLeggings(blueLeggings);
                                        player.getInventory().setBoots(blueBoots);
                                    }
                                }

                                if (Teams.green.getEntries().contains(player.toString())) {
                                    if (BedwarsEvents.hasBetterArmor.get(player)) {
                                        player.getInventory().setHelmet(greenHelmet);
                                        player.getInventory().setChestplate(greenChestplate);
                                        player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                                        player.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                                    } else if (BedwarsEvents.hasIronArmor.get(player)) {
                                        player.getInventory().setHelmet(greenHelmet);
                                        player.getInventory().setChestplate(greenChestplate);
                                        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                                        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
                                    } else if (BedwarsEvents.hasDiamondArmor.get(player)) {
                                        player.getInventory().setHelmet(greenHelmet);
                                        player.getInventory().setChestplate(greenChestplate);
                                        player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                                        player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                                    } else {
                                        player.getInventory().setHelmet(greenHelmet);
                                        player.getInventory().setChestplate(greenChestplate);
                                        player.getInventory().setLeggings(greenLeggings);
                                        player.getInventory().setBoots(greenBoots);
                                    }
                                }

                                if (Teams.yellow.getEntries().contains(player.toString())) {
                                    if (BedwarsEvents.hasBetterArmor.get(player)) {
                                        player.getInventory().setHelmet(yellowHelmet);
                                        player.getInventory().setChestplate(yellowChestplate);
                                        player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                                        player.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                                    } else if (BedwarsEvents.hasIronArmor.get(player)) {
                                        player.getInventory().setHelmet(yellowHelmet);
                                        player.getInventory().setChestplate(yellowChestplate);
                                        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                                        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
                                    } else if (BedwarsEvents.hasDiamondArmor.get(player)) {
                                        player.getInventory().setHelmet(yellowHelmet);
                                        player.getInventory().setChestplate(yellowChestplate);
                                        player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                                        player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                                    } else {
                                        player.getInventory().setHelmet(yellowHelmet);
                                        player.getInventory().setChestplate(yellowChestplate);
                                        player.getInventory().setLeggings(yellowLeggings);
                                        player.getInventory().setBoots(yellowBoots);
                                    }
                                }

                                if (Teams.aqua.getEntries().contains(player.toString())) {
                                    if (BedwarsEvents.hasBetterArmor.get(player)) {
                                        player.getInventory().setHelmet(aquaHelmet);
                                        player.getInventory().setChestplate(aquaChestplate);
                                        player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                                        player.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                                    } else if (BedwarsEvents.hasIronArmor.get(player)) {
                                        player.getInventory().setHelmet(aquaHelmet);
                                        player.getInventory().setChestplate(aquaChestplate);
                                        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                                        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
                                    } else if (BedwarsEvents.hasDiamondArmor.get(player)) {
                                        player.getInventory().setHelmet(aquaHelmet);
                                        player.getInventory().setChestplate(aquaChestplate);
                                        player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                                        player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                                    } else {
                                        player.getInventory().setHelmet(aquaHelmet);
                                        player.getInventory().setChestplate(aquaChestplate);
                                        player.getInventory().setLeggings(aquaLeggings);
                                        player.getInventory().setBoots(aquaBoots);
                                    }
                                }

                                if (Teams.white.getEntries().contains(player.toString())) {
                                    if (BedwarsEvents.hasBetterArmor.get(player)) {
                                        player.getInventory().setHelmet(whiteHelmet);
                                        player.getInventory().setChestplate(whiteChestplate);
                                        player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                                        player.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                                    } else if (BedwarsEvents.hasIronArmor.get(player)) {
                                        player.getInventory().setHelmet(whiteHelmet);
                                        player.getInventory().setChestplate(whiteChestplate);
                                        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                                        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
                                    } else if (BedwarsEvents.hasDiamondArmor.get(player)) {
                                        player.getInventory().setHelmet(whiteHelmet);
                                        player.getInventory().setChestplate(whiteChestplate);
                                        player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                                        player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                                    } else {
                                        player.getInventory().setHelmet(whiteHelmet);
                                        player.getInventory().setChestplate(whiteChestplate);
                                        player.getInventory().setLeggings(whiteLeggings);
                                        player.getInventory().setBoots(whiteBoots);
                                    }
                                }

                                if (Teams.pink.getEntries().contains(player.toString())) {
                                    if (BedwarsEvents.hasBetterArmor.get(player)) {
                                        player.getInventory().setHelmet(pinkHelmet);
                                        player.getInventory().setChestplate(pinkChestplate);
                                        player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                                        player.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                                    } else if (BedwarsEvents.hasIronArmor.get(player)) {
                                        player.getInventory().setHelmet(pinkHelmet);
                                        player.getInventory().setChestplate(pinkChestplate);
                                        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                                        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
                                    } else if (BedwarsEvents.hasDiamondArmor.get(player)) {
                                        player.getInventory().setHelmet(pinkHelmet);
                                        player.getInventory().setChestplate(pinkChestplate);
                                        player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                                        player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                                    } else {
                                        player.getInventory().setHelmet(pinkHelmet);
                                        player.getInventory().setChestplate(pinkChestplate);
                                        player.getInventory().setLeggings(pinkLeggings);
                                        player.getInventory().setBoots(pinkBoots);
                                    }
                                }

                                if (Teams.gray.getEntries().contains(player.toString())) {
                                    if (BedwarsEvents.hasBetterArmor.get(player)) {
                                        player.getInventory().setHelmet(grayHelmet);
                                        player.getInventory().setChestplate(grayChestplate);
                                        player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                                        player.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                                    } else if (BedwarsEvents.hasIronArmor.get(player)) {
                                        player.getInventory().setHelmet(grayHelmet);
                                        player.getInventory().setChestplate(grayChestplate);
                                        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                                        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
                                    } else if (BedwarsEvents.hasDiamondArmor.get(player)) {
                                        player.getInventory().setHelmet(grayHelmet);
                                        player.getInventory().setChestplate(grayChestplate);
                                        player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                                        player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                                    } else {
                                        player.getInventory().setHelmet(grayHelmet);
                                        player.getInventory().setChestplate(grayChestplate);
                                        player.getInventory().setLeggings(grayLeggings);
                                        player.getInventory().setBoots(grayBoots);
                                    }
                                }
                            }
                        }
                    }

                    catch (NullPointerException n) {
                        for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                            BedwarsCommands commands = new BedwarsCommands();
                            player.kickPlayer(ChatColor.RED + "Server reloaded, so you were kicked to avoid errors!");
                        }
                    }
                }
            }
        }, 0L, 5L);
    }
}
