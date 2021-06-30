package com.sprintkeyz.bedwars.events;

import com.sprintkeyz.bedwars.Bedwars;
import com.sprintkeyz.bedwars.commands.BedwarsCommands;
import com.sprintkeyz.bedwars.inventories.itemshops.QuickBuy;
import com.sprintkeyz.bedwars.inventories.itemshops.SoloUpgrades;
import com.sprintkeyz.bedwars.inventories.itemshops.pages.*;
import com.sprintkeyz.bedwars.inventories.selections.DelayedKBSel;
import com.sprintkeyz.bedwars.inventories.selections.TeamSelector;
import com.sprintkeyz.bedwars.inventories.itemshops.SoloUpgrades;
import com.sprintkeyz.bedwars.inventories.traps.TrapsInventory;
import com.sprintkeyz.bedwars.managers.CreateItem;
import com.sprintkeyz.bedwars.managers.Generators;
import com.sprintkeyz.bedwars.managers.Teams;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.util.Tuple;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

import java.util.*;

public class BedwarsEvents implements Listener {

    public static int countdown = 20;

    public static ArrayList<Player> onCooldownDelKB = new ArrayList<>();

    public static Boolean countdownStarted = false;

    public static GameMode gameMode;
    public static int playersJoined = 0;
    ItemStack delayedKBStick = CreateItem.createItemEnchanted("§eDelayed Knockback Stick", Material.BLAZE_ROD, Collections.singletonList("§9Right-click to configure!"), 1, true, true, Collections.singletonList(Enchantment.LURE), 1);
    ItemStack kbstick = CreateItem.createItemEnchanted("§fKnockback Stick", Material.STICK, null, 1, true, true, Collections.singletonList(Enchantment.KNOCKBACK), 1);
    public static Boolean cantakedamage = false;
    public static ArrayList<Block> playerPlacedBlocks = new ArrayList<>();
    public static HashMap<Player, Player> lastAttacker = new HashMap<>();
    public static Map<Player, Tuple<Player, Integer>> KBDelayPlayers = new HashMap<>();
    static ItemStack fireball = new ItemStack(CreateItem.createitem("Fireball", Material.FIRE_CHARGE, null, 1, true, true));
    public static HashMap<Player, Boolean> hasBetterArmor = new HashMap<Player, Boolean>();
    public static HashMap<Player, Boolean> hasIronArmor = new HashMap<Player, Boolean>();
    public static HashMap<Player, Boolean> hasDiamondArmor = new HashMap<Player, Boolean>();
    public static HashMap<Player, Boolean> hasSharpness = new HashMap<Player, Boolean>();
    public static HashMap<Player, Boolean> healPool = new HashMap<Player, Boolean>();
    public static World world = Bukkit.getServer().getWorld("world");
    public static Location redteam = new Location(world, -39.5, 63, -82.5);
    public static Location blueteam = new Location(world, 40.5, 67, -82.5);
    public static Location greenteam = new Location(world, 83.5, 67, -39.5);
    public static Location yellowteam = new Location(world, 83.5, 63, 40.5);
    public static Location aquateam = new Location(world, 40.5, 63, 83.5);
    public static Location whiteteam = new Location(world, -39.5, 67, 83.5);
    public static Location pinkteam = new Location(world, -82.5, 67, 40.5);
    public static Location grayteam = new Location(world, -82.5, 63, -39.5);
    public static Boolean RedCanDie = true;
    public static Boolean BlueCanDie = true;
    public static Boolean GreenCanDie = true;
    public static Boolean YellowCanDie = true;
    public static Boolean AquaCanDie = true;
    public static Boolean WhiteCanDie = true;
    public static Boolean PinkCanDie = true;
    public static Boolean GrayCanDie = true;
    public static Teams teams = new Teams();
    static ItemStack teamselector;
    private static Bedwars plugin;
    public static GameMode prevgm;
    public static HashMap<Player, Boolean> shears = new HashMap<Player, Boolean>();
    public static HashMap<Player, Integer> picklevel = new HashMap<Player, Integer>();
    public static HashMap<Player, Integer> axelevel = new HashMap<Player, Integer>();
    public static int taskID;

    public static ItemStack invispot = new ItemStack(Material.POTION);
    public static PotionMeta invismeta = (PotionMeta) invispot.getItemMeta();

    public static ItemStack jumppot = new ItemStack(Material.POTION);
    public static PotionMeta jumpmeta = (PotionMeta) jumppot.getItemMeta();

    public static ItemStack speedpot = new ItemStack(Material.POTION);
    public static PotionMeta speedmeta = (PotionMeta) speedpot.getItemMeta();

    public static ArrayList<Player> choosingTime = new ArrayList<>();

    public BedwarsEvents(Bedwars instance) {
        plugin = instance;
    }

    @EventHandler
    public void setMOTD(ServerListPingEvent e) {
        e.setMaxPlayers(8);
        e.setMotd("        " + ChatColor.AQUA + "A " + ChatColor.GOLD + "§lBEDWARS " + ChatColor.AQUA + "Remake By " + ChatColor.LIGHT_PURPLE + "SprintKeyz" + ChatColor.AQUA + "!" + "\n" + "                  " +
                ChatColor.DARK_PURPLE + "§lALPHA " + ChatColor.RED + "0.0.1" + ChatColor.GRAY + " [" + ChatColor.RED + "1.16.5" + ChatColor.GRAY + "]");
    }

