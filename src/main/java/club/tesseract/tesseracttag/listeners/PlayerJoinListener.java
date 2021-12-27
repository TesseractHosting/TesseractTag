package club.tesseract.tesseracttag.listeners;

import club.tesseract.tesseracttag.TesseractTag;
import club.tesseract.tesseracttag.player.TagPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener extends ShadowListener{

    private final TesseractTag plugin;
    public PlayerJoinListener(TesseractTag plugin){
        super();
        this.plugin = plugin;
        Bukkit.getOnlinePlayers().forEach(player -> plugin.getGameManager().addPlayer(TagPlayer.create(player)));
    }

    private final static Component symbol = Component.text("[", NamedTextColor.GRAY).append(Component.text("!!", NamedTextColor.GREEN)).append(
            Component.text("]", NamedTextColor.GRAY)).append(Component.space());

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        event.joinMessage(symbol.append(Component.text(event.getPlayer().getName()+" Joined the game")));
        plugin.getGameManager().addPlayer(TagPlayer.create(event.getPlayer()));
    }
}
