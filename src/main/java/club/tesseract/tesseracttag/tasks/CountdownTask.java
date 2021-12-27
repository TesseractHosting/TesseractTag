package club.tesseract.tesseracttag.tasks;

import club.tesseract.tesseracttag.TesseractTag;
import club.tesseract.tesseracttag.player.TagPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import net.kyori.adventure.text.Component;

public class CountdownTask extends BukkitRunnable {

    private TesseractTag plugin;

    public CountdownTask(TesseractTag plugin){
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (TagPlayer player : plugin.getGameManager().getPlayers()){
            if (player.isHunter()){
                player.setFrozen(false);
            }
        }
        Bukkit.broadcast(Component.text(ChatColor.RED + "The hunter is coming!!!"));
        BukkitTask startGame = new GameTask(plugin).runTaskLater(plugin, 600);
    }
}
