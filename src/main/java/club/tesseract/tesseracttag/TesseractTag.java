package club.tesseract.tesseracttag;

import org.bukkit.plugin.java.JavaPlugin;

public class TesseractTag extends JavaPlugin {

    static TesseractTag plugin = null;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {

        getLogger().info("Plugin Enabled");
    }

    @Override
    public void onDisable() {

        getLogger().info("Plugin Disabled");
    }
}
