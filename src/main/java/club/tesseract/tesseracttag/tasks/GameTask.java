package club.tesseract.tesseracttag.tasks;

import club.tesseract.tesseracttag.TesseractTag;
import club.tesseract.tesseracttag.player.TagPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class GameTask extends BukkitRunnable {

    private TesseractTag plugin;

    public GameTask(TesseractTag plugin){
        this.plugin = plugin;
    }

    @Override
    public void run() {
        TagPlayer loser = null;
        ArrayList<TagPlayer> players = (ArrayList<TagPlayer>) plugin.getGameManager().getPlayers().clone();
        for (TagPlayer player : players){
            if(player.isHunter()){
                loser = player;
            }
        }

        loser.getPlayer().sendMessage(ChatColor.RED+ "You were the last one to be tagged in 30 seconds! You lose!");
        loser.getPlayer().setGameMode(GameMode.SPECTATOR);
        plugin.getGameManager().getPlayers().remove(loser);
        players.remove(loser);

        if(players.size() == 1){
            players.get(0).getPlayer().sendMessage(ChatColor.GREEN+ "=================");
            players.get(0).getPlayer().sendMessage(ChatColor.GREEN+ "| YOU WON ! GG! |");
            players.get(0).getPlayer().sendMessage(ChatColor.GREEN+ "=================");
            players.get(0).getPlayer().setGameMode(GameMode.SPECTATOR);
            Bukkit.broadcast(Component.text(ChatColor.GOLD + players.get(0).getPlayer().getName() + " won the Tag event! GG !"));
        }

        TagPlayer nextHunter;
        if(players.indexOf(loser) != players.size() - 1){
            nextHunter = players.get(players.indexOf(loser) + 1);
        }else{
            nextHunter = players.get(players.indexOf(loser) - 1);
        }
        nextHunter.setHunter(true);
        Bukkit.broadcast(Component.text(ChatColor.RED + "The new hunter is " + nextHunter.getPlayer().getName()));
        plugin.getRoundManager().startRound();
    }
}
