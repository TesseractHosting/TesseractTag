package club.tesseract.tesseracttag.listeners;

import club.tesseract.tesseracttag.TesseractTag;
import club.tesseract.tesseracttag.player.TagPlayer;
import club.tesseract.tesseracttag.utils.GameState;
import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Objects;

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
        if(plugin.getGameManager().getGameState() != GameState.PLAYING){
            event.setCancelled(true);
            return;
        }
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) {
            event.setCancelled(true);
            return;
        }
        TagPlayer hunter = plugin.getGameManager().getPlayer(event.getDamager().getUniqueId());
        TagPlayer prey = plugin.getGameManager().getPlayer(event.getEntity().getUniqueId());
        if (hunter == null || prey == null) {
            event.setCancelled(true);
            return;
        }
        if(prey.isHunter()){
            event.setCancelled(true);
            return;
        }
        if(!hunter.isHunter()){
            if(Objects.requireNonNull(((Player) event.getDamager()).getActiveItem()).getType() == Material.STICK){
                event.setDamage(0);
                return;
            }
            event.setCancelled(true);
            return;
        }
        if (hunter.onCoolDown()) {
            event.setCancelled(true);
            return;
        }
        event.setCancelled(true);
        ((Player) event.getDamager()).getInventory().clear();
        plugin.getGameManager().setHunter(event.getEntity().getUniqueId());
        event.getEntity().getWorld().spawnParticle(Particle.DAMAGE_INDICATOR, event.getEntity().getLocation(), 100, 0.5, 0.5, 0.5);
        ((Player) event.getEntity()).playSound(event.getEntity().getLocation(), Sound.ENTITY_TURTLE_EGG_BREAK, 1, 1);
        ((Player) event.getDamager()).playSound(event.getDamager().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1,1);
    }
}
