package club.tesseract.tesseracttag.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemListener extends ShadowListener{

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event){
        event.setCancelled(true);
    }
}
