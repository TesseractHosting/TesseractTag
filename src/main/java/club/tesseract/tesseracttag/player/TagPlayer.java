package club.tesseract.tesseracttag.player;

import java.util.UUID;

public class TagPlayer {

    private final UUID uniqueId;
    private boolean hunter;

    public TagPlayer(UUID uniqueId){
        this.uniqueId = uniqueId;
        this.hunter = false;
    }

    public void setHunter(boolean hunter) {
        this.hunter = hunter;
    }

    public boolean isHunter() {
        return hunter;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }
}
