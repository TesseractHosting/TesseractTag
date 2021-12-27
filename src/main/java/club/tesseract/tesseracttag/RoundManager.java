package club.tesseract.tesseracttag;

import club.tesseract.tesseracttag.player.TagPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;

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
            player.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 0, 60, 0));
            if (player.isHunter()){
                player.setFrozen(true);
            }
        }
    }

}
