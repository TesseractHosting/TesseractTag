package club.tesseract.tesseracttag.tasks;

import club.tesseract.tesseracttag.TesseractTag;
import club.tesseract.tesseracttag.utils.ShadowScoreboard;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class GlobalTaskTimer extends BukkitRunnable {


    public static BukkitTask create(int amount, Runnable runnable){
        return new GlobalTaskTimer(amount, runnable).runTaskTimer(TesseractTag.getPlugin(), 0, 20);
    }

    private final int endTime;
    private int currentTime;
    private Runnable runnable;

    public GlobalTaskTimer(int endTime, Runnable runnable){
        this.currentTime = 0;
        this.endTime = endTime;
        this.runnable = runnable;
    }

    @Override
    public void run() {
        if(endTime > currentTime) {
            int seconds = endTime - currentTime;
            int minutes = (int) Math.floor(seconds / 60.0);
            String formattedMinutes = String.format("%02d", minutes);
            String formattedSeconds = String.format("%02d", seconds % 60);
            ShadowScoreboard.updateTimer(Component.text(formattedMinutes+":"+formattedSeconds));
        }else{
            Bukkit.getScheduler().runTask(TesseractTag.getPlugin(), runnable);
            cancel();
        }
        currentTime++;
    }
}
