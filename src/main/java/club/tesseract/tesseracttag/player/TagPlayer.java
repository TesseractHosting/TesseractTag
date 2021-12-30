package club.tesseract.tesseracttag.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.UUID;

public class TagPlayer {

    public static TagPlayer create(Player player){
        return new TagPlayer(player.getUniqueId());
    }

    public final static int cooldown = 2;

    private final UUID uniqueId;
    private long timestamp;
    private boolean hunter;
    private boolean dead;

    public TagPlayer(UUID uniqueId){
        this.uniqueId = uniqueId;
        this.hunter = false;
        this.dead = false;
        this.timestamp = Instant.now().getEpochSecond();
    }

    public TagPlayer(UUID uniqueId, boolean dead){
        this.uniqueId = uniqueId;
        this.hunter = false;
        this.dead = dead;
        this.timestamp = Instant.now().getEpochSecond();
    }

    public Player getPlayer(){
        return Bukkit.getPlayer(this.uniqueId);
    }
    public OfflinePlayer getOfflinePlayer(){
        return Bukkit.getOfflinePlayer(this.uniqueId);
    }

    public boolean isDead() {
        return dead;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public long getCooldown(){
        return timestamp+cooldown - Instant.now().getEpochSecond();
    }

    public boolean onCoolDown(){
        return timestamp+cooldown > Instant.now().getEpochSecond();
    }

    public void setHunter(boolean hunter) {
        this.hunter = hunter;
        PlayerInventory inv = getPlayer().getInventory();
        timestamp =  Instant.now().getEpochSecond();
        changeName();
        if(hunter){
            inv.setHelmet(new ItemStack(Material.RED_WOOL));
            inv.setChestplate(new ItemStack(Material.NETHERITE_CHESTPLATE));
            inv.setLeggings(new ItemStack(Material.NETHERITE_LEGGINGS));
            inv.setBoots(new ItemStack(Material.NETHERITE_BOOTS));
        }else{
            ItemStack air = new ItemStack(Material.AIR);
            inv.setHelmet(air);
            inv.setChestplate(air);
            inv.setLeggings(air);
            inv.setBoots(air);
        }
    }

    public boolean isHunter() {
        return hunter;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }


    public void changeName() {
        NamedTextColor colour = isHunter() ? NamedTextColor.RED : NamedTextColor.GREEN;
        ChatColor chatColour = isHunter() ? ChatColor.RED : ChatColor.GREEN;
        Player player = getPlayer();
        String name = ChatColor.stripColor(player.getName());
        player.customName(Component.text(name, colour));
        player.playerListName(Component.text(name, colour));
        player.displayName(Component.text(name, colour));
        if((chatColour+name).length()<=16){
            name = chatColour + name;
        }
        try {
            player.getPlayerProfile().setName(name);
            Object profile = player.getClass().getMethod("getProfile").invoke(player);
            Field ff = profile.getClass().getDeclaredField("name");
            ff.setAccessible(true);
            ff.set(profile, name);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.hidePlayer(player);
                p.showPlayer(player);
            }
        }catch( Exception e){
            e.printStackTrace();
        }
    }
}
