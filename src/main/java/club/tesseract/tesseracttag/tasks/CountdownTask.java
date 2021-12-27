package club.tesseract.tesseracttag.tasks;

import club.tesseract.tesseracttag.TesseractTag;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class CountdownTask extends BukkitRunnable {

    private TesseractTag plugin;

    public CountdownTask(TesseractTag plugin){
        this.plugin = plugin;
    }

    @Override
    public void run() {
        Bukkit.getScheduler().runTaskLater(plugin, new GameTask(), 0);
    }
}
