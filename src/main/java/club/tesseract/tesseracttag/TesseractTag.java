package club.tesseract.tesseracttag;

import org.bukkit.plugin.java.JavaPlugin;

public class TesseractTag extends JavaPlugin {

    static TesseractTag plugin = null;
    private GameManager gameManager;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        gameManager = new GameManager();
        getLogger().info("Plugin Enabled");
    }

    @Override
    public void onDisable() {

        getLogger().info("Plugin Disabled");
    }

    public static TesseractTag getPlugin() {
        return plugin;
    }
}
