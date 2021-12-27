package club.tesseract.tesseracttag.listeners;

import club.tesseract.tesseracttag.TesseractTag;
import club.tesseract.tesseracttag.player.TagPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerHurtListener extends ShadowListener{

    private final TesseractTag plugin;
    public PlayerHurtListener(TesseractTag plugin){
        super();
        this.plugin = plugin;
    }

    @EventHandler
    public void onHurt(EntityDamageEvent event){
        if(event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK){
            event.setCancelled(true);
        }
    }

    // Only Allow Hunters To Cause Damage
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onHurtByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) {
            event.setCancelled(true);
            return;
        }
        TagPlayer hunter = plugin.getGameManager().getPlayer(event.getDamager().getUniqueId());
        if (hunter == null) {
            event.setCancelled(true);
            return;
        }
        if (!hunter.isHunter()) {
            event.setCancelled(true);
            return;
        }
        event.setDamage(10);
        ((Player) event.getDamager()).getInventory().clear();
        //Hunter is hunter
    }
}
