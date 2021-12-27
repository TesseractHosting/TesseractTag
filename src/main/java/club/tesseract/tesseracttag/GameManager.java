package club.tesseract.tesseracttag;

import club.tesseract.tesseracttag.player.TagPlayer;
import org.bukkit.attribute.Attribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class GameManager {

    private final HashMap<UUID, TagPlayer> players = new HashMap<UUID, TagPlayer>();
    private UUID hunterId = null;


    public ArrayList<TagPlayer> getPlayers(){
        return new ArrayList<TagPlayer>(players.values());
    }

    public TagPlayer getPlayer(UUID player){
        return players.get(player);
    }

    public void addPlayer(TagPlayer player){
        players.put(player.getUniqueId(), player);
        player.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(1);
    }

    public void removePlayer(UUID uuid){
        players.remove(uuid);
    }

    public void setHunter(UUID uuid){
        if(hunterId != null){
            players.get(hunterId).setHunter(false);
        }
        TagPlayer tagged = players.get(uuid);
        tagged.setHunter(true);
        tagged.getPlayer().sendMessage("Tag you are it.");
        hunterId = uuid;
    }
}
