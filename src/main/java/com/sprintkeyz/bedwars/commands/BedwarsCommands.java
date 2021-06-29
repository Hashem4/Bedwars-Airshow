package com.sprintkeyz.bedwars.commands;

import com.sprintkeyz.bedwars.events.BedwarsEvents;
import com.sprintkeyz.bedwars.managers.Generators;
import com.sprintkeyz.bedwars.managers.Teams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class BedwarsCommands implements CommandExecutor {

    Generators generators = new Generators();

    public static HashMap<Player, Boolean> readyToStart = new HashMap<Player, Boolean>();


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender.isOp()) {
            if (cmd.getName().equalsIgnoreCase("revive")) {
                if (!(args.length == 1)) {
                    sender.sendMessage(ChatColor.RED + "Usage: /revive <player>");
                    return true;
                }
                String selPlayer = args[0];
                if (Teams.red.getEntries().contains(selPlayer.toString())) {
                    if (BedwarsEvents.RedCanDie == false) {
                        BedwarsEvents.RedCanDie = true;
                        sender.sendMessage(ChatColor.GREEN + "Successfully revived " + selPlayer + "!");
                        Block redbed = Bukkit.getServer().getWorld("world").getBlockAt(-40, 63, -76);
                        redbed.setType(Material.RED_BED);
                    } else if (BedwarsEvents.RedCanDie == true) {
                        sender.sendMessage(ChatColor.YELLOW + "No need, that player is already alive!");
                    }
                }
            }

            if (cmd.getName().equalsIgnoreCase("stopbasegens")) {
                Iterator iterator = Generators.taskID.iterator();

                while (iterator.hasNext()) {
                    Bukkit.getServer().getScheduler().cancelTask((Integer) iterator.next());
                }
                Bukkit.getServer().getScheduler().cancelTask(Generators.taskID2);
                sender.sendMessage(ChatColor.RED + "Stopped all base generators!");
            }

            if (cmd.getName().equalsIgnoreCase("startbasegens")) {
                generators.GenerateGold();
                generators.GenerateIron();
                sender.sendMessage(ChatColor.GREEN + "Started all base generators!");
            }

            if (cmd.getName().equalsIgnoreCase("stopdiamonds")) {
                Bukkit.getServer().getScheduler().cancelTask(Generators.taskID3);
                Bukkit.getServer().getScheduler().cancelTask(Generators.taskID4);
                Bukkit.getServer().getScheduler().cancelTask(Generators.taskID5);
                Bukkit.getServer().getScheduler().cancelTask(Generators.taskID6);
                sender.sendMessage(ChatColor.RED + "Stopped all diamond generators!");
            }

            if (cmd.getName().equalsIgnoreCase("startdiamonds")) {
                generators.GenerateDiamonds();
                sender.sendMessage(ChatColor.GREEN + "Started all diamond generators!");
            }

            if (cmd.getName().equalsIgnoreCase("stopemeralds")) {
                Bukkit.getServer().getScheduler().cancelTask(Generators.taskID7);
                Bukkit.getServer().getScheduler().cancelTask(Generators.taskID8);
                Bukkit.getServer().getScheduler().cancelTask(Generators.taskID9);
                Bukkit.getServer().getScheduler().cancelTask(Generators.taskID10);
                sender.sendMessage(ChatColor.RED + "Stopped all emerald generators!");
            }

            if (cmd.getName().equalsIgnoreCase("startemeralds")) {
                generators.GenerateEmeralds();
                sender.sendMessage(ChatColor.GREEN + "Started all emerald generators!");
            }

            if (cmd.getName().equalsIgnoreCase("vanish")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "Sorry, this command is only available to players!");
                    return true;
                } else {


                    Player player = (Player) sender;

                    if (!(player.hasPotionEffect(PotionEffectType.INVISIBILITY))) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 0, false, false));
                        player.sendMessage(ChatColor.GREEN + "You are now invisible!");
                        return true;
                    } else {
                        player.removePotionEffect(PotionEffectType.INVISIBILITY);
                        player.sendMessage(ChatColor.RED + "You are no longer invisible!");
                        return true;
                    }

                }
            }

            if (cmd.getName().equalsIgnoreCase("nick")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "Sorry, this command is only available to players!");
                    return true;
                } else {
                    if (args.length < 1) {
                        sender.sendMessage(ChatColor.RED + "Usage: /nick <nickname>");
                        return true;
                    } else {
                        Player player = (Player) sender;
                        /*player.setPlayerListName(args[0]);
                        player.setDisplayName(args[0]);
                        player.sendMessage(ChatColor.GREEN + "You are now nicked as '" + player.getPlayerListName() + "'!");*/
                        player.sendMessage("§cThis command is being updated and is temporarily disabled!");
                    }
                }
            }

            if (cmd.getName().equalsIgnoreCase("clearblocks")) {
                for (Block block : BedwarsEvents.playerPlacedBlocks) {
                    Bukkit.getWorld("world").getBlockAt(block.getLocation()).setType(Material.AIR);
                }
                sender.sendMessage(ChatColor.GREEN + "Cleared all player-placed blocks!");
            }

            if (cmd.getName().equalsIgnoreCase("nickreset")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "Sorry, this command is only available to players!");
                    return true;

                } else {
                    Player player = (Player) sender;
                    player.setPlayerListName(player.getName());
                    player.setDisplayName(player.getName());
                    player.sendMessage(ChatColor.RED + "You are no longer nicked!");
                }
            }


            if (cmd.getName().equalsIgnoreCase("rlc")) {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "reload confirm");
            }

            if (cmd.getName().equalsIgnoreCase("i")) {
                if (args.length == 2 && sender instanceof Player) {
                    int amount = Integer.parseInt(args[1]);
                    Material item = Material.getMaterial(args[0]);
                    Player player = (Player) sender;
                    try {
                        player.getInventory().setItem(player.getInventory().firstEmpty(), new ItemStack(item, amount));
                        player.sendMessage(ChatColor.GREEN + "Gave " + ChatColor.YELLOW + amount + ChatColor.GREEN + " " + item.toString().toLowerCase() + " to " + player.getName());
                    }

                    catch (IllegalArgumentException e) {
                        player.sendMessage(ChatColor.RED + "Invalid item!");
                    }
                }

                else {
                    sender.sendMessage(ChatColor.RED + "Either you are not a player, or you have used the command incorrectly! Use i <itemname> <amount>");
                    sender.sendMessage(ChatColor.RED + "Ex: /i EMERALD 1, /i IRON_INGOT 1. This uses Bukkit/Spigot material id's, but that may be changed soon!");
                }
            }

            if (cmd.getName().equalsIgnoreCase("clearconsole")) {
                try {
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Bedwars] Console cleared!");
                } catch (InterruptedException | IOException e1) {
                    e1.printStackTrace();
                }
            }

            if (cmd.getName().equalsIgnoreCase("cleargens")) {
                for (Entity entity : Bukkit.getServer().getWorld("world").getEntities()) {
                    if (entity instanceof Item) {
                        entity.remove();
                    }
                }

                sender.sendMessage(ChatColor.GREEN + "Successfully removed all drops!");
            }

            if (cmd.getName().equalsIgnoreCase("stopgens")) {
                Iterator iterator = Generators.taskID.iterator();

                while (iterator.hasNext()) {
                    Bukkit.getServer().getScheduler().cancelTask((Integer) iterator.next());
                }
                Bukkit.getServer().getScheduler().cancelTask(Generators.taskID2);
                Bukkit.getServer().getScheduler().cancelTask(Generators.taskID3);
                Bukkit.getServer().getScheduler().cancelTask(Generators.taskID4);
                Bukkit.getServer().getScheduler().cancelTask(Generators.taskID5);
                Bukkit.getServer().getScheduler().cancelTask(Generators.taskID6);
                Bukkit.getServer().getScheduler().cancelTask(Generators.taskID7);
                Bukkit.getServer().getScheduler().cancelTask(Generators.taskID8);
                Bukkit.getServer().getScheduler().cancelTask(Generators.taskID9);
                Bukkit.getServer().getScheduler().cancelTask(Generators.taskID10);
                sender.sendMessage(ChatColor.RED + "Stopped all generators!");
            }

            if (cmd.getName().equalsIgnoreCase("startgens")) {
                generators.GenerateIron();
                generators.GenerateGold();
                generators.GenerateDiamonds();
                sender.sendMessage(ChatColor.GREEN + "Started all generators!");
            }

            if (cmd.getName().equalsIgnoreCase("lobby")) {
                if (sender instanceof Player) {
                    if (args.length == 1) {

                        Player player = (Player) sender;

                        if (args[0].equalsIgnoreCase("bedwars") | args[0].equalsIgnoreCase("bw")) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "clearblocks");
                            sender.sendMessage("§aSending you to the BedWars lobby...");
                            sendToServer(player, "lobby");
                            return true;
                        }

                        if (args[0].equalsIgnoreCase("duels") | args[0].equalsIgnoreCase("duel")) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "clearblocks");
                            sender.sendMessage("§aSending you to the Duels lobby...");
                            sendToServer(player, "duels");
                            return true;
                        }
                    }

                    else {
                        sender.sendMessage(ChatColor.RED + "Incorrect usage!");
                    }
                }

                else {
                    sender.sendMessage(ChatColor.RED + "Sorry, this command is only available to players!");
                    return true;
                }
            }

            if (cmd.getName().equalsIgnoreCase("gmc")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    player.setGameMode(GameMode.CREATIVE);
                }

                else {
                    sender.sendMessage(ChatColor.RED + "Sorry, this command is only available to players!");
                    return true;
                }
            }

            if (cmd.getName().equalsIgnoreCase("gmsp")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    player.setGameMode(GameMode.SPECTATOR);
                }

                else {
                    sender.sendMessage(ChatColor.RED + "Sorry, this command is only available to players!");
                    return true;
                }
            }

            if (cmd.getName().equalsIgnoreCase("gms")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    player.setGameMode(GameMode.SURVIVAL);
                }

                else {
                    sender.sendMessage(ChatColor.RED + "Sorry, this command is only available to players!");
                    return true;
                }
            }

            if (cmd.getName().equalsIgnoreCase("gma")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    player.setGameMode(GameMode.ADVENTURE);
                }

                else {
                    sender.sendMessage(ChatColor.RED + "Sorry, this command is only available to players!");
                    return true;
                }
            }

            if (cmd.getName().equalsIgnoreCase("send")) {
                if (args.length == 2) {
                    sendToServer(Bukkit.getPlayer(args[0]), args[1]);
                } else {
                    sender.sendMessage(ChatColor.RED + "Incorrect usage! use send <player> <server>");
                }
            }

            if (cmd.getName().equalsIgnoreCase("broadcast")) {
                if (args.length > 1) {

                }
            }

            /*if (cmd.getName().equalsIgnoreCase("setinv")) {
                if (sender instanceof Player) {
                    if (args.length == 1) {
                        Player invplayer = Bukkit.getPlayer(args[0]);
                        Player player = (Player) sender;
                        Swapinv swapinv = new Swapinv(invplayer, player);
                        player.openInventory(swapinv.getInventory());
                    }
                }
            }*/
        }

        else {
            sender.sendMessage(ChatColor.RED + "Sorry, but you don't have the required permissions!");
        }

        return true;
    }

    public void sendToServer(Player player, String name) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF(name);
        } catch (IOException eee) {
            Bukkit.getLogger().info("You'll never see me!");
        }
        player.sendPluginMessage(JavaPlugin.getProvidingPlugin(BedwarsCommands.class), "BungeeCord", b.toByteArray());

    }
}
