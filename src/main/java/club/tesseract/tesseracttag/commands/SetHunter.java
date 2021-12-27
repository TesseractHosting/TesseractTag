package club.tesseract.tesseracttag.commands;

import club.tesseract.tesseracttag.TesseractTag;
import club.tesseract.tesseracttag.commands.utils.ShadowCommand;
import club.tesseract.tesseracttag.commands.utils.ShadowCommandInfo;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


@ShadowCommandInfo(name = "setHunter", permission = "tesseracttag.op")
public class SetHunter extends ShadowCommand {

    public SetHunter(TesseractTag plugin) {
        super(plugin);
    }

    @Override
    public void execute(CommandSender player, String[] args) {
        if(args.length <= 0){
            player.sendMessage(Component.text("Please tell me which player to set to a hunter"));
            return;
        }
        String args1 = args[0];
        Player hunter = Bukkit.getPlayer(args1);
        if(hunter == null){
            player.sendMessage("Unknown player");
            return;
        }
        plugin.getGameManager().setHunter(hunter.getUniqueId());
        player.sendMessage("Hunter set");


    }
}
