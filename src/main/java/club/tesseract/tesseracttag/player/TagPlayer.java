package club.tesseract.tesseracttag.player;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public class TagPlayer {

    public static TagPlayer create(Player player){
        return new TagPlayer(player.getUniqueId());
    }

    private final UUID uniqueId;
    private long timestamp;
    private boolean hunter;

    public TagPlayer(UUID uniqueId){
        this.uniqueId = uniqueId;
        this.hunter = false;
    }

    public Player getPlayer(){
        return Bukkit.getPlayer(this.uniqueId);
    }
    public OfflinePlayer getOfflinePlayer(){
        return Bukkit.getOfflinePlayer(this.uniqueId);
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

    public void setHunter(boolean hunter) {
        this.hunter = hunter;
        PlayerInventory inv = getPlayer().getInventory();
        timestamp =  Instant.now().getEpochSecond();
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
}
