package club.tesseract.tesseracttag;

import club.tesseract.tesseracttag.player.TagPlayer;
import club.tesseract.tesseracttag.tasks.GlobalTaskTimer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class RoundManager {

    private final GameManager manager;
    private int round = 0;

    public RoundManager(GameManager gameManager){
        this.manager = gameManager;
    }

    public int calculateTimer(){
        ArrayList<TagPlayer> players = manager.getPlayers();
        int prey = players.size()-1;
        return Math.min(prey, 10)*10;
    }

    public void releaseHunters(){
        manager.setRunning();
        manager.getHunter().setAllowFlight(false);
        startRound(false);
    }

    public void startRound(boolean firstRound){
        int time = calculateTimer();
        if (firstRound) {
            GlobalTaskTimer.create(7, this::releaseHunters);
            Bukkit.broadcast(Component.text(ChatColor.YELLOW + "You have 7 seconds to run from the hunter!"));
            return;
        }
        round += 1;
        GlobalTaskTimer.create(time, this::endRound);
    }

    public void endRound(){
        Player hunterPlayer = manager.getHunter();
        if(hunterPlayer != null){ // Player leaves before the round end, and they are hunter...
            hunterPlayer.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, hunterPlayer.getLocation(), 100);
            hunterPlayer.getWorld().playSound(hunterPlayer.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
            TagPlayer tagPlayer = manager.getPlayer(hunterPlayer.getUniqueId());
            if(tagPlayer != null)tagPlayer.setHunter(false);
        }
        if(manager.getPlayers().size() <= 4){
            manager.getPlayers().forEach(player -> player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,1000, 10, true, false,false)));
        }
        if(manager.getPlayers().size() <= 3){
            manager.getPlayers().forEach(player ->{
                final Player bukkitPlayer = player.getPlayer();
                bukkitPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000, 10, true, false, false));
                bukkitPlayer.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1000, 10, true, false, false));
                final ItemStack stick = new ItemStack(Material.STICK);
                stick.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
                bukkitPlayer.getInventory().addItem(stick);
            });
        }
        if(manager.getPlayers().size() <= 2){
            if(hunterPlayer != null)
                manager.removePlayer(manager.getHunter().getUniqueId());
            endGame();
        }else{
            if(hunterPlayer != null){
                manager.removePlayer(hunterPlayer.getUniqueId());
                manager.pickNextHunter();
            }else{
                manager.pickNextHunter();
            }
            startRound(false);
        }
    }

    public void endGame(){
        if(manager.getPlayers().isEmpty()){// Should never be true... in theory
            Bukkit.broadcast(Component.text("The Programmers win... no one was left..."));
            return;
        }
        TagPlayer player = manager.getPlayers().get(0);
        Bukkit.broadcast(Component.text(player.getPlayer().getName()+" has won!!!", NamedTextColor.GREEN));
        manager.endGame();
    }

}
