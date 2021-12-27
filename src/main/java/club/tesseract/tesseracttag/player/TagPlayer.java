package club.tesseract.tesseracttag.player;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TagPlayer {

    public static TagPlayer create(Player player){
        return new TagPlayer(player.getUniqueId());
    }

    private final UUID uniqueId;
    private boolean hunter;

    public TagPlayer(UUID uniqueId){
        this.uniqueId = uniqueId;
        this.hunter = false;
    }

    public Player getPlayer(){
        return Bukkit.getPlayer(this.uniqueId);
    }
    public OfflinePlayer getOfflinePlayer(){
        return Bukkit.getOfflinePlayer(this.uniqueId);
    }

    public void setHunter(boolean hunter) {
        this.hunter = hunter;
    }

    public boolean isHunter() {
        return hunter;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }
}
