package club.tesseract.tesseracttag;

import club.tesseract.tesseracttag.player.TagPlayer;
import club.tesseract.tesseracttag.tasks.GlobalTaskTimer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

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
            GlobalTaskTimer.create(time, this::releaseHunters);
            return;
        }
        round += 1;
        GlobalTaskTimer.create(time, this::endRound);
    }

    public void endRound(){
        if(manager.getHunter() != null){ // Player leaves before the round end, and they are hunter...
            Player player = manager.getHunter();
            TagPlayer tagPlayer = manager.getPlayer(player.getUniqueId());
            player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, player.getLocation(), 100);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
            tagPlayer.setHunter(false);
        }
        if(manager.getPlayers().size() <= 2){
            if(manager.getHunter() != null)
                manager.removePlayer(manager.getHunter().getUniqueId());
            endGame();
        }else{
            if(manager.getHunter() != null){
                Player player = manager.getHunter();
                manager.pickNextHunter();
                manager.removePlayer(player.getUniqueId());
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
