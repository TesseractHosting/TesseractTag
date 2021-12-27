package club.tesseract.tesseracttag;

import club.tesseract.tesseracttag.player.TagPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.time.Instant;
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
        Player bukkitPlayer = player.getPlayer();
        bukkitPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(1);
        bukkitPlayer.setSaturation(1000);
        player.changeName();
    }

    public void removePlayer(UUID uuid){
        players.remove(uuid);
    }

    public void setHunter(UUID uuid){
        if(uuid == null){
            if(hunterId != null){
                players.get(hunterId).setHunter(false);
            }
            return;
        }
        TagPlayer tagged = players.get(uuid);
        long cooldown = tagged.getTimestamp()+5;
        long now = Instant.now().getEpochSecond();
        if(cooldown > now){
            long delta = cooldown - now;
            players.get(hunterId).getPlayer().sendMessage(Component.text("Wait "+delta+" seconds before tagging"));
            return;
        }
        if(hunterId != null){
            players.get(hunterId).setHunter(false);
        }
        tagged.setHunter(true);
        Player player = tagged.getPlayer();
        player.sendMessage("Tag you are it.");
        hunterId = uuid;
        Bukkit.broadcast(Component.text(player.getName()+" is now it."));
    }
}
