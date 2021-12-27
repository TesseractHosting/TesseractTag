package club.tesseract.tesseracttag.listeners;

import club.tesseract.tesseracttag.TesseractTag;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener extends ShadowListener{

    private final TesseractTag plugin;
    public PlayerQuitListener(TesseractTag plugin){
        super();
        this.plugin = plugin;
    }

    private final static Component symbol = Component.text("[", NamedTextColor.GRAY).append(Component.text("X", NamedTextColor.RED)).append(
            Component.text("]", NamedTextColor.GRAY)).append(Component.space());

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        event.quitMessage(symbol.append(Component.text(event.getPlayer().getName() + " Left the game")));
        plugin.getGameManager().removePlayer(event.getPlayer().getUniqueId());
    }
}
