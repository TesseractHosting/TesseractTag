package club.tesseract.tesseracttag;

import club.tesseract.tesseracttag.player.TagPlayer;
import club.tesseract.tesseracttag.tasks.CountdownTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;

public class RoundManager {

    GameManager manager;
    TesseractTag plugin;

    public RoundManager(TesseractTag plugin){
        this.plugin = plugin;
        this.manager = plugin.getGameManager();
    }

    public void startRound(){
        ArrayList<TagPlayer> players = this.manager.getPlayers();
        for (TagPlayer player : players){
            player.getPlayer().teleport(new Location(Bukkit.getWorlds().get(0), 0, 60, 0));
            if (player.isHunter()){
                player.setFrozen(true);
            }
        }
        Bukkit.broadcast(Component.text(ChatColor.YELLOW + "You have five seconds to run from the hunter!"));
        new CountdownTask(plugin).runTaskLater(plugin, 100);
    }

}
