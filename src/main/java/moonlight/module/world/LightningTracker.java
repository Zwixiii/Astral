package moonlight.module.world;

import moonlight.event.EventTarget;
import moonlight.event.impl.EventReceivePacket;
import moonlight.module.Category;
import moonlight.module.Module;
import moonlight.utils.ChatUtils;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.util.EnumChatFormatting;

public class LightningTracker extends Module{

	public LightningTracker() {
		super("LightningTracker", 0, Category.WORLD);
	}
	
	@EventTarget
	public void onPacket(EventReceivePacket e) {
		 if (e.getPacket() instanceof S29PacketSoundEffect) {
             S29PacketSoundEffect packet = (S29PacketSoundEffect) e.getPacket();

             if (packet.getSoundName().equals("ambient.weather.thunder")) {
                 int x = (int) packet.getX(), y = (int) packet.getY(), z = (int) packet.getZ();
                 ChatUtils.sendPrivateChatMessage(EnumChatFormatting.YELLOW + "Lightning detected " + EnumChatFormatting.RESET + "X: " + x + " Y: " + y + " Z:" + z);
             }
         }
	}
}