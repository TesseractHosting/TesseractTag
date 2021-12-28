package club.tesseract.tesseracttag.listeners;

import io.papermc.paper.event.player.PlayerItemFrameChangeEvent;
import org.bukkit.event.EventHandler;

public class ItemFrameInteractListener extends ShadowListener {


    @EventHandler
    public void onInteract(PlayerItemFrameChangeEvent event){
        if(!event.getPlayer().isOp())
            event.setCancelled(true);
    }


}
