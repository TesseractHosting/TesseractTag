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

    private final UUID uniqueId;
    private long timestamp;
    private boolean hunter;
    private boolean dead;
    private boolean frozen;

    public TagPlayer(UUID uniqueId){
        this.uniqueId = uniqueId;
        this.hunter = false;
        this.dead = false;
    }

    public TagPlayer(UUID uniqueId, boolean dead){
        this.uniqueId = uniqueId;
        this.hunter = false;
        this.dead = dead;
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
        return timestamp+5 - Instant.now().getEpochSecond();
    }

    public boolean onCoolDown(){
        return timestamp+5 > Instant.now().getEpochSecond();
    }

    public boolean isFrozen() {return this.frozen ;}

    public void setFrozen(boolean frozen) {this.frozen = frozen;}

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
        try {
            player.getPlayerProfile().setName(chatColour+name);
            Object profile = player.getClass().getMethod("getProfile").invoke(player);
            Field ff = profile.getClass().getDeclaredField("name");
            ff.setAccessible(true);
            ff.set(profile, chatColour+name);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.hidePlayer(player);
                p.showPlayer(player);
            }
        }catch( Exception e){
            e.printStackTrace();
        }
    }
}
