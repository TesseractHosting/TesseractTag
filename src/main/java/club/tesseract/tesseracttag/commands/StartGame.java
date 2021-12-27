package club.tesseract.tesseracttag.commands;

import club.tesseract.tesseracttag.TesseractTag;
import club.tesseract.tesseracttag.commands.utils.ShadowCommand;
import club.tesseract.tesseracttag.commands.utils.ShadowCommandInfo;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@ShadowCommandInfo(name = "startGame", permission = "tesseracttag.op")
public class StartGame extends ShadowCommand {

    private TesseractTag plugin;

    public StartGame(TesseractTag plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args){
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setGameMode(GameMode.SPECTATOR);
        }
        plugin.getRoundManager().startRound();

    }
}
