package club.tesseract.tesseracttag.listeners;

import club.tesseract.tesseracttag.TesseractTag;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener extends ShadowListener {

    private final TesseractTag plugin;
    public PlayerDeathListener(TesseractTag plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        event.setCancelled(true);//We never let you die ;)
        //We dealth with damage to the player in PlayerHurtListener... so they `should` only die IF they were to get smacked by the hunter
        plugin.getGameManager().setHunter(event.getPlayer().getUniqueId());

    }
}