    @EventHandler
    public static void onPlayerDisconnect(PlayerQuitEvent e) {
        if (Bukkit.getOnlinePlayers().size() == 1) {
            if (playerPlacedBlocks.size() > 0) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "clearblocks");
            }
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "reload confirm");
        }
    }

    @EventHandler
    public void onPlayerMoveGenCheck(PlayerMoveEvent e) {
        Player player = e.getPlayer();

        List<Entity> nearbyEntities = player.getNearbyEntities(3, 3, 3);
        for (Entity ent : nearbyEntities) {
            if (ent instanceof Item) {
                Item i = (Item) ent;
                List<Block> blNearby = getBlocks(i.getLocation(), 2);
                for (Block bl : blNearby) {
                    if (bl.getType() == Material.EMERALD_BLOCK) {
                        if (i.getItemStack().getType() == Material.EMERALD && i.getItemStack().getAmount() > 2) {
                            i.getItemStack().setAmount(2);
                            break;
                        }
                    }

                    else if (bl.getType() == Material.DIAMOND_BLOCK) {
                        if (i.getItemStack().getType() == Material.DIAMOND && i.getItemStack().getAmount() > 4) {
                            i.getItemStack().setAmount(4);
                            break;
                        }
                    }
                }
            }
        }
    }

    public ArrayList<Block> getBlocks(Location start, int radius){
        ArrayList<Block> blocks = new ArrayList<>();
        for(double x = start.getX() - radius; x <= start.getX() + radius; x++){
            for(double y = start.getY() - radius; y <= start.getY() + radius; y++){
                for(double z = start.getZ() - radius; z <= start.getZ() + radius; z++){
                    Location loc = new Location(start.getWorld(), x, y, z);
                    blocks.add(loc.getBlock());
                }
            }
        }
        return blocks;
    }

    @EventHandler
    public static void onBedBreak(BlockBreakEvent e) {
        if (e.getBlock().getType() == Material.RED_BED) {
            e.setDropItems(false);
            Player player = e.getPlayer();

            if (Teams.red.getEntries().contains(player.toString())) {
                e.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You can't break your own bed!");
            }
            else {
                if (Teams.red.getEntries().size() > 0) {
                    Bedwars.redPlayer.sendTitle("§cBED DESTROYED!", "§fYou will no longer respawn!", 5, 50, 5);
                }
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    if (Teams.blue.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§cRed Bed" + "§7 was destroyed by " + "§9" + player.getPlayerListName() + "§7!");
                            world.playSound(play.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.green.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§cRed Bed" + "§7 was destroyed by " + "§a" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.yellow.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§cRed Bed" + "§7 was destroyed by " + "§e" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.aqua.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§cRed Bed" + "§7 was destroyed by " + "§b" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.white.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§cRed Bed" + "§7 was destroyed by " + "§f" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.pink.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§cRed Bed" + "§7 was destroyed by " + "§d" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.gray.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§cRed Bed" + "§7 was destroyed by " + "§8" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }
                }
                RedCanDie = false;

                if (Teams.red.getEntries().size() == 0) {
                    Teams.objective.getScoreboard().resetScores("§cR " + "§fRed: " + Teams.redStr);
                    Teams.redStr = "§c✗";
                    Teams.objective.getScore("§cR " + "§fRed: " + Teams.redStr).setScore(10);
                }

                else {
                    Teams.objective.getScoreboard().resetScores("§cR " + "§fRed: " + Teams.redStr);
                    Teams.redStr = "§a1";
                    Teams.objective.getScore("§cR " + "§fRed: " + Teams.redStr).setScore(10);
                }
            }

        }





        // GREEN
        if (e.getBlock().getType() == Material.LIME_BED) {
            e.setDropItems(false);
            Player player = e.getPlayer();
            if (Teams.green.getEntries().contains(player.toString())) {
                e.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You can't break your own bed!");
            }
            else {
                if (Teams.green.getEntries().size() > 0) {
                    Bedwars.greenPlayer.sendTitle("§cBED DESTROYED!", "§fYou will no longer respawn!", 5, 50, 5);
                }
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    if (Teams.blue.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§aGreen Bed" + "§7 was destroyed by " + "§9" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);
                        }
                    }

                    if (Teams.red.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§aGreen Bed" + "§7 was destroyed by " + "§c" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.yellow.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§aGreen Bed" + "§7 was destroyed by " + "§e" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.aqua.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§aGreen Bed" + "§7 was destroyed by " + "§b" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.white.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§aGreen Bed" + "§7 was destroyed by " + "§f" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.pink.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§aGreen Bed" + "§7 was destroyed by " + "§d" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.gray.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§aGreen Bed" + "§7 was destroyed by " + "§8" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }
                }
                GreenCanDie = false;
                if (Teams.green.getEntries().size() > 0) {
                    Teams.objective.getScoreboard().resetScores("§aG " + "§fGreen: " + Teams.greenStr);
                    Teams.greenStr = "§a1";
                    Teams.objective.getScore("§aG " + "§fGreen: " + Teams.greenStr).setScore(8);
                }

                else {
                    Teams.objective.getScoreboard().resetScores("§aG " + "§fGreen: " + Teams.greenStr);
                    Teams.greenStr = "§c✗";
                    Teams.objective.getScore("§aG " + "§fGreen: " + Teams.greenStr).setScore(8);
                }
            }
        }





        // BLUE
        if (e.getBlock().getType() == Material.BLUE_BED) {
            e.setDropItems(false);
            Player player = e.getPlayer();
            if (Teams.blue.getEntries().contains(player.toString())) {
                e.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You can't break your own bed!");
            }
            else {
                if (Teams.blue.getEntries().size() > 0) {
                    Bedwars.bluePlayer.sendTitle("§cBED DESTROYED!", "§fYou will no longer respawn!", 5, 50, 5);
                }
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    if (Teams.red.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§9Blue Bed" + "§7 was destroyed by " + "§c" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);
                        }
                    }

                    if (Teams.green.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§9Blue Bed" + "§7 was destroyed by " + "§a" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.yellow.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§9Blue Bed" + "§7 was destroyed by " + "§e" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.aqua.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§9Blue Bed" + "§7 was destroyed by " + "§b" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.white.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§9Blue Bed" + "§7 was destroyed by " + "§f" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.pink.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§9Blue Bed" + "§7 was destroyed by " + "§d" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.gray.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§9Blue Bed" + "§7 was destroyed by " + "§8" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }
                }
                BlueCanDie = false;
                if (Teams.blue.getEntries().size() > 0) {
                    Teams.objective.getScoreboard().resetScores("§9B " + "§fBlue: " + Teams.blueStr);
                    Teams.blueStr = "§a1";
                    Teams.objective.getScore("§9B " + "§fBlue: " + Teams.blueStr).setScore(9);
                }

                else {
                    Teams.objective.getScoreboard().resetScores("§9B " + "§fBlue: " + Teams.blueStr);
                    Teams.blueStr = "§c✗";
                    Teams.objective.getScore("§9B " + "§fBlue: " + Teams.blueStr).setScore(9);
                }
            }
        }





        // YELLOW
        if (e.getBlock().getType() == Material.YELLOW_BED) {
            e.setDropItems(false);
            Player player = e.getPlayer();
            if (Teams.yellow.getEntries().contains(player.toString())) {
                e.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You can't break your own bed!");
            }
            else {
                if (Teams.yellow.getEntries().size() > 0) {
                    Bedwars.yellowPlayer.sendTitle("§cBED DESTROYED!", "§fYou will no longer respawn!", 5, 50, 5);
                }
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    if (Teams.red.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§eYellow Bed" + "§7 was destroyed by " + "§c" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);
                        }
                    }

                    if (Teams.green.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§eYellow Bed" + "§7 was destroyed by " + "§a" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.blue.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§eYellow Bed" + "§7 was destroyed by " + "§9" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.aqua.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§eYellow Bed" + "§7 was destroyed by " + "§b" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.white.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§eYellow Bed" + "§7 was destroyed by " + "§f" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.pink.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§eYellow Bed" + "§7 was destroyed by " + "§d" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.gray.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§eYellow Bed" + "§7 was destroyed by " + "§8" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }
                }
                YellowCanDie = false;
                if (Teams.yellow.getEntries().size() > 0) {
                    Teams.objective.getScoreboard().resetScores("§eY " + "§fYellow: " + Teams.yellowStr);
                    Teams.yellowStr = "§a1";
                    Teams.objective.getScore("§eY " + "§fYellow: " + Teams.yellowStr).setScore(7);
                }

                else {
                    Teams.objective.getScoreboard().resetScores("§eY " + "§fYellow: " + Teams.yellowStr);
                    Teams.yellowStr = "§c✗";
                    Teams.objective.getScore("§eY " + "§fYellow: " + Teams.yellowStr).setScore(7);
                }
            }
        }




        // AQUA
        if (e.getBlock().getType() == Material.CYAN_BED) {
            e.setDropItems(false);
            Player player = e.getPlayer();
            if (Teams.aqua.getEntries().contains(player.toString())) {
                e.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You can't break your own bed!");
            }
            else {
                if (Teams.aqua.getEntries().size() > 0) {
                    Bedwars.aquaPlayer.sendTitle("§cBED DESTROYED!", "§fYou will no longer respawn!", 5, 50, 5);
                }
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    if (Teams.red.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§bAqua Bed" + "§7 was destroyed by " + "§c" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);
                        }
                    }

                    if (Teams.green.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§bAqua Bed" + "§7 was destroyed by " + "§a" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.yellow.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§bAqua Bed" + "§7 was destroyed by " + "§e" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.blue.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§bAqua Bed" + "§7 was destroyed by " + "§9" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.white.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§bAqua Bed" + "§7 was destroyed by " + "§f" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.pink.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§bAqua Bed" + "§7 was destroyed by " + "§d" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.gray.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§bAqua Bed" + "§7 was destroyed by " + "§8" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }
                }
                AquaCanDie = false;
                if (Teams.aqua.getEntries().size() > 0) {
                    Teams.objective.getScoreboard().resetScores("§bA " + "§fAqua: " + Teams.aquaStr);
                    Teams.aquaStr = "§a1";
                    Teams.objective.getScore("§bA " + "§fAqua: " + Teams.aquaStr).setScore(6);
                }

                else {
                    Teams.objective.getScoreboard().resetScores("§bA " + "§fAqua: " + Teams.aquaStr);
                    Teams.aquaStr = "§c✗";
                    Teams.objective.getScore("§bA " + "§fAqua: " + Teams.aquaStr).setScore(6);
                }
            }
        }





        // WHITE
        if (e.getBlock().getType() == Material.WHITE_BED) {
            e.setDropItems(false);
            Player player = e.getPlayer();
            if (Teams.white.getEntries().contains(player.toString())) {
                e.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You can't break your own bed!");
            }
            else {
                if (Teams.white.getEntries().size() > 0) {
                    Bedwars.whitePlayer.sendTitle("§cBED DESTROYED!", "§fYou will no longer respawn!", 5, 50, 5);
                }
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    if (Teams.red.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§fWhite Bed" + "§7 was destroyed by " + "§c" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);
                        }
                    }

                    if (Teams.green.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§fWhite Bed" + "§7 was destroyed by " + "§a" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.yellow.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§fWhite Bed" + "§7 was destroyed by " + "§e" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.aqua.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§fWhite Bed" + "§7 was destroyed by " + "§b" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.blue.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§fWhite Bed" + "§7 was destroyed by " + "§9" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.pink.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§fWhite Bed" + "§7 was destroyed by " + "§d" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.gray.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§fWhite Bed" + "§7 was destroyed by " + "§8" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }
                }
                WhiteCanDie = false;
                if (Teams.white.getEntries().size() > 0) {
                    Teams.objective.getScoreboard().resetScores("§fW " + "§fWhite: " + Teams.whiteStr);
                    Teams.whiteStr = "§a1";
                    Teams.objective.getScore("§fW " + "§fWhite: " + Teams.whiteStr).setScore(5);
                }

                else {
                    Teams.objective.getScoreboard().resetScores("§fW " + "§fWhite: " + Teams.whiteStr);
                    Teams.whiteStr = "§c✗";
                    Teams.objective.getScore("§fW " + "§fWhite: " + Teams.whiteStr).setScore(5);
                }
            }
        }





        // PINK
        if (e.getBlock().getType() == Material.PINK_BED) {
            e.setDropItems(false);
            Player player = e.getPlayer();
            if (Teams.pink.getEntries().contains(player.toString())) {
                e.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You can't break your own bed!");
            }
            else {
                if (Teams.pink.getEntries().size() > 0) {
                    Bedwars.pinkPlayer.sendTitle("§cBED DESTROYED!", "§fYou will no longer respawn!", 5, 50, 5);
                }
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    if (Teams.red.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§dPink Bed" + "§7 was destroyed by " + "§c" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);
                        }
                    }

                    if (Teams.green.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§dPink Bed" + "§7 was destroyed by " + "§a" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.yellow.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§dPink Bed" + "§7 was destroyed by " + "§e" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.aqua.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§dPink Bed" + "§7 was destroyed by " + "§b" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.white.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§dPink Bed" + "§7 was destroyed by " + "§f" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.blue.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§dPink Bed" + "§7 was destroyed by " + "§9" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.gray.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§dPink Bed" + "§7 was destroyed by " + "§8" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }
                }
                PinkCanDie = false;
                if (Teams.pink.getEntries().size() > 0) {
                    Teams.objective.getScoreboard().resetScores("§dP " + "§fPink: " + Teams.pinkStr);
                    Teams.pinkStr = "§a1";
                    Teams.objective.getScore("§dP " + "§fPink: " + Teams.pinkStr).setScore(4);
                }

                else {
                    Teams.objective.getScoreboard().resetScores("§dP " + "§fPink: " + Teams.pinkStr);
                    Teams.pinkStr = "§c✗";
                    Teams.objective.getScore("§dP " + "§fPink: " + Teams.pinkStr).setScore(4);
                }
            }
        }

        if (e.getBlock().getType() == Material.GRAY_BED) {
            e.setDropItems(false);
            Player player = e.getPlayer();
            if (Teams.gray.getEntries().contains(player.toString())) {
                e.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You can't break your own bed!");
            }
            else {
                if (Teams.gray.getEntries().size() > 0) {
                    Bedwars.grayPlayer.sendTitle("§cBED DESTROYED!", "§fYou will no longer respawn!", 5, 50, 5);
                }
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    if (Teams.red.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§8Gray Bed" + "§7 was destroyed by " + "§c" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);
                        }
                    }

                    if (Teams.green.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§8Gray Bed" + "§7 was destroyed by " + "§a" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.yellow.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§8Gray Bed" + "§7 was destroyed by " + "§e" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.aqua.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§8Gray Bed" + "§7 was destroyed by " + "§b" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.white.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§8§lGray Bed" + "§7 was destroyed by " + "§f§l" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.pink.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§8§lGray Bed" + "§7 was destroyed by " + "§d§l" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }

                    if (Teams.blue.getEntries().contains(pl.toString())) {
                        for (Player play : Bukkit.getOnlinePlayers()) {
                            play.sendMessage("§f§lBED DESTRUCTION > " + "§8§lGray Bed" + "§7 was destroyed by " + "§9§l" + player.getPlayerListName() + "§7!");
                            world.playSound(pl.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);

                        }
                    }
                }
                GrayCanDie = false;

                if (Teams.gray.getEntries().size() == 0) {
                    Teams.objective.getScoreboard().resetScores("§8S " + "§fGray: " + Teams.grayStr);
                    Teams.grayStr = "§c✗";
                    Teams.objective.getScore("§8S " + "§fGray: " + Teams.grayStr).setScore(3);
                }

                else {
                    Teams.objective.getScoreboard().resetScores("§8S " + "§fGray: " + Teams.grayStr);
                    Teams.grayStr = "§a1";
                    Teams.objective.getScore("§8S " + "§fGray: " + Teams.grayStr).setScore(3);
                }
            }
        }
    }

    @EventHandler
    public static void onVoidDamage(EntityDamageEvent e) {
        if (e.getEntity().getType() == EntityType.DROPPED_ITEM) {
            return;
        }

        else {
            if (e.getEntity() instanceof Player) {
                if (e.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
                    e.setCancelled(true);
                }
            }

            else {
                e.setCancelled(false);
            }
        }
    }


    @EventHandler
    public static void onSoloShop(InventoryOpenEvent e) {
        HumanEntity pl = e.getPlayer();
        if(e.getInventory().getType() == InventoryType.MERCHANT && e.getInventory().getHolder() instanceof Villager && ((Villager) e.getInventory().getHolder()).getCustomName().equals("Item Shop")) {
            e.setCancelled(true);
            QuickBuy itemshop = new QuickBuy();
            pl.openInventory(itemshop.getInventory());
        }
    }

    @EventHandler
    public static void onSoloUpgrades(InventoryOpenEvent e) {
        HumanEntity pl = e.getPlayer();
        if(e.getInventory().getType() == InventoryType.MERCHANT && e.getInventory().getHolder() instanceof Villager && ((Villager) e.getInventory().getHolder()).getCustomName().equals("Solo Upgrades")) {
            e.setCancelled(true);
            SoloUpgrades soloUpgrades = new SoloUpgrades();
            pl.openInventory(soloUpgrades.getInventory());
        }
    }

    @EventHandler
    public static void onItemShop(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) { return; }
        if (e.getClickedInventory().getHolder() instanceof QuickBuy) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();

            if (e.getCurrentItem() == null) { return; }

            if (e.getCurrentItem().getType() == Material.WHITE_WOOL) {

                if (player.getInventory().contains(Material.IRON_INGOT, 4)) {
                    for (int i = 0; i < 4; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT));
                    }

                    if (Teams.red.getEntries().contains(player.toString())) {
                        player.getInventory().addItem(new ItemStack(Material.RED_WOOL, 16));
                    }

                    if (Teams.blue.getEntries().contains(player.toString())) {
                        player.getInventory().addItem(new ItemStack(Material.BLUE_WOOL, 16));
                    }

                    if (Teams.green.getEntries().contains(player.toString())) {
                        player.getInventory().addItem(new ItemStack(Material.LIME_WOOL, 16));
                    }

                    if (Teams.yellow.getEntries().contains(player.toString())) {
                        player.getInventory().addItem(new ItemStack(Material.YELLOW_WOOL, 16));
                    }

                    if (Teams.aqua.getEntries().contains(player.toString())) {
                        player.getInventory().addItem(new ItemStack(Material.CYAN_WOOL, 16));
                    }

                    if (Teams.white.getEntries().contains(player.toString())) {
                        player.getInventory().addItem(new ItemStack(Material.WHITE_WOOL, 16));
                    }

                    if (Teams.pink.getEntries().contains(player.toString())) {
                        player.getInventory().addItem(new ItemStack(Material.PINK_WOOL, 16));
                    }

                    if (Teams.gray.getEntries().contains(player.toString())) {
                        player.getInventory().addItem(new ItemStack(Material.GRAY_WOOL, 16));
                    }

                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Wool");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Iron!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            // if stone sword clicked
            if (e.getCurrentItem().getType() == Material.STONE_SWORD) {
                if (player.getInventory().contains(Material.IRON_INGOT, 10)) {
                    for (int i = 0; i < 10; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT));
                    }
                    if (player.getInventory().getItem(0).getType() == Material.WOODEN_SWORD) {
                        player.getInventory().setItem(0, new ItemStack(Material.STONE_SWORD));
                    }
                    else {
                        player.getInventory().addItem(new ItemStack(Material.STONE_SWORD));
                    }
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Stone Sword");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Iron!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.CHAINMAIL_BOOTS && e.getSlot() == 21) {
                if (player.getInventory().contains(Material.IRON_INGOT, 40)) {
                    if (player.getInventory().getLeggings().getType() == Material.CHAINMAIL_LEGGINGS || player.getInventory().getLeggings().getType() == Material.IRON_LEGGINGS || player.getInventory().getLeggings().getType() == Material.DIAMOND_LEGGINGS) {
                        player.sendMessage(ChatColor.RED + "You already have this item or something better!");
                    }
                    else {
                        for (int i = 0; i < 40; i++) {
                            player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT));
                        }
                        hasBetterArmor.put(player, true);
                        player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                        player.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                        player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Permanent Chainmail Armor");
                    }
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Iron!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.DIAMOND_BOOTS) {
                if (player.getInventory().contains(Material.EMERALD, 6)) {
                    for (int i=0; i<6; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.EMERALD));
                    }
                    hasBetterArmor.put(player, false);
                    hasIronArmor.put(player, false);
                    hasDiamondArmor.put(player, true);
                    player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                    player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Permanent Diamond Armor");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Emeralds!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.BOW && e.getSlot() == 23) {
                if (player.getInventory().contains(Material.GOLD_INGOT, 12)) {
                    for (int i = 0; i < 12; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT));
                    }
                    player.getInventory().addItem(new ItemStack(Material.BOW));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Bow");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Gold!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.FIRE_CHARGE) {
                if (player.getInventory().contains(Material.IRON_INGOT, 40)) {
                    for (int i = 0; i < 40; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT));
                    }
                    player.getInventory().addItem(fireball);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Fireball");
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Iron!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.TNT && e.getSlot() == 25) {
                if (player.getInventory().contains(Material.GOLD_INGOT, 4)) {
                    for (int i = 0; i < 4; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT));
                    }
                    player.getInventory().addItem(new ItemStack(Material.TNT));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " TNT");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Gold!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.OAK_PLANKS) {
                if (player.getInventory().contains(Material.GOLD_INGOT, 4)) {
                    for (int i = 0; i < 4; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT));
                    }
                    player.getInventory().addItem(new ItemStack(Material.OAK_PLANKS, 16));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Oak Wood Planks");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Gold!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.IRON_SWORD) {
                if (player.getInventory().contains(Material.GOLD_INGOT, 4)) {
                    for (int i = 0; i < 7; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT));
                    }
                    player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Iron Sword");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Gold!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.IRON_BOOTS) {
                if (player.getInventory().contains(Material.GOLD_INGOT, 12)) {
                    if (player.getInventory().getLeggings().getType() == Material.IRON_LEGGINGS || player.getInventory().getLeggings().getType() == Material.DIAMOND_LEGGINGS) {
                        player.sendMessage(ChatColor.RED + "You already have this item or something better!");
                    }
                    else {
                        for (int i = 0; i < 12; i++) {
                            player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT));
                        }

                        hasBetterArmor.put(player, false);
                        hasIronArmor.put(player, true);

                        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                        player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Permanent Iron Armor");
                    }
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Gold!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.SHEARS) {
                if (shears.get(player) == false) {
                    if (player.getInventory().contains(Material.IRON_INGOT, 20)) {
                        for (int i = 0; i < 20; i++) {
                            player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT));
                        }
                        shears.put(player, true);
                        player.getInventory().addItem(new ItemStack(Material.SHEARS));
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                        player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Permanent Shears");
                    } else {
                        player.sendMessage(ChatColor.RED + "You don't have enough Iron!");
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                    }
                }

                else {
                    player.sendMessage(ChatColor.RED + "You already have this item!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.END_STONE) {
                if (player.getInventory().contains(Material.IRON_INGOT, 24)) {
                    for (int i = 0; i < 24; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT));
                    }
                    player.getInventory().addItem(new ItemStack(Material.END_STONE, 12));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " End Stone");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Iron!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.TERRACOTTA && e.getSlot() == 33) {
                if (player.getInventory().contains(Material.IRON_INGOT, 12)) {
                    for (int i = 0; i < 12; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT));
                    }
                    player.getInventory().addItem(new ItemStack(Material.TERRACOTTA, 12));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Hardened Clay");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Iron!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.WATER_BUCKET) {
                if (player.getInventory().contains(Material.GOLD_INGOT, 3)) {
                    for (int i = 0; i < 3; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT));
                    }
                    player.getInventory().addItem(new ItemStack(Material.WATER_BUCKET));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Water Bucket");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Gold!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.GLASS) {
                if (player.getInventory().contains(Material.IRON_INGOT, 12)) {
                    for (int i = 0; i < 12; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT));
                    }
                    player.getInventory().addItem(new ItemStack(Material.GLASS, 4));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Blast-Proof Glass");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Iron!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.WOODEN_PICKAXE && e.getSlot() == 38) {
                if (player.getInventory().contains(Material.IRON_INGOT, 10)) {
                    for (int i = 0; i < 10; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT));
                    }
                    picklevel.put(player, 1);
                    player.getInventory().addItem(Bedwars.woodPickaxe);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Wooden Pickaxe (Efficiency I)");
                    player.openInventory(new QuickBuy().getInventory());
                } else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Iron!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            else if (e.getCurrentItem().getType() == Material.IRON_PICKAXE && e.getSlot() == 38) {
                if (player.getInventory().contains(Material.IRON_INGOT, 10)) {
                    for (int i = 0; i < 10; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT));
                    }
                    picklevel.put(player, 2);
                    player.getInventory().removeItem(new ItemStack[]{Bedwars.woodPickaxe});
                    player.getInventory().setItem(player.getInventory().firstEmpty(), Bedwars.ironPickaxe);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Iron Pickaxe (Efficiency II)");
                    player.openInventory(new QuickBuy().getInventory());
                } else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Iron!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            else if (e.getCurrentItem().getType() == Material.GOLDEN_PICKAXE && e.getSlot() == 38) {
                if (player.getInventory().contains(Material.GOLD_INGOT, 3)) {
                    for (int i = 0; i < 3; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT));
                    }
                    picklevel.put(player, 3);
                    player.getInventory().removeItem(new ItemStack[]{Bedwars.ironPickaxe});
                    player.getInventory().setItem(player.getInventory().firstEmpty(), Bedwars.goldPickaxe);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Golden Pickaxe (Efficiency III)");
                    player.openInventory(new QuickBuy().getInventory());
                } else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Gold!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            else if (e.getCurrentItem().getType() == Material.DIAMOND_PICKAXE && e.getSlot() == 38 && picklevel.get(player) == 3) {
                if (player.getInventory().contains(Material.GOLD_INGOT, 6)) {
                    for (int i = 0; i < 6; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT));
                    }
                    picklevel.put(player, 4);
                    player.getInventory().removeItem(new ItemStack[]{Bedwars.goldPickaxe});
                    player.getInventory().setItem(player.getInventory().firstEmpty(), Bedwars.diamondPickaxe);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Diamond Pickaxe (Efficiency III)");
                    player.openInventory(new QuickBuy().getInventory());
                } else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Gold!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            else if (picklevel.get(player) == 4 && e.getSlot() == 38) {
                player.sendMessage(ChatColor.RED + "You already have this item!");
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
            }

            if (e.getCurrentItem().getType() == Material.WOODEN_AXE) {
                if (player.getInventory().contains(Material.IRON_INGOT, 10)) {
                    for (int i = 0; i < 10; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT));
                    }
                    axelevel.put(player, 1);
                    player.getInventory().addItem(Bedwars.woodAxe);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Wooden Axe (Efficiency I)");
                    player.openInventory(new QuickBuy().getInventory());
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Iron!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            else if (e.getCurrentItem().getType() == Material.STONE_AXE) {
                if (player.getInventory().contains(Material.IRON_INGOT, 10)) {
                    for (int i = 0; i < 10; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT));
                    }
                    axelevel.put(player, 2);
                    player.getInventory().removeItem(new ItemStack[]{Bedwars.woodAxe});
                    player.getInventory().addItem(Bedwars.stoneAxe);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Stone Axe (Efficiency I)");
                    player.openInventory(new QuickBuy().getInventory());
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Iron!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            else if (e.getCurrentItem().getType() == Material.IRON_AXE) {
                if (player.getInventory().contains(Material.GOLD_INGOT, 3)) {
                    for (int i = 0; i < 3; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT));
                    }
                    axelevel.put(player, 3);
                    player.getInventory().removeItem(new ItemStack[]{Bedwars.stoneAxe});
                    player.getInventory().addItem(Bedwars.ironAxe);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Iron Axe (Efficiency II)");
                    player.openInventory(new QuickBuy().getInventory());
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Gold!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            else if (e.getCurrentItem().getType() == Material.DIAMOND_AXE && axelevel.get(player) == 3) {
                if (player.getInventory().contains(Material.GOLD_INGOT, 6)) {
                    for (int i = 0; i < 6; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT));
                    }
                    axelevel.put(player, 4);
                    player.getInventory().removeItem(new ItemStack[]{Bedwars.ironAxe});
                    player.getInventory().addItem(Bedwars.diamondAxe);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Diamond Axe (Efficiency III)");
                    player.openInventory(new QuickBuy().getInventory());
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Gold!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            else if (BedwarsEvents.axelevel.get(player) == 4 && e.getSlot() == 39){
                player.sendMessage(ChatColor.RED + "You already have this item!");
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
            }

            if (e.getCurrentItem().getType() == Material.POTION && e.getSlot() == 40) {
                if (player.getInventory().contains(Material.EMERALD, 2)) {
                    for (int i = 0; i < 2; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.EMERALD));
                    }
                    invismeta.addCustomEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 600, 0), true);
                    invismeta.setDisplayName("§fPotion of Invisibility");
                    invispot.setItemMeta(invismeta);
                    player.getInventory().addItem(invispot);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Invisibility Potion (30 Seconds)");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Emeralds!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.POTION && e.getSlot() == 41) {
                if (player.getInventory().contains(Material.EMERALD, 1)) {
                    player.getInventory().removeItem(new ItemStack(Material.EMERALD));
                    jumpmeta.addCustomEffect(new PotionEffect(PotionEffectType.JUMP, 900, 4), true);
                    jumpmeta.setDisplayName("§fPotion of Jump Boost");
                    jumppot.setItemMeta(jumpmeta);
                    player.getInventory().addItem(jumppot);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Jump V Potion (45 seconds)");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Emeralds!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.POTION && e.getSlot() == 42) {
                if (player.getInventory().contains(Material.EMERALD, 1)) {
                    player.getInventory().removeItem(new ItemStack(Material.EMERALD));
                    speedmeta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 900, 1), true);
                    speedmeta.setDisplayName("§fPotion of Speed");
                    speedpot.setItemMeta(speedmeta);
                    player.getInventory().addItem(speedpot);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Speed II Potion (45 seconds)");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Emeralds!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.GOLDEN_APPLE) {
                if (player.getInventory().contains(Material.GOLD_INGOT, 3)) {
                    for (int i = 0; i < 3; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT));
                    }
                    player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Golden Apple");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Gold!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.TERRACOTTA && e.getSlot() == 1) {
                player.openInventory(new Blocks().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.GOLDEN_SWORD) {
                player.openInventory(new Melee().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.CHAINMAIL_BOOTS && e.getSlot() == 3) {
                player.openInventory(new Armor().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.WOODEN_PICKAXE && e.getSlot() == 4) {
                player.openInventory(new Tools().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.BOW && e.getSlot() == 5) {
                player.openInventory(new Ranged().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.BREWING_STAND) {
                player.openInventory(new Potions().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.TNT && e.getSlot() == 7) {
                player.openInventory(new Utilities().getInventory());
            }
        }
    }

    @EventHandler
    public void onSoloUpgrades(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) { return; }
        if (e.getClickedInventory().getHolder() instanceof SoloUpgrades) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();

            if (e.getCurrentItem() == null) {
                return;
            }

            if (e.getCurrentItem().getType() == Material.IRON_SWORD) {
                if (!hasSharpness.get(player)) {
                    if (player.getInventory().contains(Material.DIAMOND, 4)) {
                        for (int i = 0; i < 4; i++) {
                            player.getInventory().removeItem(new ItemStack(Material.DIAMOND));
                        }
                        hasSharpness.put(player, true);
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                        player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Sharpened Swords");
                        player.openInventory(new SoloUpgrades().getInventory());
                    } else {
                        player.sendMessage(ChatColor.RED + "You don't have enough Diamonds!");
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                    }
                }
            }

            if (e.getCurrentItem().getType() == Material.BEACON) {
                if (player.getInventory().contains(Material.DIAMOND, 1)) {
                    if (healPool.get(player) == false) {
                        for (int i = 0; i < 1; i++) {
                            player.getInventory().removeItem(new ItemStack(Material.DIAMOND));
                        }
                        healPool.put(player, true);
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                        player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Heal Pool");
                        player.openInventory(new SoloUpgrades().getInventory());
                        return;
                    }

                    else {
                        player.sendMessage(ChatColor.RED + "You already have this upgrade!");
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                        player.openInventory(new SoloUpgrades().getInventory());
                        return;
                    }
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Diamonds!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                    player.openInventory(new SoloUpgrades().getInventory());
                    return;
                }
            }

            if (e.getCurrentItem().getType() == Material.GOLDEN_PICKAXE) {
                if (SoloUpgrades.hastelevel.get(player) == 0) {
                    if (player.getInventory().contains(Material.DIAMOND, 2)) {
                        for (int i = 0; i < 2; i++) {
                            player.getInventory().removeItem(new ItemStack(Material.DIAMOND));
                        }
                        SoloUpgrades.hastelevel.put(player, 1);
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                        player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Maniac Miner I");
                        player.openInventory(new SoloUpgrades().getInventory());
                    }

                    else {
                        player.sendMessage(ChatColor.RED + "You don't have enough Diamonds!");
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                    }
                }

                if (SoloUpgrades.hastelevel.get(player) == 1) {
                    if (player.getInventory().contains(Material.DIAMOND, 4)) {
                        for (int i = 0; i < 4; i++) {
                            player.getInventory().removeItem(new ItemStack(Material.DIAMOND));
                        }
                        SoloUpgrades.hastelevel.put(player, 2);
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                        player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Maniac Miner II");
                        player.openInventory(new SoloUpgrades().getInventory());
                        return;
                    }

                    else {
                        player.sendMessage(ChatColor.RED + "You don't have enough Diamonds!");
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                        return;
                    }
                }

                if (SoloUpgrades.hastelevel.get(player) == 2) {
                    player.sendMessage(ChatColor.RED + "You already have this upgrade!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                    return;
                }
            }

            if (e.getCurrentItem().getType() == Material.IRON_CHESTPLATE) {
                if (SoloUpgrades.protlevel.get(player) == 0) {
                    if (player.getInventory().contains(Material.DIAMOND, 2)) {
                        for (int i = 0; i < 2; i++) {
                            player.getInventory().removeItem(new ItemStack(Material.DIAMOND));
                        }
                        SoloUpgrades.protlevel.put(player, 1);
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                        player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Protection I");
                        player.openInventory(new SoloUpgrades().getInventory());
                        return;
                    }

                    else {
                        player.sendMessage(ChatColor.RED + "You don't have enough Diamonds!");
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                        return;
                    }
                }

                if (SoloUpgrades.protlevel.get(player) == 1) {
                    if (player.getInventory().contains(Material.DIAMOND, 4)) {
                        for (int i = 0; i < 4; i++) {
                            player.getInventory().removeItem(new ItemStack(Material.DIAMOND));
                        }
                        SoloUpgrades.protlevel.put(player, 2);
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                        player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Protection II");
                        player.openInventory(new SoloUpgrades().getInventory());
                        return;
                    }

                    else {
                        player.sendMessage(ChatColor.RED + "You don't have enough Diamonds!");
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                        return;
                    }
                }

                if (SoloUpgrades.protlevel.get(player) == 2) {
                    if (player.getInventory().contains(Material.DIAMOND, 8)) {
                        for (int i = 0; i < 8; i++) {
                            player.getInventory().removeItem(new ItemStack(Material.DIAMOND));
                        }
                        SoloUpgrades.protlevel.put(player, 3);
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                        player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Protection III");
                        player.openInventory(new SoloUpgrades().getInventory());
                        return;
                    }

                    else {
                        player.sendMessage(ChatColor.RED + "You don't have enough Diamonds!");
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                        return;
                    }
                }

                if (SoloUpgrades.protlevel.get(player) == 3) {
                    if (player.getInventory().contains(Material.DIAMOND, 16)) {
                        for (int i = 0; i < 16; i++) {
                            player.getInventory().removeItem(new ItemStack(Material.DIAMOND));
                        }
                        SoloUpgrades.protlevel.put(player, 4);
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                        player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Protection IV");
                        player.openInventory(new SoloUpgrades().getInventory());
                        return;
                    }

                    else {
                        player.sendMessage(ChatColor.RED + "You don't have enough Diamonds!");
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                        return;
                    }
                }

                if (SoloUpgrades.protlevel.get(player) == 4) {
                    player.sendMessage(ChatColor.RED + "You already have this upgrade!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                    return;
                }
            }

            if (e.getCurrentItem().getType() == Material.FURNACE) {
                Generators generators = new Generators();
                player.sendMessage("§cThis upgrade is coming soon, i'm too lazy to add and no one uses it anyways... but like i said will be coming soon :D");
            }

            if (e.getCurrentItem().getType() == Material.LEATHER) {
                TrapsInventory trapsInventory = new TrapsInventory();
                player.openInventory(trapsInventory.getInventory());
            }
        }
    }

    @EventHandler
    public static void onTrapsInv(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) { return; }
        if (e.getClickedInventory().getHolder() instanceof TrapsInventory) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();

            if (e.getCurrentItem() == null) {
                return;
            }

            if (e.getCurrentItem().getType() == Material.IRON_PICKAXE) {
                if (TrapsInventory.hasMinerFatigue.get(player) == false) {
                    if (player.getInventory().contains(Material.DIAMOND, 1)) {
                        player.getInventory().removeItem(new ItemStack(Material.DIAMOND));
                        TrapsInventory.hasMinerFatigue.put(player, true);
                        player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Miner Fatigue Trap");
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    }

                    else {
                        player.sendMessage(ChatColor.RED + "You don't have enough Diamonds!");
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                    }
                }

                else {
                    player.sendMessage(ChatColor.RED + "You already have this trap!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.ARROW) {
                player.openInventory(new SoloUpgrades().getInventory());
            }
        }
    }

    // TNT
    @EventHandler
    public static void onTNTplace(BlockPlaceEvent e) {
        if (e.getBlockPlaced().getType() == Material.TNT) {
            Location TNTloc = e.getBlockPlaced().getLocation();
            e.getBlockPlaced().setType(Material.AIR);
            TNTPrimed tnt = (TNTPrimed) e.getPlayer().getWorld().spawnEntity(TNTloc, EntityType.PRIMED_TNT);
            tnt.setFuseTicks(52);
        }
    }

    @EventHandler
    public static void onEntityExplode(ExplosionPrimeEvent e) {
        if (e.getEntity().getType() == EntityType.PRIMED_TNT) {
            e.setRadius(6);
        }
    }

    @EventHandler
    public void onTntDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof TNTPrimed) {
            Player p = (Player) e.getEntity();

            e.setCancelled(true);

            if(p.getHealth() - 2 <= 0) {
                p.setHealth(p.getHealth() - 2);
            } else {
                p.setHealth(p.getHealth() - 2 <= 0 ? 0D : p.getHealth()- 2);
            }

            p.setVelocity(new Vector(p.getVelocity().getX() * 5, p.getVelocity().getY() + 1.5, p.getVelocity().getZ() * 5));
        }

        else if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player p = (Player) e.getEntity();
            Player damager = (Player) e.getDamager();

            if (!lastAttacker.containsKey(p)) {

                lastAttacker.put(p, damager);

                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(JavaPlugin.getProvidingPlugin(BedwarsEvents.class), new Runnable() {
                    @Override
                    public void run() {
                        lastAttacker.remove(p);
                    }
                }, 600L);
            }

            else {
                lastAttacker.remove(p);
                lastAttacker.put(p, damager);
            }
        }
    }

    @EventHandler
    public static void onPlayerCraft(CraftItemEvent e) {
        Player player = (Player) e.getWhoClicked();
        e.setCancelled(true);
    }

    @EventHandler
    public static void playerLogoutEvent(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        prevgm = player.getGameMode();
    }

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent e) {

        playersJoined += 1;

        e.setJoinMessage(null);

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            Bedwars.isSpectating.put(player, true);
            cantakedamage = false;
            player.teleport(new Location(world, 0.5, 118, 0.5));
            teamselector = CreateItem.createitem("§2§lTeam Selector", Material.NOTE_BLOCK, null, 1, true, true);
            player.getInventory().clear();
            player.setFoodLevel(20);
            player.setHealth(20);

            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "nte player " + player.getName() + " clear");
            player.setDisplayName("§f" + player.getDisplayName());
            player.setPlayerListName(player.getDisplayName());

            if (Bedwars.doesntNeedArmor.containsKey(player)) {
                Bedwars.doesntNeedArmor.remove(player);
            }

            if (healPool.containsKey(player)) {
                healPool.remove(player);
            }

            if (hasSharpness.containsKey(player)) {
                hasSharpness.remove(player);
            }

            if (Bedwars.isInvis.containsKey(player)) {
                Bedwars.isInvis.remove(player);
            }

            if (hasBetterArmor.containsKey(player)) {
                hasBetterArmor.remove(player);
            }

            if (hasIronArmor.containsKey(player)) {
                hasIronArmor.remove(player);
            }

            if (hasDiamondArmor.containsKey(player)) {
                hasDiamondArmor.remove(player);
            }

            if (picklevel.containsKey(player)) {
                picklevel.remove(player);
            }

            if (axelevel.containsKey(player)) {
                axelevel.remove(player);
            }

            if (shears.containsKey(player)) {
                shears.remove(player);
            }

            shears.put(player, false);
            hasIronArmor.put(player, false);
            hasDiamondArmor.put(player, false);
            healPool.put(player, false);

            Bedwars.isInvis.put(player, false);

            picklevel.put(player, 0);
            axelevel.put(player, 0);

            TrapsInventory.hasMinerFatigue.put(player, false);
            TrapsInventory.hasAlarmTrap.put(player, false);
            TrapsInventory.hasCounteroffensive.put(player, false);
            TrapsInventory.hasItsaTrap.put(player, false);

            SoloUpgrades.hastelevel.put(player, 0);
            SoloUpgrades.protlevel.put(player, 0);

            hasSharpness.put(player, false);

            Bedwars.doesntNeedArmor.put(player, true);
            hasBetterArmor.put(player, false);
            player.setGameMode(prevgm);
            player.getInventory().removeItem(new ItemStack(Material.WOODEN_SWORD));

            for (PotionEffect effect : player.getActivePotionEffects()) {
                player.removePotionEffect(effect.getType());
            }

            player.getInventory().setItem(8, teamselector);

            //api.changeSky(SkyChanger.wrapPlayer(player), SkyPacket.RAIN_LEVEL_CHANGE, 3F);
        }

        if (playersJoined >= 2) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (countdown >= 1) {
                        if (countdown == 10) {
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "The game starts in " + ChatColor.GOLD +  countdown + ChatColor.YELLOW + " seconds!");
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                p.sendTitle(ChatColor.GREEN + "" + countdown, null, 0, 20, 0);
                            }
                        }

                        else if (countdown <= 5 && countdown > 1) {
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "The game starts in " + ChatColor.RED + countdown + ChatColor.YELLOW + " seconds!");
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                p.sendTitle(ChatColor.YELLOW + "" + countdown, null, 0, 20, 0);
                            }
                        }

                        else if (countdown == 1) {
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "The game starts in " + ChatColor.RED + countdown + ChatColor.YELLOW + " second!");
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                p.sendTitle(ChatColor.YELLOW + "" + countdown, null, 0, 20, 0);
                            }
                        }

                        countdown--;
                    } else {
                        cantakedamage = true;
                        Teams.objective.unregister();
                        Teams.scoreboard();
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "cleargens");
                        for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                            if (Teams.red.getEntries().contains(pl.toString()) ||
                                    Teams.blue.getEntries().contains(pl.toString()) ||
                                    Teams.green.getEntries().contains(pl.toString()) ||
                                    Teams.yellow.getEntries().contains(pl.toString()) ||
                                    Teams.aqua.getEntries().contains(pl.toString()) ||
                                    Teams.white.getEntries().contains(pl.toString()) ||
                                    Teams.pink.getEntries().contains(pl.toString()) ||
                                    Teams.gray.getEntries().contains(pl.toString())) {

                                pl.getInventory().clear();
                                Bedwars.isSpectating.put(pl, false);
                                if (Teams.red.getEntries().contains(pl.toString())) {
                                    pl.teleport(redteam);
                                    Bedwars.doesntNeedArmor.put(pl, false);
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "nte player " + pl.getName() + " clear");
                                    pl.setDisplayName("§c" + pl.getDisplayName() + "§f");
                                    pl.setPlayerListName(pl.getDisplayName());
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "nte player " + pl.getName() + " prefix '&c&lR&r&c '");
                                }
                                if (Teams.blue.getEntries().contains(pl.toString())) {
                                    pl.teleport(blueteam);
                                    Bedwars.doesntNeedArmor.put(pl, false);
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "nte player " + pl.getName() + " clear");
                                    pl.setDisplayName("§9" + pl.getDisplayName() + "§f");
                                    pl.setPlayerListName(pl.getDisplayName());
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "nte player " + pl.getName() + " prefix '&9&lB&r&9 '");
                                }
                                if (Teams.green.getEntries().contains(pl.toString())) {
                                    pl.teleport(greenteam);
                                    Bedwars.doesntNeedArmor.put(pl, false);
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "nte player " + pl.getName() + " clear");
                                    pl.setDisplayName("§a" + pl.getDisplayName() + "§f");
                                    pl.setPlayerListName(pl.getDisplayName());
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "nte player " + pl.getName() + " prefix '&a&lG&r&a '");
                                }
                                if (Teams.yellow.getEntries().contains(pl.toString())) {
                                    pl.teleport(yellowteam);
                                    Bedwars.doesntNeedArmor.put(pl, false);
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "nte player " + pl.getName() + " clear");
                                    pl.setDisplayName("§e" + pl.getDisplayName() + "§f");
                                    pl.setPlayerListName(pl.getDisplayName());
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "nte player " + pl.getName() + " prefix '&e&lY&r&e '");
                                }
                                if (Teams.aqua.getEntries().contains(pl.toString())) {
                                    pl.teleport(aquateam);
                                    Bedwars.doesntNeedArmor.put(pl, false);
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "nte player " + pl.getName() + " clear");
                                    pl.setDisplayName("§b" + pl.getDisplayName() + "§f");
                                    pl.setPlayerListName(pl.getDisplayName());
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "nte player " + pl.getName() + " prefix '&b&lA&r&b '");
                                }
                                if (Teams.white.getEntries().contains(pl.toString())) {
                                    pl.teleport(whiteteam);
                                    Bedwars.doesntNeedArmor.put(pl, false);
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "nte player " + pl.getName() + " clear");
                                    pl.setDisplayName("§f" + pl.getDisplayName() + "§f");
                                    pl.setPlayerListName(pl.getDisplayName());
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "nte player " + pl.getName() + " prefix '&f&lW&r&f '");
                                }
                                if (Teams.pink.getEntries().contains(pl.toString())) {
                                    pl.teleport(pinkteam);
                                    Bedwars.doesntNeedArmor.put(pl, false);
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "nte player " + pl.getName() + " clear");
                                    pl.setDisplayName("§d" + pl.getDisplayName() + "§f");
                                    pl.setPlayerListName(pl.getDisplayName());
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "nte player " + pl.getName() + " prefix '&d&lP&r&d '");
                                }
                                if (Teams.gray.getEntries().contains(pl.toString())) {
                                    pl.teleport(grayteam);
                                    Bedwars.doesntNeedArmor.put(pl, false);
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "nte player " + pl.getName() + " clear");
                                    pl.setDisplayName("§8" + pl.getDisplayName() + "§f");
                                    pl.setPlayerListName(pl.getDisplayName());
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "nte player " + pl.getName() + " prefix '&8&lS&r&8 '");
                                }

                                pl.sendMessage("§a§l--------------------------------------");
                                pl.sendMessage("                            §f§lBed Wars");
                                pl.sendMessage(" ");
                                pl.sendMessage("    §e§lProtect your bed and destroy the enemy beds.");
                                pl.sendMessage("     §e§lUpgrade yourself and your team by collecting");
                                pl.sendMessage("   §e§lIron, Gold, Emerald and Diamond from generators");
                                pl.sendMessage("            §e§lto access powerful upgrades.");
                                pl.sendMessage(" ");
                                pl.sendMessage("§a§l--------------------------------------");

                            } else {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "clearblocks");
                                for (Player player : Bukkit.getOnlinePlayers()) {
                                    /*player.sendMessage(ChatColor.RED + "Sending you to the lobby because you did not choose a team...");
                                    BedwarsCommands bedwarsCommands = new BedwarsCommands();
                                    bedwarsCommands.sendToServer(player, "Lobby");*/
                                    player.kickPlayer("You or someone else did not choose a team!");
                                }
                            }
                        }

                        this.cancel();
                    }
                }
            }.runTaskTimer(JavaPlugin.getProvidingPlugin(BedwarsEvents.class), 0, 20);
        } else {
            Bukkit.broadcastMessage(ChatColor.RED + "We need at least 2 players to start!");
        }
    }


    @EventHandler
    public static void onItemFrameRClick(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof ItemFrame) {
            event.setCancelled(true);
            ItemFrame itemFrame = (ItemFrame) event.getRightClicked();
            Location loc = itemFrame.getLocation();
            loc.getBlock().setType(Material.PAINTING);
        }
    }

    @EventHandler
    public static void playerInteractEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getItem() != null) {
            if (!e.getItem().getType().isBlock()) {
                if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if (e.getItem().getItemMeta() != null && e.getItem().getItemMeta().getLore() != null) {
                        if (e.getItem().getItemMeta().getLore().contains("§9Right-click to configure!")) {
                            player.openInventory(new DelayedKBSel().getInventory());
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPvPAttack(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (e.getDamager() instanceof Player) {
                Player damager = (Player) e.getDamager();
                if (damager.getInventory().getItemInMainHand() != null) {
                    if (damager.getInventory().getItemInMainHand().getItemMeta().getLore() != null && damager.getInventory().getItemInMainHand().getItemMeta() != null) {
                        if (damager.getInventory().getItemInMainHand().getItemMeta().getLore().contains("§9Right-click to configure!")) {
                            if (KBDelayPlayers.containsKey(damager)) {
                                if (!onCooldownDelKB.contains(damager)) {
                                    e.setCancelled(true);
                                    damager.playSound(damager.getLocation(), Sound.ENTITY_PLAYER_ATTACK_STRONG, 10, 1);
                                    player.playSound(damager.getLocation(), Sound.ENTITY_PLAYER_ATTACK_STRONG, 10, 1);
                                    damager.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 10, 1);
                                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 10, 1);
                                    int delay = KBDelayPlayers.get(damager).b();
                                    Vector vector = damager.getEyeLocation().getDirection();
                                    delayKnockback(player, delay, vector);
                                    onCooldownDelKB.add(damager);
                                    delKBCooldown(damager);
                                } else {
                                    e.setCancelled(true);
                                    damager.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§cYou must wait 10 seconds between uses!"));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void delKBCooldown(Player player) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                onCooldownDelKB.remove(player);
            }
        }, 200L); // would be 200L
    }

    public void delayKnockback(Player player, int time, Vector direction) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(JavaPlugin.getProvidingPlugin(BedwarsEvents.class), new Runnable() {
            @Override
            public void run() {
                direction.multiply(1.006);
                direction.setY(0.5);
                player.setVelocity(player.getVelocity().add(direction));
            }
        }, time * 20L);
    }

    @EventHandler
    public static void onKBSelClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) { return; }
        if (e.getClickedInventory().getHolder() instanceof DelayedKBSel) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            if (e.getCurrentItem() == null) { return; }

            if (e.getCurrentItem().getType() == Material.RED_CONCRETE) {
                KBDelayPlayers.put(player, new Tuple(null, 3));
                player.sendMessage(ChatColor.GREEN + "Set delay to 3 seconds!");
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
            }

            if (e.getCurrentItem().getType() == Material.ORANGE_CONCRETE) {
                KBDelayPlayers.put(player, new Tuple(null, 5));
                player.sendMessage(ChatColor.GREEN + "Set delay to 5 seconds!");
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
            }

            if (e.getCurrentItem().getType() == Material.YELLOW_CONCRETE) {
                KBDelayPlayers.put(player, new Tuple(null, 8));
                player.sendMessage(ChatColor.GREEN + "Set delay to 8 seconds!");
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
            }

            if (e.getCurrentItem().getType() == Material.GREEN_TERRACOTTA) {
                KBDelayPlayers.put(player, new Tuple(null, 10));
                player.sendMessage(ChatColor.GREEN + "Set delay to 10 seconds!");
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
            }

            if (e.getCurrentItem().getType() == Material.LIME_CONCRETE) {
                KBDelayPlayers.put(player, new Tuple(null, 12));
                player.sendMessage(ChatColor.GREEN + "Set delay to 12 seconds!");
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
            }

            if (e.getCurrentItem().getType() == Material.LIGHT_BLUE_CONCRETE) {
                KBDelayPlayers.put(player, new Tuple(null, 15));
                player.sendMessage(ChatColor.GREEN + "Set delay to 15 seconds!");
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
            }

            if (e.getCurrentItem().getType() == Material.BLUE_CONCRETE) {
                KBDelayPlayers.put(player, new Tuple(null, 20));
                player.sendMessage(ChatColor.GREEN + "Set delay to 20 seconds!");
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
            }

            if (e.getCurrentItem().getType() == Material.PURPLE_CONCRETE) {
                KBDelayPlayers.put(player, new Tuple(null, 25));
                player.sendMessage(ChatColor.GREEN + "Set delay to 25 seconds!");
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
            }

            if (e.getCurrentItem().getType() == Material.OAK_SIGN) {
                if (!choosingTime.contains(player)) {
                    player.sendMessage(ChatColor.YELLOW + "Please type the amount of seconds (between 0 and 30) in the chat within 15 seconds.");
                    choosingTime.add(player);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(JavaPlugin.getProvidingPlugin(BedwarsEvents.class), new Runnable() {
                        @Override
                        public void run() {
                            if (choosingTime.contains(player)) {
                                choosingTime.remove(player);
                                player.sendMessage(ChatColor.RED + "You did not enter a time within 15 seconds, try again!");
                            }
                        }
                    }, 300L);
                }
            }
        }
    }

    @EventHandler
    public void onChooseTime(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        if (choosingTime.contains(player)) {
            e.setCancelled(true);
            try {
                Integer timeInt = Integer.parseInt(e.getMessage());
                if (timeInt >= 0 && timeInt <= 30) {
                    // can use as normal kb stickstick, i guess?
                    KBDelayPlayers.put(player, new Tuple(null, timeInt));
                    player.sendMessage(ChatColor.GREEN + "Set delay to " + timeInt + " seconds!");
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    choosingTime.remove(player);
                }

                else {
                    player.sendMessage(ChatColor.RED + "Number out of range! You may only use numbers 0-30! Please try again!");
                    choosingTime.remove(player);
                }
            }

            catch (Exception ex) {
                player.sendMessage(ChatColor.RED + "Invalid Character! You may only use numbers 0-30! Please try again!");
                choosingTime.remove(player);
            }
        }
    }

    @EventHandler
    public static void onNoteBlockClick(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        if (e.getBlockPlaced().getType() == Material.NOTE_BLOCK) {
            e.setCancelled(true);
            TeamSelector selector = new TeamSelector();
            player.openInventory(selector.getInventory());
        }

        else {
            return;
        }
    }

    @EventHandler
    public static void onTeamSelectClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) { return; }
        if (e.getClickedInventory().getHolder() instanceof TeamSelector) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();

            if (e.getCurrentItem() == null) { return; }

            if (e.getCurrentItem().getType() == Material.RED_STAINED_GLASS_PANE) {
                if (!(Teams.red.getEntries().size() > 0)) {
                    Teams.red.addEntry(player.toString());
                    Teams.blue.removeEntry(player.toString());
                    Teams.green.removeEntry(player.toString());
                    Teams.yellow.removeEntry(player.toString());
                    Teams.aqua.removeEntry(player.toString());
                    Teams.white.removeEntry(player.toString());
                    Teams.pink.removeEntry(player.toString());
                    Teams.gray.removeEntry(player.toString());
                    player.sendMessage("§7Joined the " + "§c§lRED" + " §7team!");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You can't join that team because someone else is on it!");
                }
            }

            if (e.getCurrentItem().getType() == Material.BLUE_STAINED_GLASS_PANE) {
                if (!(Teams.blue.getEntries().size() > 0)) {
                    Teams.blue.addEntry(player.toString());
                    Teams.red.removeEntry(player.toString());
                    Teams.green.removeEntry(player.toString());
                    Teams.yellow.removeEntry(player.toString());
                    Teams.aqua.removeEntry(player.toString());
                    Teams.white.removeEntry(player.toString());
                    Teams.pink.removeEntry(player.toString());
                    Teams.gray.removeEntry(player.toString());
                    player.sendMessage("§7Joined the " + "§1§lBLUE" + " §7team!");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You can't join that team because someone else is on it!");
                }
            }

            if (e.getCurrentItem().getType() == Material.LIME_STAINED_GLASS_PANE) {
                if (!(Teams.green.getEntries().size() > 0)) {
                    Teams.green.addEntry(player.toString());
                    Teams.red.removeEntry(player.toString());
                    Teams.blue.removeEntry(player.toString());
                    Teams.yellow.removeEntry(player.toString());
                    Teams.aqua.removeEntry(player.toString());
                    Teams.white.removeEntry(player.toString());
                    Teams.pink.removeEntry(player.toString());
                    Teams.gray.removeEntry(player.toString());
                    player.sendMessage("§7Joined the " + "§a§lGREEN" + " §7team!");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You can't join that team because someone else is on it!");
                }
            }

            if (e.getCurrentItem().getType() == Material.YELLOW_STAINED_GLASS_PANE) {
                if (!(Teams.yellow.getEntries().size() > 0)) {
                    Teams.yellow.addEntry(player.toString());
                    Teams.red.removeEntry(player.toString());
                    Teams.blue.removeEntry(player.toString());
                    Teams.green.removeEntry(player.toString());
                    Teams.aqua.removeEntry(player.toString());
                    Teams.white.removeEntry(player.toString());
                    Teams.pink.removeEntry(player.toString());
                    Teams.gray.removeEntry(player.toString());
                    player.sendMessage("§7Joined the " + "§e§lYELLOW" + " §7team!");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You can't join that team because someone else is on it!");
                }
            }

            if (e.getCurrentItem().getType() == Material.LIGHT_BLUE_STAINED_GLASS_PANE) {
                if (!(Teams.aqua.getEntries().size() > 0)) {
                    Teams.aqua.addEntry(player.toString());
                    Teams.red.removeEntry(player.toString());
                    Teams.blue.removeEntry(player.toString());
                    Teams.green.removeEntry(player.toString());
                    Teams.yellow.removeEntry(player.toString());
                    Teams.white.removeEntry(player.toString());
                    Teams.pink.removeEntry(player.toString());
                    Teams.gray.removeEntry(player.toString());
                    player.sendMessage("§7Joined the " + "§b§lAQUA" + " §7team!");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You can't join that team because someone else is on it!");
                }
            }

            if (e.getCurrentItem().getType() == Material.WHITE_STAINED_GLASS_PANE) {
                if (!(Teams.white.getEntries().size() > 0)) {
                    Teams.white.addEntry(player.toString());
                    Teams.red.removeEntry(player.toString());
                    Teams.blue.removeEntry(player.toString());
                    Teams.green.removeEntry(player.toString());
                    Teams.yellow.removeEntry(player.toString());
                    Teams.aqua.removeEntry(player.toString());
                    Teams.pink.removeEntry(player.toString());
                    Teams.gray.removeEntry(player.toString());
                    player.sendMessage("§7Joined the " + "§f§lWHITE" + " §7team!");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You can't join that team because someone else is on it!");
                }
            }

            if (e.getCurrentItem().getType() == Material.PINK_STAINED_GLASS_PANE) {
                if (!(Teams.pink.getEntries().size() > 0)) {
                    Teams.pink.addEntry(player.toString());
                    Teams.red.removeEntry(player.toString());
                    Teams.blue.removeEntry(player.toString());
                    Teams.green.removeEntry(player.toString());
                    Teams.yellow.removeEntry(player.toString());
                    Teams.aqua.removeEntry(player.toString());
                    Teams.white.removeEntry(player.toString());
                    Teams.gray.removeEntry(player.toString());
                    player.sendMessage("§7Joined the " + "§d§lPINK" + " §7team!");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You can't join that team because someone else is on it!");
                }
            }

            if (e.getCurrentItem().getType() == Material.GRAY_STAINED_GLASS_PANE) {
                if (!(Teams.gray.getEntries().size() > 0)) {
                    Teams.gray.addEntry(player.toString());
                    Teams.red.removeEntry(player.toString());
                    Teams.blue.removeEntry(player.toString());
                    Teams.green.removeEntry(player.toString());
                    Teams.yellow.removeEntry(player.toString());
                    Teams.aqua.removeEntry(player.toString());
                    Teams.white.removeEntry(player.toString());
                    Teams.pink.removeEntry(player.toString());
                    player.sendMessage("§7Joined the " + "§8§lGRAY" + " §7team!");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You can't join that team because someone else is on it!");
                }
            }

        }
    }

    @EventHandler
    public void chatFormat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        if (Teams.red.getEntries().contains(player.toString())) {
            e.setFormat("§c" + player.getPlayerListName() + "§f: " + e.getMessage());
        }

        if (Teams.blue.getEntries().contains(player.toString())) {
            e.setFormat("§9" + player.getPlayerListName() + "§f: " + e.getMessage());
        }

        if (Teams.green.getEntries().contains(player.toString())) {
            e.setFormat("§a" + player.getPlayerListName() + "§f: " + e.getMessage());
        }

        if (Teams.yellow.getEntries().contains(player.toString())) {
            e.setFormat("§e" + player.getPlayerListName() + "§f: " + e.getMessage());
        }

        if (Teams.aqua.getEntries().contains(player.toString())) {
            e.setFormat("§b" + player.getPlayerListName() + "§f: " + e.getMessage());
        }

        if (Teams.white.getEntries().contains(player.toString())) {
            e.setFormat("§f" + player.getPlayerListName() + "§f: " + e.getMessage());
        }

        if (Teams.pink.getEntries().contains(player.toString())) {
            e.setFormat("§d" + player.getPlayerListName() + "§f: " + e.getMessage());
        }

        if (Teams.gray.getEntries().contains(player.toString())) {
            e.setFormat("§8" + player.getPlayerListName() + "§f: " + e.getMessage());
        }
    }

    @EventHandler
    public static void onHealthRegen(EntityRegainHealthEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();

            if (player.hasPotionEffect(PotionEffectType.REGENERATION)) {
                e.setCancelled(false);
            }

            else {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public static void onFoodLevelChange(FoodLevelChangeEvent e) {
        if (e.getEntity() instanceof Player) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public static void onBlockPlace(BlockPlaceEvent e) {
        playerPlacedBlocks.add(e.getBlockPlaced());
    }

    @EventHandler
    public static void onBlockBreak(BlockBreakEvent e) {

        /*if (e.getBlock().getType() == Material.BROWN_STAINED_GLASS) {
            e.setCancelled(true);
        } else if (e.getBlock().getType() == Material.GRAY_STAINED_GLASS) {
            e.setCancelled(true);
        } else if (e.getBlock().getType() == Material.OAK_FENCE) {
            e.setCancelled(true);
        } else if (e.getBlock().getType() == Material.OAK_STAIRS) {
            e.setCancelled(true);
        } else if (e.getBlock().getType() == Material.OAK_LOG) {
            e.setCancelled(true);
        } else if (e.getBlock().getType() == Material.OAK_SLAB) {
            e.setCancelled(true);
        } else if (e.getBlock().getType() == Material.SPRUCE_PLANKS) {
            e.setCancelled(true);
        } else if (e.getBlock().getType() == Material.SPRUCE_SLAB) {
            e.setCancelled(true);
        } else if (e.getBlock().getType() == Material.OAK_PLANKS) {
            if (e.getPlayer().getLocation().getY() > 100) {
                e.setCancelled(true);
            }
        }*/

        if (!(e.getBlock().getType() == Material.RED_BED ||
                e.getBlock().getType() == Material.BLUE_BED ||
                e.getBlock().getType() == Material.LIME_BED ||
                e.getBlock().getType() == Material.YELLOW_BED ||
                e.getBlock().getType() == Material.CYAN_BED ||
                e.getBlock().getType() == Material.WHITE_BED ||
                e.getBlock().getType() == Material.PINK_BED ||
                e.getBlock().getType() == Material.GRAY_BED)) {
            if (!playerPlacedBlocks.contains(e.getBlock())) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.RED + "You can only break blocks placed by a player!");
            } else {
                playerPlacedBlocks.remove(e.getBlock());
            }
        }
    }

    @EventHandler
    public static void onPlayerDeath(PlayerDeathEvent e) {
        Player player = (Player) e.getEntity();
        Player killer = (Player) e.getEntity().getKiller();

        killer.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§6+8 coins"));
        killer.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);

        if (picklevel.containsKey(player)) {
            if (picklevel.get(player) > 1) {
                picklevel.put(player, picklevel.get(player) - 1);
            }
        }

        if (axelevel.containsKey(player)) {
            if (axelevel.get(player) > 1) {
                axelevel.put(player, axelevel.get(player) - 1);
            }
        }

        player.getInventory().clear();

        if (Teams.red.getEntries().contains(player.toString())) {
            if (BedwarsEvents.RedCanDie == true) {
                assert player != null;

                if (e.getDeathMessage().contains("was slain by")) {
                    e.setDeathMessage(ChatColor.RED + player.getPlayerListName() + ChatColor.GRAY + " was " + ChatColor.GOLD + "stomped " + ChatColor.GRAY + "by " + ChatColor.WHITE + killer.getPlayerListName());
                }

                if (e.getDeathMessage().contains("fell from a high place")) {
                    e.setDeathMessage(ChatColor.RED + player.getPlayerListName() + ChatColor.GRAY + " hit the hard wood floor.");
                }

                Bedwars.isSpectating.put(player, true);
                player.getInventory().clear();
                gameMode = player.getGameMode();
                Bedwars.doesntNeedArmor.put(player, true);
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
                                    , 5, 10, 5);
                            seconds--;
                        } else if (seconds == 1) {
                            player.sendTitle(ChatColor.RED + "YOU DIED!", ChatColor.YELLOW + "You will respawn in "
                                            + ChatColor.RED + seconds
                                            + ChatColor.YELLOW + " second!"
                                    , 5, 10, 5);
                            seconds--;
                        } else {
                            player.setAllowFlight(false);
                            player.setGameMode(gameMode);
                            player.setHealth(20);
                            Bedwars.doesntNeedArmor.put(player, false);
                            player.removePotionEffect(PotionEffectType.INVISIBILITY);
                            player.teleport(BedwarsEvents.redteam);
                            player.getInventory().setItem(player.getInventory().firstEmpty(), new ItemStack(Material.WOODEN_SWORD, 1));
                            Bedwars.isSpectating.put(player, false);
                            this.cancel();
                        }
                    }
                }.runTaskTimer(JavaPlugin.getProvidingPlugin(Bedwars.class), 0, 20);
            }

            else {
                if (e.getDeathMessage().contains("was slain by")) {
                    player.getWorld().strikeLightning(player.getLocation());
                    e.setDeathMessage(ChatColor.RED + player.getPlayerListName() + ChatColor.GRAY + " was " + ChatColor.GOLD + "stomped " + ChatColor.GRAY + "by " + ChatColor.WHITE + killer.getPlayerListName() + ChatColor.GRAY + ". " + ChatColor.AQUA + "§lFINAL KILL!");
                }

                if (e.getDeathMessage().contains("fell from a high place")) {
                    player.getWorld().strikeLightning(player.getLocation());
                    e.setDeathMessage(ChatColor.RED + player.getPlayerListName() + ChatColor.GRAY + " hit the hard wood floor. " + ChatColor.AQUA + "§lFINAL KILL!");
                }

                player.setGameMode(GameMode.SPECTATOR);
                player.teleport(new Location(BedwarsEvents.world, 0, 105, 0));
                player.sendMessage(ChatColor.RED + "You lost the game!");
                Teams.objective.getScoreboard().resetScores("§cR " + "§fRed: " + Teams.redStr);
                Teams.redStr = "§c✗";
                Teams.objective.getScore("§cR " + "§fRed: " + Teams.redStr).setScore(10);
            }
        }










        // BLUE team
        if (Teams.blue.getEntries().contains(player.toString())) {
            if (BedwarsEvents.BlueCanDie == true) {
                assert player != null;

                if (e.getDeathMessage().contains("was slain by")) {
                    player.getWorld().strikeLightning(player.getLocation());
                    e.setDeathMessage(ChatColor.BLUE + player.getPlayerListName() + ChatColor.GRAY + " was " + ChatColor.GOLD + "stomped " + ChatColor.GRAY + "by " + ChatColor.WHITE + killer.getPlayerListName());
                }

                if (e.getDeathMessage().contains("fell from a high place")) {
                    player.getWorld().strikeLightning(player.getLocation());
                    e.setDeathMessage(ChatColor.BLUE + player.getPlayerListName() + ChatColor.GRAY + " hit the hard wood floor.");
                }

                Bedwars.isSpectating.put(player, true);
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
                                    , 5, 10, 5);
                            seconds--;
                        } else if (seconds == 1) {
                            player.sendTitle(ChatColor.RED + "YOU DIED!", ChatColor.YELLOW + "You will respawn in "
                                            + ChatColor.RED + seconds
                                            + ChatColor.YELLOW + " second!"
                                    , 5, 10, 5);
                            seconds--;
                        } else {
                            player.setAllowFlight(false);
                            player.setGameMode(gameMode);
                            player.setHealth(20);
                            player.removePotionEffect(PotionEffectType.INVISIBILITY);
                            player.teleport(BedwarsEvents.blueteam);
                            player.getInventory().setItem(player.getInventory().firstEmpty(), new ItemStack(Material.WOODEN_SWORD, 1));
                            Bedwars.isSpectating.put(player, false);
                            this.cancel();
                        }
                    }
                }.runTaskTimer(JavaPlugin.getProvidingPlugin(Bedwars.class), 0, 20);
            }

            else {
                if (e.getDeathMessage().contains("was slain by")) {
                    player.getWorld().strikeLightning(player.getLocation());
                    e.setDeathMessage(ChatColor.BLUE + player.getPlayerListName() + ChatColor.GRAY + " was " + ChatColor.GOLD + "stomped " + ChatColor.GRAY + "by " + ChatColor.WHITE + killer.getPlayerListName() + ChatColor.GRAY + ". " + ChatColor.AQUA + "§lFINAL KILL!");
                }

                if (e.getDeathMessage().contains("fell from a high place")) {
                    player.getWorld().strikeLightning(player.getLocation());
                    e.setDeathMessage(ChatColor.BLUE + player.getPlayerListName() + ChatColor.GRAY + " hit the hard wood floor. " + ChatColor.AQUA + "§lFINAL KILL!");
                }
                player.setGameMode(GameMode.SPECTATOR);
                player.teleport(new Location(BedwarsEvents.world, 0, 105, 0));
                player.sendMessage(ChatColor.RED + "You lost the game!");
                Teams.objective.getScoreboard().resetScores("§9B " + "§fBlue: " + Teams.blueStr);
                Teams.blueStr = "§c✗";
                Teams.objective.getScore("§9B " + "§fBlue: " + Teams.blueStr).setScore(9);
            }
        }










        // GREEN team
        if (Teams.green.getEntries().contains(player.toString())) {
            if (BedwarsEvents.GreenCanDie == true) {
                assert player != null;

                if (e.getDeathMessage().contains("was slain by")) {
                    e.setDeathMessage(ChatColor.GREEN + player.getPlayerListName() + ChatColor.GRAY + " was " + ChatColor.GOLD + "stomped " + ChatColor.GRAY + "by " + ChatColor.WHITE + killer.getPlayerListName());
                }

                if (e.getDeathMessage().contains("fell from a high place")) {
                    e.setDeathMessage(ChatColor.GREEN + player.getPlayerListName() + ChatColor.GRAY + " hit the hard wood floor.");
                }


                Bedwars.isSpectating.put(player, true);
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
                                    , 5, 10, 5);
                            seconds--;
                        } else if (seconds == 1) {
                            player.sendTitle(ChatColor.RED + "YOU DIED!", ChatColor.YELLOW + "You will respawn in "
                                            + ChatColor.RED + seconds
                                            + ChatColor.YELLOW + " second!"
                                    , 5, 10, 5);
                            seconds--;
                        } else {
                            player.setAllowFlight(false);
                            player.setGameMode(gameMode);
                            player.setHealth(20);
                            player.removePotionEffect(PotionEffectType.INVISIBILITY);
                            player.teleport(BedwarsEvents.greenteam);
                            player.getInventory().setItem(player.getInventory().firstEmpty(), new ItemStack(Material.WOODEN_SWORD, 1));
                            Bedwars.isSpectating.put(player, false);
                            this.cancel();
                        }
                    }
                }.runTaskTimer(JavaPlugin.getProvidingPlugin(Bedwars.class), 0, 20);
            }

            else {
                if (e.getDeathMessage().contains("was slain by")) {
                    player.getWorld().strikeLightning(player.getLocation());
                    e.setDeathMessage(ChatColor.GREEN + player.getPlayerListName() + ChatColor.GRAY + " was " + ChatColor.GOLD + "stomped " + ChatColor.GRAY + "by " + ChatColor.WHITE + killer.getPlayerListName() + ChatColor.GRAY + ". " + ChatColor.AQUA + "§lFINAL KILL!");
                }

                if (e.getDeathMessage().contains("fell from a high place")) {
                    player.getWorld().strikeLightning(player.getLocation());
                    e.setDeathMessage(ChatColor.GREEN + player.getPlayerListName() + ChatColor.GRAY + " hit the hard wood floor. " + ChatColor.AQUA + "§lFINAL KILL!");
                }
                player.setGameMode(GameMode.SPECTATOR);
                player.teleport(new Location(BedwarsEvents.world, 0, 105, 0));
                player.sendMessage(ChatColor.RED + "You lost the game!");
                Teams.objective.getScoreboard().resetScores("§aG " + "§fGreen: " + Teams.green);
                Teams.greenStr = "§c✗";
                Teams.objective.getScore("§cG " + "§fGreen: " + Teams.greenStr).setScore(8);
            }
        }










        // YELLOW team
        if (Teams.yellow.getEntries().contains(player.toString())) {
            if (BedwarsEvents.YellowCanDie == true) {
                assert player != null;

                if (e.getDeathMessage().contains("was slain by")) {
                    e.setDeathMessage(ChatColor.YELLOW + player.getPlayerListName() + ChatColor.GRAY + " was " + ChatColor.GOLD + "stomped " + ChatColor.GRAY + "by " + ChatColor.WHITE + killer.getPlayerListName());
                }

                if (e.getDeathMessage().contains("fell from a high place")) {
                    e.setDeathMessage(ChatColor.YELLOW + player.getPlayerListName() + ChatColor.GRAY + " hit the hard wood floor.");
                }

                Bedwars.isSpectating.put(player, true);
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
                                    , 5, 10, 5);
                            seconds--;
                        } else if (seconds == 1) {
                            player.sendTitle(ChatColor.RED + "YOU DIED!", ChatColor.YELLOW + "You will respawn in "
                                            + ChatColor.RED + seconds
                                            + ChatColor.YELLOW + " second!"
                                    , 5, 10, 5);
                            seconds--;
                        } else {
                            player.setAllowFlight(false);
                            player.setGameMode(gameMode);
                            player.setHealth(20);
                            player.removePotionEffect(PotionEffectType.INVISIBILITY);
                            player.teleport(BedwarsEvents.yellowteam);
                            player.getInventory().setItem(player.getInventory().firstEmpty(), new ItemStack(Material.WOODEN_SWORD, 1));
                            Bedwars.isSpectating.put(player, false);
                            this.cancel();
                        }
                    }
                }.runTaskTimer(JavaPlugin.getProvidingPlugin(Bedwars.class), 0, 20);
            }

            else {
                if (e.getDeathMessage().contains("was slain by")) {
                    player.getWorld().strikeLightning(player.getLocation());
                    e.setDeathMessage(ChatColor.YELLOW + player.getPlayerListName() + ChatColor.GRAY + " was " + ChatColor.GOLD + "stomped " + ChatColor.GRAY + "by " + ChatColor.WHITE + killer.getPlayerListName() + ChatColor.GRAY + ". " + ChatColor.AQUA + "§lFINAL KILL!");
                }

                if (e.getDeathMessage().contains("fell from a high place")) {
                    player.getWorld().strikeLightning(player.getLocation());
                    e.setDeathMessage(ChatColor.YELLOW + player.getPlayerListName() + ChatColor.GRAY + " hit the hard wood floor. " + ChatColor.AQUA + "§lFINAL KILL!");
                }
                player.setGameMode(GameMode.SPECTATOR);
                player.teleport(new Location(BedwarsEvents.world, 0, 105, 0));
                player.sendMessage(ChatColor.RED + "You lost the game!");
                Teams.objective.getScoreboard().resetScores("§eY " + "§fYellow: " + Teams.yellowStr);
                Teams.yellowStr = "§c✗";
                Teams.objective.getScore("§9B " + "§fBlue: " + Teams.yellowStr).setScore(7);
            }
        }











        // AQUA team
        if (Teams.aqua.getEntries().contains(player.toString())) {
            if (BedwarsEvents.AquaCanDie == true) {
                assert player != null;

                if (e.getDeathMessage().contains("was slain by")) {
                    e.setDeathMessage(ChatColor.AQUA + player.getPlayerListName() + ChatColor.GRAY + " was " + ChatColor.GOLD + "stomped " + ChatColor.GRAY + "by " + ChatColor.WHITE + killer.getPlayerListName());
                }

                if (e.getDeathMessage().contains("fell from a high place")) {
                    e.setDeathMessage(ChatColor.AQUA + player.getPlayerListName() + ChatColor.GRAY + " hit the hard wood floor.");
                }

                Bedwars.isSpectating.put(player, true);
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
                                    , 5, 10, 5);
                            seconds--;
                        } else if (seconds == 1) {
                            player.sendTitle(ChatColor.RED + "YOU DIED!", ChatColor.YELLOW + "You will respawn in "
                                            + ChatColor.RED + seconds
                                            + ChatColor.YELLOW + " second!"
                                    , 5, 10, 5);
                            seconds--;
                        } else {
                            player.setAllowFlight(false);
                            player.setGameMode(gameMode);
                            player.setHealth(20);
                            player.removePotionEffect(PotionEffectType.INVISIBILITY);
                            player.teleport(BedwarsEvents.aquateam);
                            player.getInventory().setItem(player.getInventory().firstEmpty(), new ItemStack(Material.WOODEN_SWORD, 1));
                            Bedwars.isSpectating.put(player, false);
                            this.cancel();
                        }
                    }
                }.runTaskTimer(JavaPlugin.getProvidingPlugin(Bedwars.class), 0, 20);
            }

            else {
                if (e.getDeathMessage().contains("was slain by")) {
                    player.getWorld().strikeLightning(player.getLocation());
                    e.setDeathMessage(ChatColor.AQUA + player.getPlayerListName() + ChatColor.GRAY + " was " + ChatColor.GOLD + "stomped " + ChatColor.GRAY + "by " + ChatColor.WHITE + killer.getPlayerListName() + ChatColor.GRAY + ". " + ChatColor.AQUA + "§lFINAL KILL!");
                }

                if (e.getDeathMessage().contains("fell from a high place")) {
                    player.getWorld().strikeLightning(player.getLocation());
                    e.setDeathMessage(ChatColor.AQUA + player.getPlayerListName() + ChatColor.GRAY + " hit the hard wood floor. " + ChatColor.AQUA + "§lFINAL KILL!");
                }
                player.setGameMode(GameMode.SPECTATOR);
                player.teleport(new Location(BedwarsEvents.world, 0, 105, 0));
                player.sendMessage(ChatColor.RED + "You lost the game!");
                Teams.objective.getScoreboard().resetScores("§bA " + "§fAqua: " + Teams.aquaStr);
                Teams.blueStr = "§c✗";
                Teams.objective.getScore("§bA " + "§fAqua: " + Teams.aquaStr).setScore(6);
            }
        }











        // WHITE team
        if (Teams.white.getEntries().contains(player.toString())) {
            if (BedwarsEvents.WhiteCanDie == true) {
                assert player != null;

                if (e.getDeathMessage().contains("was slain by")) {
                    e.setDeathMessage(ChatColor.WHITE + player.getPlayerListName() + ChatColor.GRAY + " was " + ChatColor.GOLD + "stomped " + ChatColor.GRAY + "by " + ChatColor.WHITE + killer.getPlayerListName());
                }

                if (e.getDeathMessage().contains("fell from a high place")) {
                    e.setDeathMessage(ChatColor.WHITE + player.getPlayerListName() + ChatColor.GRAY + " hit the hard wood floor.");
                }

                Bedwars.isSpectating.put(player, true);
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
                                    , 5, 10, 5);
                            seconds--;
                        } else if (seconds == 1) {
                            player.sendTitle(ChatColor.RED + "YOU DIED!", ChatColor.YELLOW + "You will respawn in "
                                            + ChatColor.RED + seconds
                                            + ChatColor.YELLOW + " second!"
                                    , 5, 10, 5);
                            seconds--;
                        } else {
                            player.setAllowFlight(false);
                            player.setGameMode(gameMode);
                            player.setHealth(20);
                            player.removePotionEffect(PotionEffectType.INVISIBILITY);
                            player.teleport(BedwarsEvents.whiteteam);
                            player.getInventory().setItem(player.getInventory().firstEmpty(), new ItemStack(Material.WOODEN_SWORD, 1));
                            Bedwars.isSpectating.put(player, false);
                            this.cancel();
                        }
                    }
                }.runTaskTimer(JavaPlugin.getProvidingPlugin(Bedwars.class), 0, 20);
            }

            else {
                if (e.getDeathMessage().contains("was slain by")) {
                    player.getWorld().strikeLightning(player.getLocation());
                    e.setDeathMessage(ChatColor.WHITE + player.getPlayerListName() + ChatColor.GRAY + " was " + ChatColor.GOLD + "stomped " + ChatColor.GRAY + "by " + ChatColor.WHITE + killer.getPlayerListName() + ChatColor.GRAY + ". " + ChatColor.AQUA + "§lFINAL KILL!");
                }

                if (e.getDeathMessage().contains("fell from a high place")) {
                    player.getWorld().strikeLightning(player.getLocation());
                    e.setDeathMessage(ChatColor.WHITE + player.getPlayerListName() + ChatColor.GRAY + " hit the hard wood floor. " + ChatColor.AQUA + "§lFINAL KILL!");
                }
                player.setGameMode(GameMode.SPECTATOR);
                player.teleport(new Location(BedwarsEvents.world, 0, 105, 0));
                player.sendMessage(ChatColor.RED + "You lost the game!");
                Teams.objective.getScoreboard().resetScores("§fW " + "§fWhite: " + Teams.whiteStr);
                Teams.whiteStr = "§c✗";
                Teams.objective.getScore("§fW " + "§fWhite: " + Teams.whiteStr).setScore(5);
            }
        }










        // PINK team
        if (Teams.pink.getEntries().contains(player.toString())) {
            if (BedwarsEvents.PinkCanDie == true) {
                assert player != null;

                if (e.getDeathMessage().contains("was slain by")) {
                    e.setDeathMessage(ChatColor.LIGHT_PURPLE + player.getPlayerListName() + ChatColor.GRAY + " was " + ChatColor.GOLD + "stomped " + ChatColor.GRAY + "by " + ChatColor.WHITE + killer.getPlayerListName());
                }

                if (e.getDeathMessage().contains("fell from a high place")) {
                    e.setDeathMessage(ChatColor.LIGHT_PURPLE + player.getPlayerListName() + ChatColor.GRAY + " hit the hard wood floor.");
                }

                Bedwars.isSpectating.put(player, true);
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
                                    , 5, 10, 5);
                            seconds--;
                        } else if (seconds == 1) {
                            player.sendTitle(ChatColor.RED + "YOU DIED!", ChatColor.YELLOW + "You will respawn in "
                                            + ChatColor.RED + seconds
                                            + ChatColor.YELLOW + " second!"
                                    , 5, 10, 5);
                            seconds--;
                        } else {
                            player.setAllowFlight(false);
                            player.setGameMode(gameMode);
                            player.setHealth(20);
                            player.removePotionEffect(PotionEffectType.INVISIBILITY);
                            player.teleport(BedwarsEvents.pinkteam);
                            player.getInventory().setItem(player.getInventory().firstEmpty(), new ItemStack(Material.WOODEN_SWORD, 1));
                            Bedwars.isSpectating.put(player, false);
                            this.cancel();
                        }
                    }
                }.runTaskTimer(JavaPlugin.getProvidingPlugin(Bedwars.class), 0, 20);
            }

            else {
                if (e.getDeathMessage().contains("was slain by")) {
                    player.getWorld().strikeLightning(player.getLocation());
                    e.setDeathMessage(ChatColor.LIGHT_PURPLE + player.getPlayerListName() + ChatColor.GRAY + " was " + ChatColor.GOLD + "stomped " + ChatColor.GRAY + "by " + ChatColor.WHITE + killer.getPlayerListName() + ChatColor.GRAY + ". " + ChatColor.AQUA + "§lFINAL KILL!");
                }

                if (e.getDeathMessage().contains("fell from a high place")) {
                    player.getWorld().strikeLightning(player.getLocation());
                    e.setDeathMessage(ChatColor.LIGHT_PURPLE + player.getPlayerListName() + ChatColor.GRAY + " hit the hard wood floor. " + ChatColor.AQUA + "§lFINAL KILL!");
                }
                player.setGameMode(GameMode.SPECTATOR);
                player.teleport(new Location(BedwarsEvents.world, 0, 105, 0));
                player.sendMessage(ChatColor.RED + "You lost the game!");
                Teams.objective.getScoreboard().resetScores("§dP " + "§fPink: " + Teams.pinkStr);
                Teams.pinkStr = "§c✗";
                Teams.objective.getScore("§dP " + "§fPink: " + Teams.pinkStr).setScore(4);
            }
        }










        // GRAY team
        if (Teams.gray.getEntries().contains(player.toString())) {
            if (BedwarsEvents.GrayCanDie == true) {
                assert player != null;

                if (e.getDeathMessage().contains("was slain by")) {
                    e.setDeathMessage(ChatColor.DARK_GRAY + player.getPlayerListName() + ChatColor.GRAY + " was " + ChatColor.GOLD + "stomped " + ChatColor.GRAY + "by " + ChatColor.WHITE + killer.getPlayerListName());
                }

                if (e.getDeathMessage().contains("fell from a high place")) {
                    e.setDeathMessage(ChatColor.DARK_GRAY + player.getPlayerListName() + ChatColor.GRAY + " hit the hard wood floor.");
                }

                Bedwars.isSpectating.put(player, true);
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
                                    , 5, 10, 5);
                            seconds--;
                        } else if (seconds == 1) {
                            player.sendTitle(ChatColor.RED + "YOU DIED!", ChatColor.YELLOW + "You will respawn in "
                                            + ChatColor.RED + seconds
                                            + ChatColor.YELLOW + " second!"
                                    , 5, 10, 5);
                            seconds--;
                        } else {
                            player.setAllowFlight(false);
                            player.setGameMode(gameMode);
                            player.setHealth(20);
                            player.removePotionEffect(PotionEffectType.INVISIBILITY);
                            player.teleport(BedwarsEvents.grayteam);
                            player.getInventory().setItem(player.getInventory().firstEmpty(), new ItemStack(Material.WOODEN_SWORD, 1));
                            Bedwars.isSpectating.put(player, false);
                            this.cancel();
                        }
                    }
                }.runTaskTimer(JavaPlugin.getProvidingPlugin(Bedwars.class), 0, 20);
            }

            else {
                if (e.getDeathMessage().contains("was slain by")) {
                    player.getWorld().strikeLightning(player.getLocation());
                    e.setDeathMessage(ChatColor.DARK_GRAY + player.getPlayerListName() + ChatColor.GRAY + " was " + ChatColor.GOLD + "stomped " + ChatColor.GRAY + "by " + ChatColor.WHITE + killer.getPlayerListName() + ChatColor.GRAY + ". " + ChatColor.AQUA + "§lFINAL KILL!");
                }

                if (e.getDeathMessage().contains("fell from a high place")) {
                    player.getWorld().strikeLightning(player.getLocation());
                    e.setDeathMessage(ChatColor.DARK_GRAY + player.getPlayerListName() + ChatColor.GRAY + " hit the hard wood floor. " + ChatColor.AQUA + "§lFINAL KILL!");
                }
                player.setGameMode(GameMode.SPECTATOR);
                player.teleport(new Location(BedwarsEvents.world, 0, 105, 0));
                player.sendMessage(ChatColor.RED + "You lost the game!");
                Teams.objective.getScoreboard().resetScores("§8S " + "§fGray: " + Teams.grayStr);
                Teams.grayStr = "§c✗";
                Teams.objective.getScore("§8S " + "§fGray: " + Teams.grayStr).setScore(3);
            }
        }
    }

    @EventHandler
    public static void pickupSword(EntityPickupItemEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (e.getItem().getItemStack().getType() == Material.WOODEN_SWORD) {
                e.setCancelled(true);
                e.getItem().remove();
            }

            if (e.getItem().getItemStack().getType() == Material.STONE_SWORD) {
                if (player.getInventory().contains(Material.WOODEN_SWORD)) {
                    player.getInventory().removeItem(new ItemStack(Material.WOODEN_SWORD));
                }
            }

            if (e.getItem().getItemStack().getType() == Material.SHEARS) {
                e.setCancelled(true);
                e.getItem().remove();
            }

            if (e.getItem().getItemStack().getType() == Material.WOODEN_PICKAXE) {
                e.setCancelled(true);
                e.getItem().remove();
            }

            if (e.getItem().getItemStack().getType() == Material.WOODEN_AXE) {
                e.setCancelled(true);
                e.getItem().remove();
            }

            if (e.getItem().getItemStack().getType() == Material.WOODEN_SWORD) {
                e.setCancelled(true);
                e.getItem().remove();
            }
        }

    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (cantakedamage == false) {
                e.setCancelled(true);
            }
        }
    }



    @EventHandler
    public void onBlocksClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) { return; }
        if (e.getClickedInventory().getHolder() instanceof Blocks) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();

            if (e.getCurrentItem() == null) {
                return;
            }

            if (e.getCurrentItem().getType() == Material.NETHER_STAR) {
                player.openInventory(new QuickBuy().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.GOLDEN_SWORD) {
                player.openInventory(new Melee().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.CHAINMAIL_BOOTS) {
                player.openInventory(new Armor().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.WOODEN_PICKAXE) {
                player.openInventory(new Tools().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.BOW) {
                player.openInventory(new Ranged().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.BREWING_STAND) {
                player.openInventory(new Potions().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.TNT) {
                player.openInventory(new Utilities().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.WHITE_WOOL) {
                if (player.getInventory().contains(Material.IRON_INGOT, 4)) {
                    for (int i = 0; i < 4; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT));
                    }

                    if (Teams.red.getEntries().contains(player.toString())) {
                        player.getInventory().addItem(new ItemStack(Material.RED_WOOL, 16));
                    }

                    if (Teams.blue.getEntries().contains(player.toString())) {
                        player.getInventory().addItem(new ItemStack(Material.BLUE_WOOL, 16));
                    }

                    if (Teams.green.getEntries().contains(player.toString())) {
                        player.getInventory().addItem(new ItemStack(Material.LIME_WOOL, 16));
                    }

                    if (Teams.yellow.getEntries().contains(player.toString())) {
                        player.getInventory().addItem(new ItemStack(Material.YELLOW_WOOL, 16));
                    }

                    if (Teams.aqua.getEntries().contains(player.toString())) {
                        player.getInventory().addItem(new ItemStack(Material.CYAN_WOOL, 16));
                    }

                    if (Teams.white.getEntries().contains(player.toString())) {
                        player.getInventory().addItem(new ItemStack(Material.WHITE_WOOL, 16));
                    }

                    if (Teams.pink.getEntries().contains(player.toString())) {
                        player.getInventory().addItem(new ItemStack(Material.PINK_WOOL, 16));
                    }

                    if (Teams.gray.getEntries().contains(player.toString())) {
                        player.getInventory().addItem(new ItemStack(Material.GRAY_WOOL, 16));
                    }

                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Wool");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Iron!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.TERRACOTTA && e.getSlot() == 20) {
                if (player.getInventory().contains(Material.IRON_INGOT, 12)) {
                    for (int i = 0; i < 12; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT));
                    }
                    player.getInventory().addItem(new ItemStack(Material.TERRACOTTA, 12));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Hardened Clay");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Iron!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.GLASS) {
                if (player.getInventory().contains(Material.IRON_INGOT, 12)) {
                    for (int i = 0; i < 12; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT));
                    }
                    player.getInventory().addItem(new ItemStack(Material.GLASS, 4));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Blast-Proof Glass");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Iron!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.END_STONE) {
                if (player.getInventory().contains(Material.IRON_INGOT, 24)) {
                    for (int i = 0; i < 24; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT));
                    }
                    player.getInventory().addItem(new ItemStack(Material.END_STONE, 12));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " End Stone");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Iron!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.LADDER) {
                if (player.getInventory().contains(Material.IRON_INGOT, 4)) {
                    for (int i = 0; i < 4; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT));
                    }
                    player.getInventory().addItem(new ItemStack(Material.LADDER, 16));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Ladder");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Iron!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.OAK_PLANKS) {
                if (player.getInventory().contains(Material.GOLD_INGOT, 4)) {
                    for (int i = 0; i < 4; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT));
                    }
                    player.getInventory().addItem(new ItemStack(Material.OAK_PLANKS, 16));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Oak Wood Planks");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Gold!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.OBSIDIAN) {
                if (player.getInventory().contains(Material.EMERALD, 4)) {
                    for (int i = 0; i < 4; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.EMERALD));
                    }
                    player.getInventory().addItem(new ItemStack(Material.OBSIDIAN, 4));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Obsidian");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Emeralds!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }
        }
    }

    @EventHandler
    public void onMeleeClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) { return; }
        if (e.getClickedInventory().getHolder() instanceof Melee) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();

            if (e.getCurrentItem() == null) {
                return;
            }

            if (e.getCurrentItem().getType() == Material.NETHER_STAR) {
                player.openInventory(new QuickBuy().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.TERRACOTTA) {
                player.openInventory(new Blocks().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.CHAINMAIL_BOOTS) {
                player.openInventory(new Armor().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.WOODEN_PICKAXE) {
                player.openInventory(new Tools().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.BOW) {
                player.openInventory(new Ranged().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.BREWING_STAND) {
                player.openInventory(new Potions().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.TNT) {
                player.openInventory(new Utilities().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.STONE_SWORD) {
                if (player.getInventory().contains(Material.IRON_INGOT, 10)) {
                    for (int i = 0; i < 10; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT));
                    }
                    player.getInventory().addItem(new ItemStack(Material.STONE_SWORD));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Stone Sword");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Iron!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.IRON_SWORD) {
                if (player.getInventory().contains(Material.GOLD_INGOT, 7)) {
                    for (int i = 0; i < 7; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT));
                    }
                    player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Iron Sword");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Gold!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.DIAMOND_SWORD) {
                if (player.getInventory().contains(Material.EMERALD, 4)) {
                    for (int i = 0; i < 4; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.EMERALD));
                    }
                    player.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Diamond Sword");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Emeralds!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.STICK && e.getSlot() == 22) {
                if (player.getInventory().contains(Material.GOLD_INGOT, 5)) {
                    for (int i = 0; i < 5; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT));
                    }
                    player.getInventory().addItem(kbstick);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Stick (Knockback I)");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Gold!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.BLAZE_ROD && e.getSlot() == 23) {
                player.getInventory().addItem(delayedKBStick);
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                player.sendMessage(ChatColor.GREEN + "You purchased §e§kw §r§eDelayed Knockback Stick §kw§r");
            }
        }
    }

    @EventHandler
    public void onArmorClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) { return; }
        if (e.getClickedInventory().getHolder() instanceof Armor) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();

            if (e.getCurrentItem() == null) {
                return;
            }

            if (e.getCurrentItem().getType() == Material.NETHER_STAR) {
                player.openInventory(new QuickBuy().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.TERRACOTTA) {
                player.openInventory(new Blocks().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.GOLDEN_SWORD) {
                player.openInventory(new Melee().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.WOODEN_PICKAXE) {
                player.openInventory(new Tools().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.BOW) {
                player.openInventory(new Ranged().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.BREWING_STAND) {
                player.openInventory(new Potions().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.TNT) {
                player.openInventory(new Utilities().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.CHAINMAIL_BOOTS && e.getSlot() == 19) {
                if (player.getInventory().contains(Material.IRON_INGOT, 40)) {
                    if (player.getInventory().getLeggings().getType() == Material.CHAINMAIL_LEGGINGS || player.getInventory().getLeggings().getType() == Material.IRON_LEGGINGS || player.getInventory().getLeggings().getType() == Material.DIAMOND_LEGGINGS) {
                        player.sendMessage(ChatColor.RED + "You already have this item or something better!");
                    }
                    else {
                        for (int i = 0; i < 40; i++) {
                            player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT));
                        }
                        hasBetterArmor.put(player, true);
                        player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                        player.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                        player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Permanent Chainmail Armor");
                    }
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Iron!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.IRON_BOOTS) {
                if (player.getInventory().contains(Material.GOLD_INGOT, 12)) {
                    if (player.getInventory().getLeggings().getType() == Material.IRON_LEGGINGS || player.getInventory().getLeggings().getType() == Material.DIAMOND_LEGGINGS) {
                        player.sendMessage(ChatColor.RED + "You already have this item or something better!");
                    }
                    else {
                        for (int i = 0; i < 12; i++) {
                            player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT));
                        }

                        hasBetterArmor.put(player, false);
                        hasIronArmor.put(player, true);

                        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                        player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Permanent Iron Armor");
                    }
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Gold!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.DIAMOND_BOOTS) {
                if (player.getInventory().contains(Material.EMERALD, 6)) {
                    for (int i=0; i<6; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.EMERALD));
                    }
                    hasBetterArmor.put(player, false);
                    hasIronArmor.put(player, false);
                    hasDiamondArmor.put(player, true);
                    player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                    player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Permanent Diamond Armor");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Emeralds!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }
        }
    }

    @EventHandler
    public void onToolsClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) { return; }
        if (e.getClickedInventory().getHolder() instanceof Tools) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();

            if (e.getCurrentItem() == null) {
                return;
            }

            if (e.getCurrentItem().getType() == Material.NETHER_STAR) {
                player.openInventory(new QuickBuy().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.TERRACOTTA) {
                player.openInventory(new Blocks().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.GOLDEN_SWORD) {
                player.openInventory(new Melee().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.CHAINMAIL_BOOTS) {
                player.openInventory(new Armor().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.BOW) {
                player.openInventory(new Ranged().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.BREWING_STAND) {
                player.openInventory(new Potions().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.TNT) {
                player.openInventory(new Utilities().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.WOODEN_PICKAXE && e.getSlot() == 20) {
                if (player.getInventory().contains(Material.IRON_INGOT, 10)) {
                    for (int i = 0; i < 10; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT));
                    }
                    picklevel.put(player, 1);
                    player.getInventory().addItem(Bedwars.woodPickaxe);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Wooden Pickaxe (Efficiency I)");
                    player.openInventory(new Tools().getInventory());
                } else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Iron!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            } else if (e.getCurrentItem().getType() == Material.IRON_PICKAXE && e.getSlot() == 20) {
                if (player.getInventory().contains(Material.IRON_INGOT, 10)) {
                    for (int i = 0; i < 10; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT));
                    }
                    picklevel.put(player, 2);
                    player.getInventory().removeItem(new ItemStack[]{Bedwars.woodPickaxe});
                    player.getInventory().setItem(player.getInventory().firstEmpty(), Bedwars.ironPickaxe);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Iron Pickaxe (Efficiency II)");
                    player.openInventory(new Tools().getInventory());
                } else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Iron!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            } else if (e.getCurrentItem().getType() == Material.GOLDEN_PICKAXE && e.getSlot() == 20) {
                if (player.getInventory().contains(Material.GOLD_INGOT, 3)) {
                    for (int i = 0; i < 3; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT));
                    }
                    picklevel.put(player, 3);
                    player.getInventory().removeItem(new ItemStack[]{Bedwars.ironPickaxe});
                    player.getInventory().setItem(player.getInventory().firstEmpty(), Bedwars.goldPickaxe);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Gold Pickaxe (Efficiency III)");
                    player.openInventory(new Tools().getInventory());
                } else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Gold!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            } else if (e.getCurrentItem().getType() == Material.DIAMOND_PICKAXE && e.getSlot() == 20 && picklevel.get(player) == 3) {
                if (player.getInventory().contains(Material.GOLD_INGOT, 6)) {
                    for (int i = 0; i < 6; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT));
                    }
                    picklevel.put(player, 4);
                    player.getInventory().removeItem(new ItemStack[]{Bedwars.goldPickaxe});
                    player.getInventory().setItem(player.getInventory().firstEmpty(), Bedwars.diamondPickaxe);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Diamond Pickaxe (Efficiency III)");
                    player.openInventory(new Tools().getInventory());
                } else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Gold!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            } else if (picklevel.get(player) == 4 && e.getSlot() == 20) {
                player.sendMessage(ChatColor.RED + "You already have this item!");
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
            }

            if (e.getCurrentItem().getType() == Material.WOODEN_AXE) {
                if (player.getInventory().contains(Material.IRON_INGOT, 10)) {
                    for (int i = 0; i < 10; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT));
                    }
                    axelevel.put(player, 1);
                    player.getInventory().addItem(Bedwars.woodAxe);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Wooden Axe (Efficiency I)");
                    player.openInventory(new Tools().getInventory());
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Iron!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            else if (e.getCurrentItem().getType() == Material.STONE_AXE) {
                if (player.getInventory().contains(Material.IRON_INGOT, 10)) {
                    for (int i = 0; i < 10; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT));
                    }
                    axelevel.put(player, 2);
                    player.getInventory().removeItem(new ItemStack[]{Bedwars.woodAxe});
                    player.getInventory().addItem(Bedwars.stoneAxe);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Stone Axe (Efficiency I)");
                    player.openInventory(new Tools().getInventory());
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Iron!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            else if (e.getCurrentItem().getType() == Material.IRON_AXE) {
                if (player.getInventory().contains(Material.GOLD_INGOT, 3)) {
                    for (int i = 0; i < 3; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT));
                    }
                    axelevel.put(player, 3);
                    player.getInventory().removeItem(new ItemStack[]{Bedwars.stoneAxe});
                    player.getInventory().addItem(Bedwars.ironAxe);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Iron Axe (Efficiency II)");
                    player.openInventory(new Tools().getInventory());
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Gold!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            else if (e.getCurrentItem().getType() == Material.DIAMOND_AXE) {
                if (player.getInventory().contains(Material.GOLD_INGOT, 6)) {
                    for (int i = 0; i < 6; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT));
                    }
                    axelevel.put(player, 4);
                    player.getInventory().removeItem(new ItemStack[]{Bedwars.ironAxe});
                    player.getInventory().addItem(Bedwars.diamondAxe);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Diamond Axe (Efficiency III)");
                    player.openInventory(new Tools().getInventory());
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Gold!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            else if (BedwarsEvents.picklevel.get(player) == 4 && e.getSlot() == 21) {
                player.sendMessage(ChatColor.RED + "You already have this item!");
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
            }

            if (e.getCurrentItem().getType() == Material.SHEARS) {
                if (shears.get(player) == false) {
                    if (player.getInventory().contains(Material.IRON_INGOT, 20)) {
                        for (int i = 0; i < 20; i++) {
                            player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT));
                        }
                        shears.put(player, true);
                        player.getInventory().addItem(new ItemStack(Material.SHEARS));
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                        player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Permanent Shears");
                    } else {
                        player.sendMessage(ChatColor.RED + "You don't have enough Iron!");
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                    }
                }

                else {
                    player.sendMessage(ChatColor.RED + "You already have this item!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }
        }
    }

    @EventHandler
    public void onRangedClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) { return; }
        if (e.getClickedInventory().getHolder() instanceof Ranged) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();

            if (e.getCurrentItem() == null) {
                return;
            }

            if (e.getCurrentItem().getType() == Material.NETHER_STAR) {
                player.openInventory(new QuickBuy().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.TERRACOTTA) {
                player.openInventory(new Blocks().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.GOLDEN_SWORD) {
                player.openInventory(new Melee().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.CHAINMAIL_BOOTS) {
                player.openInventory(new Armor().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.WOODEN_PICKAXE) {
                player.openInventory(new Tools().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.BREWING_STAND) {
                player.openInventory(new Potions().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.TNT) {
                player.openInventory(new Utilities().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.ARROW) {
                if (player.getInventory().contains(Material.GOLD_INGOT, 2)) {
                    for (int i = 0; i < 2; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT));
                    }
                    player.getInventory().addItem(new ItemStack(Material.ARROW, 8));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Arrow");
                }
                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Gold!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.BOW && e.getSlot() == 20) {
                if (player.getInventory().contains(Material.GOLD_INGOT, 12)) {
                    for (int i = 0; i < 12; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT));
                    }
                    player.getInventory().addItem(new ItemStack(Material.BOW));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Bow");
                }
                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Gold!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.BOW && e.getSlot() == 21) {
                if (player.getInventory().contains(Material.GOLD_INGOT, 24)) {
                    for (int i = 0; i < 24; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT));
                    }
                    ItemStack invPowerBow = CreateItem.createItemEnchanted("§fBow", Material.BOW, Collections.singletonList("§9Power I"), 1, true, true, Collections.singletonList(Enchantment.ARROW_DAMAGE), 1);
                    player.getInventory().addItem(invPowerBow);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Bow (Power I)");
                }
                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Gold!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.BOW && e.getSlot() == 22) {
                if (player.getInventory().contains(Material.EMERALD, 6)) {
                    for (int i = 0; i < 6; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.EMERALD));
                    }
                    List<Enchantment> enchants = new ArrayList<>();
                    enchants.add(Enchantment.ARROW_KNOCKBACK);
                    enchants.add(Enchantment.ARROW_DAMAGE);
                    ItemStack invPunchBow = CreateItem.createItemEnchanted("§fBow", Material.BOW, Collections.singletonList("§9Power I, Punch I"), 1, true, true, enchants, 1);
                    player.getInventory().addItem(invPunchBow);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Bow (Power I, Punch I)");
                }
                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Emeralds!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }
        }
    }

    @EventHandler
    public void onPotionsClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) { return; }
        if (e.getClickedInventory().getHolder() instanceof Potions) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();

            if (e.getCurrentItem() == null) {
                return;
            }

            if (e.getCurrentItem().getType() == Material.NETHER_STAR) {
                player.openInventory(new QuickBuy().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.TERRACOTTA) {
                player.openInventory(new Blocks().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.GOLDEN_SWORD) {
                player.openInventory(new Melee().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.CHAINMAIL_BOOTS) {
                player.openInventory(new Armor().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.WOODEN_PICKAXE) {
                player.openInventory(new Tools().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.BOW) {
                player.openInventory(new Ranged().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.TNT) {
                player.openInventory(new Utilities().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.POTION && e.getSlot() == 19) {
                if (player.getInventory().contains(Material.EMERALD, 2)) {
                    for (int i = 0; i < 2; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.EMERALD));
                    }
                    invismeta.addCustomEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 600, 0), true);
                    invismeta.setDisplayName("§fPotion of Invisibility");
                    invispot.setItemMeta(invismeta);
                    player.getInventory().addItem(invispot);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Invisibility Potion (30 seconds)");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Emeralds!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.POTION && e.getSlot() == 20) {
                if (player.getInventory().contains(Material.EMERALD, 1)) {
                    player.getInventory().removeItem(new ItemStack(Material.EMERALD));
                    jumpmeta.addCustomEffect(new PotionEffect(PotionEffectType.JUMP, 900, 4), true);
                    jumpmeta.setDisplayName("§fPotion of Jump Boost");
                    jumppot.setItemMeta(jumpmeta);
                    player.getInventory().addItem(jumppot);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Jump V Potion (45 seconds)");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Emeralds!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.POTION && e.getSlot() == 21) {
                if (player.getInventory().contains(Material.EMERALD, 1)) {
                    player.getInventory().removeItem(new ItemStack(Material.EMERALD));
                    speedmeta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 900, 1), true);
                    speedmeta.setDisplayName("§fPotion of Speed");
                    speedpot.setItemMeta(speedmeta);
                    player.getInventory().addItem(speedpot);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Speed II Potion (45 seconds)");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Emeralds!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }
        }
    }

    @EventHandler
    public void onUtilityClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) { return; }
        if (e.getClickedInventory().getHolder() instanceof Utilities) {
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();

            if (e.getCurrentItem() == null) {
                return;
            }

            if (e.getCurrentItem().getType() == Material.NETHER_STAR) {
                player.openInventory(new QuickBuy().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.TERRACOTTA) {
                player.openInventory(new Blocks().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.GOLDEN_SWORD) {
                player.openInventory(new Melee().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.CHAINMAIL_BOOTS) {
                player.openInventory(new Armor().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.WOODEN_PICKAXE) {
                player.openInventory(new Tools().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.BOW) {
                player.openInventory(new Ranged().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.BREWING_STAND) {
                player.openInventory(new Potions().getInventory());
            }

            if (e.getCurrentItem().getType() == Material.GOLDEN_APPLE) {
                if (player.getInventory().contains(Material.GOLD_INGOT, 3)) {
                    for (int i = 0; i < 3; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT));
                    }
                    player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Golden Apple");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Gold!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.SNOWBALL) {
                if (player.getInventory().contains(Material.IRON_INGOT, 40)) {
                    for (int i = 0; i < 40; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT));
                    }
                    ItemStack bedbug = CreateItem.createitem("§fBedbug", Material.SNOWBALL, null, 1, true, true);
                    player.getInventory().addItem(bedbug);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Bedbug");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Iron!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.POLAR_BEAR_SPAWN_EGG) {
                if (player.getInventory().contains(Material.IRON_INGOT, 120)) {
                    for (int i = 0; i < 120; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT));
                    }
                    ItemStack dreamdefender = CreateItem.createitem("§fDream Defender", Material.POLAR_BEAR_SPAWN_EGG, null, 1, true, true);
                    player.getInventory().addItem(dreamdefender);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Dream Defender");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Iron!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.FIRE_CHARGE) {
                if (player.getInventory().contains(Material.IRON_INGOT, 40)) {
                    for (int i = 0; i < 40; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT));
                    }
                    player.getInventory().addItem(fireball);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Fireball");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Iron!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.TNT && e.getSlot() == 23) {
                if (player.getInventory().contains(Material.GOLD_INGOT, 4)) {
                    for (int i = 0; i < 4; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT));
                    }
                    player.getInventory().addItem(new ItemStack(Material.TNT));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " TNT");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Gold!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.ENDER_PEARL) {
                if (player.getInventory().contains(Material.EMERALD, 4)) {
                    for (int i = 0; i < 4; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.EMERALD));
                    }
                    player.getInventory().addItem(new ItemStack(Material.ENDER_PEARL));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Ender Pearl");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Emeralds!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.WATER_BUCKET) {
                if (player.getInventory().contains(Material.GOLD_INGOT, 3)) {
                    for (int i = 0; i < 3; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT));
                    }
                    player.getInventory().addItem(new ItemStack(Material.WATER_BUCKET));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Water Bucket");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Gold!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }

            if (e.getCurrentItem().getType() == Material.EGG) {
                if (player.getInventory().contains(Material.EMERALD, 2)) {
                    for (int i = 0; i < 2; i++) {
                        player.getInventory().removeItem(new ItemStack(Material.EMERALD));
                    }
                    ItemStack bridgeegg = CreateItem.createitem("§fBridge Egg", Material.EGG, null, 1, true, true);
                    player.getInventory().addItem(bridgeegg);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 2);
                    player.sendMessage(ChatColor.GREEN + "You purchased" + ChatColor.GOLD + " Bridge Egg");
                }

                else {
                    player.sendMessage(ChatColor.RED + "You don't have enough Emeralds!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                }
            }
        }
    }



    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        if (e.getEntity() instanceof PolarBear) {
            Entity entity = e.getEntity();
            Location eLoc = entity.getLocation();
            entity.remove();
            Bukkit.getWorld("world").spawnEntity(eLoc, EntityType.IRON_GOLEM);
        }
    }


    @EventHandler
    public void onProjectileLand(ProjectileHitEvent e) {
        if (e.getEntity() instanceof Snowball) {
            Location loc = e.getEntity().getLocation();
            Bukkit.getWorld("world").spawnEntity(loc, EntityType.SILVERFISH);
        }
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.EGG) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onHypixelBridge(ProjectileLaunchEvent event) {
        if(!(event.getEntity() instanceof Egg)) {
            return;
        }

        Player player = (Player) event.getEntity().getShooter();

        long period = 1;

        final int[] ticks = {0};

        new BukkitRunnable() {
            @Override
            public void run(){
                ticks[0] += 1;
                if (event.getEntity().isDead()) {
                    this.cancel();
                    return;
                }

                if (ticks[0] >= 42) {
                    event.getEntity().remove();
                    this.cancel();
                }

                Location blockLoc = event.getEntity().getLocation().subtract(0, 2, 0);
                Location blockLoc2 = event.getEntity().getLocation().subtract(1, 2, 1);
                Location blockLoc3 = event.getEntity().getLocation().subtract(0, 2, 1);
                Location blockLoc4 = event.getEntity().getLocation().subtract(1, 2, 0);

                if (Teams.red.getEntries().contains(player.toString())) {
                    if (event.getEntity().getWorld().getBlockAt(blockLoc).getType() == Material.AIR && event.getEntity().getWorld().getBlockAt(blockLoc2).getType() == Material.AIR) {
                        event.getEntity().getWorld().getBlockAt(blockLoc).setType(Material.RED_WOOL);
                        event.getEntity().getWorld().getBlockAt(blockLoc2).setType(Material.RED_WOOL);
                        event.getEntity().getWorld().getBlockAt(blockLoc3).setType(Material.RED_WOOL);
                        event.getEntity().getWorld().getBlockAt(blockLoc4).setType(Material.RED_WOOL);

                        playerPlacedBlocks.add(blockLoc.getBlock());
                        playerPlacedBlocks.add(blockLoc2.getBlock());
                        playerPlacedBlocks.add(blockLoc3.getBlock());
                        playerPlacedBlocks.add(blockLoc4.getBlock());
                    }
                }

                if (Teams.blue.getEntries().contains(player.toString())) {
                    if (event.getEntity().getWorld().getBlockAt(blockLoc).getType() == Material.AIR && event.getEntity().getWorld().getBlockAt(blockLoc2).getType() == Material.AIR) {
                        event.getEntity().getWorld().getBlockAt(blockLoc).setType(Material.BLUE_WOOL);
                        event.getEntity().getWorld().getBlockAt(blockLoc2).setType(Material.BLUE_WOOL);
                        event.getEntity().getWorld().getBlockAt(blockLoc3).setType(Material.BLUE_WOOL);
                        event.getEntity().getWorld().getBlockAt(blockLoc4).setType(Material.BLUE_WOOL);

                        playerPlacedBlocks.add(blockLoc.getBlock());
                        playerPlacedBlocks.add(blockLoc2.getBlock());
                        playerPlacedBlocks.add(blockLoc3.getBlock());
                        playerPlacedBlocks.add(blockLoc4.getBlock());
                    }
                }

                if (Teams.green.getEntries().contains(player.toString())) {
                    if (event.getEntity().getWorld().getBlockAt(blockLoc).getType() == Material.AIR && event.getEntity().getWorld().getBlockAt(blockLoc2).getType() == Material.AIR) {
                        event.getEntity().getWorld().getBlockAt(blockLoc).setType(Material.LIME_WOOL);
                        event.getEntity().getWorld().getBlockAt(blockLoc2).setType(Material.LIME_WOOL);
                        event.getEntity().getWorld().getBlockAt(blockLoc3).setType(Material.LIME_WOOL);
                        event.getEntity().getWorld().getBlockAt(blockLoc4).setType(Material.LIME_WOOL);

                        playerPlacedBlocks.add(blockLoc.getBlock());
                        playerPlacedBlocks.add(blockLoc2.getBlock());
                        playerPlacedBlocks.add(blockLoc3.getBlock());
                        playerPlacedBlocks.add(blockLoc4.getBlock());
                    }
                }

                if (Teams.yellow.getEntries().contains(player.toString())) {
                    if (event.getEntity().getWorld().getBlockAt(blockLoc).getType() == Material.AIR && event.getEntity().getWorld().getBlockAt(blockLoc2).getType() == Material.AIR) {
                        event.getEntity().getWorld().getBlockAt(blockLoc).setType(Material.YELLOW_WOOL);
                        event.getEntity().getWorld().getBlockAt(blockLoc2).setType(Material.YELLOW_WOOL);
                        event.getEntity().getWorld().getBlockAt(blockLoc3).setType(Material.YELLOW_WOOL);
                        event.getEntity().getWorld().getBlockAt(blockLoc4).setType(Material.YELLOW_WOOL);

                        playerPlacedBlocks.add(blockLoc.getBlock());
                        playerPlacedBlocks.add(blockLoc2.getBlock());
                        playerPlacedBlocks.add(blockLoc3.getBlock());
                        playerPlacedBlocks.add(blockLoc4.getBlock());
                    }
                }

                if (Teams.aqua.getEntries().contains(player.toString())) {
                    if (event.getEntity().getWorld().getBlockAt(blockLoc).getType() == Material.AIR && event.getEntity().getWorld().getBlockAt(blockLoc2).getType() == Material.AIR) {
                        event.getEntity().getWorld().getBlockAt(blockLoc).setType(Material.CYAN_WOOL);
                        event.getEntity().getWorld().getBlockAt(blockLoc2).setType(Material.CYAN_WOOL);
                        event.getEntity().getWorld().getBlockAt(blockLoc3).setType(Material.CYAN_WOOL);
                        event.getEntity().getWorld().getBlockAt(blockLoc4).setType(Material.CYAN_WOOL);

                        playerPlacedBlocks.add(blockLoc.getBlock());
                        playerPlacedBlocks.add(blockLoc2.getBlock());
                        playerPlacedBlocks.add(blockLoc3.getBlock());
                        playerPlacedBlocks.add(blockLoc4.getBlock());
                    }
                }

                if (Teams.white.getEntries().contains(player.toString())) {
                    if (event.getEntity().getWorld().getBlockAt(blockLoc).getType() == Material.AIR && event.getEntity().getWorld().getBlockAt(blockLoc2).getType() == Material.AIR) {
                        event.getEntity().getWorld().getBlockAt(blockLoc).setType(Material.WHITE_WOOL);
                        event.getEntity().getWorld().getBlockAt(blockLoc2).setType(Material.WHITE_WOOL);
                        event.getEntity().getWorld().getBlockAt(blockLoc3).setType(Material.WHITE_WOOL);
                        event.getEntity().getWorld().getBlockAt(blockLoc4).setType(Material.WHITE_WOOL);

                        playerPlacedBlocks.add(blockLoc.getBlock());
                        playerPlacedBlocks.add(blockLoc2.getBlock());
                        playerPlacedBlocks.add(blockLoc3.getBlock());
                        playerPlacedBlocks.add(blockLoc4.getBlock());
                    }
                }

                if (Teams.pink.getEntries().contains(player.toString())) {
                    if (event.getEntity().getWorld().getBlockAt(blockLoc).getType() == Material.AIR && event.getEntity().getWorld().getBlockAt(blockLoc2).getType() == Material.AIR) {
                        event.getEntity().getWorld().getBlockAt(blockLoc).setType(Material.PINK_WOOL);
                        event.getEntity().getWorld().getBlockAt(blockLoc2).setType(Material.PINK_WOOL);
                        event.getEntity().getWorld().getBlockAt(blockLoc3).setType(Material.PINK_WOOL);
                        event.getEntity().getWorld().getBlockAt(blockLoc4).setType(Material.PINK_WOOL);

                        playerPlacedBlocks.add(blockLoc.getBlock());
                        playerPlacedBlocks.add(blockLoc2.getBlock());
                        playerPlacedBlocks.add(blockLoc3.getBlock());
                        playerPlacedBlocks.add(blockLoc4.getBlock());
                    }
                }

                if (Teams.gray.getEntries().contains(player.toString())) {
                    if (event.getEntity().getWorld().getBlockAt(blockLoc).getType() == Material.AIR && event.getEntity().getWorld().getBlockAt(blockLoc2).getType() == Material.AIR) {
                        event.getEntity().getWorld().getBlockAt(blockLoc).setType(Material.GRAY_WOOL);
                        event.getEntity().getWorld().getBlockAt(blockLoc2).setType(Material.GRAY_WOOL);
                        event.getEntity().getWorld().getBlockAt(blockLoc3).setType(Material.GRAY_WOOL);
                        event.getEntity().getWorld().getBlockAt(blockLoc4).setType(Material.GRAY_WOOL);

                        playerPlacedBlocks.add(blockLoc.getBlock());
                        playerPlacedBlocks.add(blockLoc2.getBlock());
                        playerPlacedBlocks.add(blockLoc3.getBlock());
                        playerPlacedBlocks.add(blockLoc4.getBlock());
                    }
                }

            }
        }.runTaskTimer(JavaPlugin.getProvidingPlugin(BedwarsEvents.class), 1L, period/4);
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        for (Block block : e.blockList().toArray(new Block[e.blockList().size()])) {
            if (block.getType() == Material.GLASS) {
                e.blockList().remove(block);
            }

            if (block.getType() == Material.RED_BED ||
                    block.getType() == Material.BLUE_BED ||
                    block.getType() == Material.LIME_BED ||
                    block.getType() == Material.YELLOW_BED ||
                    block.getType() == Material.CYAN_BED ||
                    block.getType() == Material.WHITE_BED ||
                    block.getType() == Material.PINK_BED ||
                    block.getType() == Material.GRAY_BED) {
                e.blockList().remove(block);
            }

            if (block.getType() == Material.DIAMOND_BLOCK || block.getType() == Material.EMERALD_BLOCK || block.getType() == Material.SMOOTH_STONE_SLAB || block.getType() == Material.STONE_BRICKS || block.getType() == Material.STONE_BRICK_STAIRS) {
                e.blockList().remove(block);
            }

            if (block.getType() == Material.OBSIDIAN) {
                e.blockList().remove(block);
            }

            if (!playerPlacedBlocks.contains(block)) {
                e.blockList().remove(block);
            }
        }
    }


    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onInvisDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                Player player = (Player) e.getEntity();
                if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                    player.removePotionEffect(PotionEffectType.INVISIBILITY);
                }
            }
        }
    }
}
