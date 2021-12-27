package club.tesseract.tesseracttag.commands.utils;


import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class DynamicCommand extends Command {

    ShadowCommand command;

    public DynamicCommand(ShadowCommand command){
        super(command.getCommandInfo().name());
        this.command = command;
        setAliases(command.getAliases());
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, String[] args) {
        return command.onCommand(sender,this,commandLabel,args);
    }
}
