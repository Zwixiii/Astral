package nightmare.module.combat;

import java.util.ArrayList;

import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import nightmare.Nightmare;
import nightmare.event.EventTarget;
import nightmare.event.impl.EventReceivePacket;
import nightmare.event.impl.EventUpdate;
import nightmare.module.Category;
import nightmare.module.Module;
import nightmare.settings.Setting;
import nightmare.utils.ServerUtils;

public class AntiBot extends Module{

	public AntiBot() {
		super("AntiBot", 0, Category.COMBAT);
		
		ArrayList<String> options = new ArrayList<>();
		
		options.add("Normal");
		options.add("Hypixel");
		options.add("Advanced");
		
		Nightmare.instance.settingsManager.rSetting(new Setting("Mode", this, "Normal", options));
	}
	
    @EventTarget
    public void onUpdate(EventUpdate event) {
    	
    	String mode = Nightmare.instance.settingsManager.getSettingByName(this, "Mode").getValString();
    	
    	if(mode.equals("Normal")) {
            for(Object entity : mc.theWorld.loadedEntityList) {
                if(((Entity)entity).isInvisible() && entity != mc.thePlayer) {
                    mc.theWorld.removeEntity((Entity)entity);
                }
            }
    	}
    	
    	if(mode.equals("Hypixel")) {
            for(Entity entity : mc.theWorld.loadedEntityList) {
                if((entity.isInvisible() && entity != mc.thePlayer) && !this.isInTablist(entity)) {
                    mc.theWorld.removeEntity(entity);
                }
            }
    	}
    }
    
    @EventTarget
    public void onReceivePacket(EventReceivePacket event) {
    	
        String mode = Nightmare.instance.settingsManager.getSettingByName(this, "Mode").getValString();

        if(mode.equalsIgnoreCase("Advanced") && event.getPacket() instanceof S0CPacketSpawnPlayer) {
            S0CPacketSpawnPlayer packet = (S0CPacketSpawnPlayer)event.getPacket();
            double posX = packet.getX() / 32D;
            double posY = packet.getY() / 32D;
            double posZ = packet.getZ() / 32D;

            double diffX = mc.thePlayer.posX - posX;
            double diffY = mc.thePlayer.posY - posY;
            double diffZ = mc.thePlayer.posZ - posZ;

            double dist = Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ);

            if(dist <= 17D && posX != mc.thePlayer.posX && posY != mc.thePlayer.posY && posZ != mc.thePlayer.posZ) {
                event.setCancelled(true);
            }
        }
    }
    
	public boolean isInTablist(Entity entity) {
		
		if(ServerUtils.isOnServer()) {
			for (Object item : mc.getNetHandler().getPlayerInfoMap()) {
				NetworkPlayerInfo playerInfo = (NetworkPlayerInfo) item;

				if (playerInfo != null && playerInfo.getGameProfile() != null && playerInfo.getGameProfile().getName().contains(entity.getName())) {
					return true;
				}
			}
		}
		return false;
	}
}