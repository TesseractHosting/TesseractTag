package club.tesseract.tesseracttag.tasks;

import club.tesseract.tesseracttag.TesseractTag;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class ActionBarTask extends BukkitRunnable {

    private static BukkitTask actionBarTask;

    public static void start(){
        actionBarTask = new ActionBarTask(TesseractTag.getPlugin()).runTaskTimerAsynchronously(TesseractTag.getPlugin(), 0, 18);
    }
    public static void shutdown(){
        if(actionBarTask == null)return;
        if(!actionBarTask.isCancelled())actionBarTask.cancel();
    }
    private final TesseractTag plugin;
    public ActionBarTask(TesseractTag plugin){
        this.plugin = plugin;
    }

    @Override
    public void run() {
        plugin.getGameManager().getPlayers().forEach(tagPlayer ->{
            Player player = tagPlayer.getPlayer();
            if(tagPlayer.isHunter())
                if(!tagPlayer.onCoolDown())
                    player.sendActionBar(Component.text("Hunt your pray"));
                else
                    player.sendActionBar(Component.text("Cooldown "+ tagPlayer.getCooldown()+" Seconds left"));
            else
                player.sendActionBar(Component.text("Run and hide"));
        });
    }
}
