package club.tesseract.tesseracttag.listeners;

import club.tesseract.tesseracttag.TesseractTag;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class ShadowListener implements Listener {

    public ShadowListener(){
        Bukkit.getServer().getPluginManager().registerEvents(this, TesseractTag.getPlugin());
    }
}
