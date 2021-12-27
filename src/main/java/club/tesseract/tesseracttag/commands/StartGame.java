package club.tesseract.tesseracttag.commands;

import club.tesseract.tesseracttag.TesseractTag;
import club.tesseract.tesseracttag.commands.utils.ShadowCommand;
import club.tesseract.tesseracttag.commands.utils.ShadowCommandInfo;
import org.bukkit.command.CommandSender;

@ShadowCommandInfo(name = "startGame", permission = "tesseracttag.op")
public class StartGame extends ShadowCommand {
    public StartGame(TesseractTag plugin) {
        super(plugin);
    }

    @Override
    public void execute(CommandSender sender, String[] args){

    }
}
