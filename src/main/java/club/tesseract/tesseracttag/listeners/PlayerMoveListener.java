package club.tesseract.tesseracttag.listeners;

import club.tesseract.tesseracttag.TesseractTag;
import club.tesseract.tesseracttag.player.TagPlayer;
import club.tesseract.tesseracttag.utils.GameState;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener extends ShadowListener {

    private TesseractTag plugin;

    public PlayerMoveListener(TesseractTag plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        TagPlayer player = this.plugin.getGameManager().getPlayer(event.getPlayer().getUniqueId());
        if(player == null)return;
        if(player.isHunter() && plugin.getGameManager().getGameState() == GameState.FROZEN && event.hasChangedBlock()){
            event.setCancelled(true);
            player.getPlayer().sendMessage(ChatColor.DARK_GRAY + "You can't move yet!");
        }
    }

}
