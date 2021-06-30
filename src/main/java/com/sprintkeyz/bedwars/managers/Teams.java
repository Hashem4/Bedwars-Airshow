package com.sprintkeyz.bedwars.managers;

import com.sprintkeyz.bedwars.commands.BedwarsCommands;
import com.sprintkeyz.bedwars.events.BedwarsEvents;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Teams {

    static Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

    static SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");

    public static Team red = scoreboard.registerNewTeam("Red");
    public static Team gray = scoreboard.registerNewTeam("Gray");
    public static Team white = scoreboard.registerNewTeam("White");
    public static Team pink = scoreboard.registerNewTeam("Pink");
    public static Team aqua = scoreboard.registerNewTeam("Aqua");
    public static Team yellow = scoreboard.registerNewTeam("Yellow");
    public static Team green = scoreboard.registerNewTeam("Green");
    public static Team blue = scoreboard.registerNewTeam("Blue");

    public static String redStr = "§a✓";
    public static String blueStr = "§a✓";
    public static String greenStr = "§a✓";
    public static String yellowStr = "§a✓";
    public static String aquaStr = "§a✓";
    public static String whiteStr = "§a✓";
    public static String pinkStr = "§a✓";
    public static String grayStr = "§a✓";

    public static Objective objective = scoreboard.registerNewObjective("test", "dummy", "§e§lBED WARS");

    public static void scoreboard() {

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");

        Team red = scoreboard.registerNewTeam("Red");
        Team gray = scoreboard.registerNewTeam("Gray");
        Team white = scoreboard.registerNewTeam("White");
        Team pink = scoreboard.registerNewTeam("Pink");
        Team aqua = scoreboard.registerNewTeam("Aqua");
        Team yellow = scoreboard.registerNewTeam("Yellow");
        Team green = scoreboard.registerNewTeam("Green");
        Team blue = scoreboard.registerNewTeam("Blue");

        String redStr = "§a✓";
        String blueStr = "§a✓";
        String greenStr = "§a✓";
        String yellowStr = "§a✓";
        String aquaStr = "§a✓";
        String whiteStr = "§a✓";
        String pinkStr = "§a✓";
        String grayStr = "§a✓";

        Objective objective = scoreboard.registerNewObjective("test", "dummy", "§e§lBED WARS");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        // MAKE IT DISPLAY WHEN PLAYERS JOIN IN FUTURE
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(JavaPlugin.getProvidingPlugin(Teams.class), new Runnable() {
            @Override
            public void run() {
                Date currentDate = new Date();
                Score serverdate = objective.getScore(ChatColor.GRAY + format.format(currentDate) + " m001A");
                Score serverip = objective.getScore(ChatColor.YELLOW + "www.hypixel.net");

                Score space = objective.getScore("");
                Score space2 = objective.getScore(" ");
                Score space3 = objective.getScore("  ");
                Score red = objective.getScore("§cR " + "§fRed: " + redStr);
                Score blue = objective.getScore("§9B " + "§fBlue: " + blueStr);
                Score green = objective.getScore("§aG " + "§fGreen: " + greenStr);
                Score yellow = objective.getScore("§eY " + "§fYellow: " + yellowStr);
                Score aqua = objective.getScore("§bA " + "§fAqua: " + aquaStr);
                Score white = objective.getScore("§fW " + "§fWhite: " + whiteStr);
                Score pink = objective.getScore("§dP " + "§fPink: " + pinkStr);
                Score gray = objective.getScore("§8S " + "§fGray: " + grayStr);


                // higher number = higher up on board

                serverdate.setScore(12);
                space2.setScore(11);
                red.setScore(10);
                blue.setScore(9);
                green.setScore(8);
                yellow.setScore(7);
                aqua.setScore(6);
                white.setScore(5);
                pink.setScore(4);
                gray.setScore(3);

                space.setScore(2);
                serverip.setScore(1);

                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.setScoreboard(scoreboard);
                }
            }
        }, 0L, 20L);
    }
}
