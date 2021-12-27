package club.tesseract.tesseracttag;

import club.tesseract.tesseracttag.commands.utils.DynamicCommand;
import club.tesseract.tesseracttag.commands.utils.ShadowCommand;
import club.tesseract.tesseracttag.listeners.*;
import club.tesseract.tesseracttag.tasks.ActionBarTask;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.util.Locale;

public class TesseractTag extends JavaPlugin {

    static TesseractTag plugin = null;
    private GameManager gameManager;
    private RoundManager roundManager;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        roundManager = new RoundManager(this);
        gameManager = new GameManager();
        registerEvents();
        registerCommands();
        ActionBarTask.start();
        getLogger().info("Plugin Enabled");
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
        gameManager.setHunter(null);
        ActionBarTask.shutdown();
        getLogger().info("Plugin Disabled");
    }


    private void registerEvents(){
        new PlayerJoinListener(this);
        new PlayerQuitListener(this);
        new PlayerHurtListener(this);
        new PlayerDeathListener(this);
        new PlayerMoveListener(this);
        new PlayerDropItemListener();
    }

    private void registerCommands(){
        String packageName = getClass().getPackage().getName();
        for (Class<? extends ShadowCommand> clazz : new Reflections(packageName+".commands").getSubTypesOf(ShadowCommand.class)){
            try{
                ShadowCommand cmd = clazz.getDeclaredConstructor(this.getClass()).newInstance(this);
                String cmdName = cmd.getCommandInfo().name();
                if(cmdName.isEmpty())continue;
                PluginCommand command = getCommand(cmdName);
                if(command == null){
                    getLogger().info("Injecting Command: "+cmdName);
                    if(!addCommandToCommandMap(cmd)){
                        getLogger().info("Failed to add "+cmdName+" to command map");
                        continue;
                    }
                    command = getCommand(cmdName);
                    if(command==null){
                        getLogger().info("Command "+cmdName+" failed to inject");
                        continue;
                    }
                }
                getLogger().info("Registering command "+cmdName);
                command.setExecutor(cmd);
                command.setTabCompleter(cmd);
                if(cmd.getCommandInfo().permission().isEmpty())
                    command.setPermission(null);
                else
                    command.setPermission(cmd.getCommandInfo().permission());
                command.setDescription(cmd.getCommandInfo().description());
                command.setUsage(cmd.getCommandInfo().usage());
                command.setAliases(cmd.getAliases());
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    private boolean addCommandToCommandMap(ShadowCommand command) {
        return Bukkit.getCommandMap().register(command.getCommandInfo().name(),getName().toLowerCase(Locale.ROOT), new DynamicCommand(command));
    }
    public GameManager getGameManager() {
        return gameManager;
    }

    public RoundManager getRoundManager() {
        return roundManager;
    }

    public static TesseractTag getPlugin() {
        return plugin;
    }
}
