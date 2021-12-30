package club.tesseract.tesseracttag.utils;

import club.tesseract.tesseracttag.TesseractTag;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Objects;

public class ShadowScoreboard {

    private static final String onlineCountEntry = ChatColor.GOLD + "\u00BB Players: ";
    private static final String hunterEntry = ChatColor.GOLD + "\u00BB hunter: ";
    private static final String timerEntry = ChatColor.GOLD + "\u00BB Time: ";
    public static Component timer = Component.text("00:00");
    private static final Scoreboard globalBoard = Bukkit.getScoreboardManager().getMainScoreboard();

    public static void shutdown(){
        Bukkit.getOnlinePlayers().forEach(player ->{
            if(player.getScoreboard().getObjective("TesseractTag") != null)
                player.getScoreboard().getObjective("TesseractTag").unregister();
        });
    }

    public static void updateTimer(Component component){
        timer = component;
        sendScoreboardUpdate();
    }

    public static void setScoreboard(Player player){
        if(player.getScoreboard() == globalBoard) player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        final Scoreboard board = player.getScoreboard();
        final Objective obj = board.getObjective("TesseractTag") != null ? board.getObjective("TesseractTag") : board.registerNewObjective("TesseractTag", "dummy", Component.text("--Tesseract Tag--"));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        final Team onlineCounter = board.getTeam("onlineCounter") != null ?  board.getTeam("onlineCounter") : board.registerNewTeam("onlineCounter");
        onlineCounter.addEntry(onlineCountEntry);
        onlineCounter.suffix(onlineCount());
        obj.getScore(onlineCountEntry).setScore(7);
        obj.getScore(ChatColor.RED.toString()+" ").setScore(6);
        final Team hunter = board.getTeam("hunter") != null? board.getTeam("hunter") : board.registerNewTeam("hunter");
        hunter.addEntry(hunterEntry);
        hunter.suffix(getHunter());
        obj.getScore(hunterEntry).setScore(5);
        final Team timer = board.getTeam("timer") != null? board.getTeam("timer") : board.registerNewTeam("timer");
        timer.addEntry(timerEntry);
        timer.suffix(Component.text("00:00"));
        obj.getScore(ChatColor.BLUE+" ").setScore(4);
        obj.getScore(timerEntry).setScore(3);
        obj.getScore(ChatColor.BLACK+" ").setScore(2);
        obj.getScore(ChatColor.AQUA+".gg/Y7RKzctY99").setScore(1);
        obj.getScore(ChatColor.AQUA+".gg/tesseract").setScore(0);
        obj.getScore(ChatColor.GOLD+"IP: "+ChatColor.AQUA+"tag.tesseract.club").setScore(8);
        player.setScoreboard(board);
    }
    public static void forceReloadScoreboards(){
        Bukkit.getOnlinePlayers().forEach(ShadowScoreboard::forceReloadScoreboards);
    }
    public static void forceReloadScoreboards(Player player){
        setScoreboard(player);
    }
    public static void sendScoreboardUpdate(Player player){
        updateScoreboard(player);
    }
    public static void sendScoreboardUpdate() {
        Bukkit.getOnlinePlayers().forEach(ShadowScoreboard::updateScoreboard);
    }
    private static void updateScoreboard(Player player){
        Scoreboard scoreboard = player.getScoreboard();
        if(scoreboard.getTeam("onlineCounter") != null)
            Objects.requireNonNull(scoreboard.getTeam("onlineCounter")).suffix(onlineCount());
        if(scoreboard.getTeam("hunter") != null)
            Objects.requireNonNull(scoreboard.getTeam("hunter")).suffix(getHunter());
        if(scoreboard.getTeam("timer") != null)
            Objects.requireNonNull(scoreboard.getTeam("timer")).suffix(getTimer());
    }

    public static void updateObjectiveName(Player player, String objectiveName, Component newName){
        if(player.getScoreboard().getObjective(objectiveName) != null)
            Objects.requireNonNull(player.getScoreboard().getObjective(objectiveName)).displayName(newName);
    }
    public static void updateObjectiveName(String objectiveName, Component newName){
        Bukkit.getOnlinePlayers().forEach(player -> {
            updateObjectiveName(player, objectiveName, newName);
        });
    }

    private static Component getTimer(){
        return timer;
    }

    private static Component getHunter(){
        Player player = TesseractTag.getPlugin().getGameManager().getHunter();
        return Component.text(player == null ? "Non": player.getName());
    }

    private static Component onlineCount(){
        return  Component.text(Bukkit.getOnlinePlayers().size()).color(NamedTextColor.DARK_RED).append(Component.text("/").color(NamedTextColor.RED)).append(Component.text(Bukkit.getMaxPlayers()).color(NamedTextColor.DARK_RED));
    }
}
