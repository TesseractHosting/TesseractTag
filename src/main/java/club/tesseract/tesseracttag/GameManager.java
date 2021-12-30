package club.tesseract.tesseracttag;

import club.tesseract.tesseracttag.player.TagPlayer;
import club.tesseract.tesseracttag.utils.GameState;
import club.tesseract.tesseracttag.tasks.GlobalTaskTimer;
import club.tesseract.tesseracttag.utils.ShadowScoreboard;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;



public class GameManager {

    private final static Location spawn = new Location(Bukkit.getWorlds().get(0), 0, 63, 0);
    private final HashMap<UUID, TagPlayer> players = new HashMap<>();
    private GameState gameState = GameState.IDLE;
    private RoundManager roundManager;
    private final ItemStack nameTag;
    private UUID hunterId = null;

    public GameManager(){
        roundManager = new RoundManager(this);
        nameTag = new ItemStack(Material.NAME_TAG);
        ItemMeta meta = nameTag.getItemMeta();
        meta.displayName(Component.text("TAG", NamedTextColor.RED));
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Easter Egg", NamedTextColor.WHITE));
        lore.add(Component.text(";)", NamedTextColor.WHITE));
        meta.lore(lore);
        nameTag.setItemMeta(meta);
    }


    public GameState getGameState(){
        return gameState;
    }

    public void setRunning(){
        gameState = GameState.PLAYING;
    }

    public Player getHunter(){
        if(hunterId != null){
            return Bukkit.getPlayer(hunterId);
        }
        return null;
    }

    public ArrayList<TagPlayer> getPlayers(){
        return new ArrayList<TagPlayer>(players.values());
    }

    public TagPlayer getPlayer(UUID player){
        return players.get(player);
    }

    public void addPlayer(TagPlayer player){
        Player bukkitPlayer = player.getPlayer();
        bukkitPlayer.getInventory().clear();
        bukkitPlayer.getActivePotionEffects().forEach(effect -> {
            bukkitPlayer.removePotionEffect(effect.getType());
        });
        bukkitPlayer.setGameMode(GameMode.ADVENTURE);
        if(gameState != GameState.IDLE){
            bukkitPlayer.setAllowFlight(true);
            bukkitPlayer.setFlying(true);
            Bukkit.getOnlinePlayers().forEach(other-> other.hidePlayer(TesseractTag.getPlugin(), bukkitPlayer));
        }else{
            bukkitPlayer.setAllowFlight(false);
            bukkitPlayer.setFlying(false);
            Bukkit.getOnlinePlayers().forEach(other->other.showPlayer(TesseractTag.getPlugin(), bukkitPlayer));
            players.put(player.getUniqueId(), player);
        }
        bukkitPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(1);
        bukkitPlayer.setSaturation(1000);
        player.changeName();
        ShadowScoreboard.setScoreboard(bukkitPlayer);
    }

    public void removePlayer(UUID uuid){
        players.remove(uuid);
        Player player = Bukkit.getPlayer(uuid);
        if(player != null){// Player removed from list but still in game.
            player.setAllowFlight(true);
            player.setFlying(true);
            Bukkit.getOnlinePlayers().forEach(other-> other.hidePlayer(TesseractTag.getPlugin(), player));
        }
    }

    public void setHunter(UUID uuid){
        if(uuid == null){
            if(hunterId != null){
                if(players.get(hunterId) != null)
                    players.get(hunterId).setHunter(false);
            }
            return;
        }
        TagPlayer tagged = players.get(uuid);
        long cooldown = tagged.getTimestamp()+TagPlayer.cooldown;
        long now = Instant.now().getEpochSecond();
        if(cooldown > now){
            long delta = cooldown - now;
            if(players.get(hunterId) != null)
                players.get(hunterId).getPlayer().sendMessage(Component.text("Wait "+delta+" seconds before tagging"));
            return;
        }
        if(hunterId != null){
            if(players.get(hunterId) != null)
                players.get(hunterId).setHunter(false);
        }
        hunterId = uuid;
        tagged.setHunter(true);
        Player player = tagged.getPlayer();

        player.sendMessage("Tag! you are it.");
        player.getInventory().addItem(nameTag);

        Bukkit.broadcast(Component.text(ChatColor.RED + player.getName()+" is now it."));
        ShadowScoreboard.sendScoreboardUpdate();
    }

    public void endGame(){
        gameState = GameState.IDLE;
        setHunter(null);
        players.clear();
        Bukkit.getOnlinePlayers().forEach(player -> {
            addPlayer(TagPlayer.create(player));
            player.teleport(spawn);
            player.getInventory().clear();
            player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));

        });
        ShadowScoreboard.sendScoreboardUpdate();
    }

    public void startGame(){
        if(gameState != GameState.IDLE)return;
        if(players.size() <= 1){
            Bukkit.broadcast(Component.text("cannot start game with only one person"));
        }
        pickNextHunter();
        gameState = GameState.FROZEN;
        this.getHunter().setAllowFlight(true);
        this.getHunter().setFlying(true);
        Bukkit.getOnlinePlayers().forEach(player->{
            player.teleport(spawn);
        });
        roundManager.startRound(true);

    }
    public void pickNextHunter(){
        Random rand = new Random();
        int index = rand.nextInt(players.size());
        TagPlayer newHunter = getPlayers().get(index);
        setHunter(newHunter.getUniqueId());
    }
}
