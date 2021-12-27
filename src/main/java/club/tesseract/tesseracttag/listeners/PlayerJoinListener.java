package club.tesseract.tesseracttag.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener extends ShadowListener{

    private final static Component symbol = Component.text("[", NamedTextColor.GRAY).append(Component.text("!!", NamedTextColor.GREEN)).append(
            Component.text("]", NamedTextColor.GRAY)).append(Component.space());

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        event.joinMessage(symbol.append(Component.text(event.getPlayer().getName()+" Joined the game")));
    }
}
