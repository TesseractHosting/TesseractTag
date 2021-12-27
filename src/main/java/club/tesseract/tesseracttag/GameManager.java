package club.tesseract.tesseracttag;

import club.tesseract.tesseracttag.player.TagPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class GameManager {

    private final HashMap<UUID, TagPlayer> players = new HashMap<UUID, TagPlayer>();


    public ArrayList<TagPlayer> getPlayers(){
        return new ArrayList<TagPlayer>(players.values());
    }

    public void addPlayer(TagPlayer player){
        players.put(player.getUniqueId(), player);
    }

    public void removePlayer(UUID uuid){
        players.remove(uuid);
    }

}
