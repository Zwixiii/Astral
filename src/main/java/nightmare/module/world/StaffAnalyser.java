package nightmare.module.world;

import net.minecraft.network.play.server.S02PacketChat;
import nightmare.Nightmare;
import nightmare.event.EventTarget;
import nightmare.event.impl.EventPreMotionUpdate;
import nightmare.event.impl.EventReceivePacket;
import nightmare.event.impl.EventRespawn;
import nightmare.module.Category;
import nightmare.module.Module;
import nightmare.settings.Setting;
import nightmare.utils.ServerUtils;
import nightmare.utils.TimerUtils;
import nightmare.utils.staffanalyser.CheckThread;

public class StaffAnalyser extends Module{

    public static String key = null;
    private TimerUtils timer = new TimerUtils();
    private CheckThread thread;
    
	public StaffAnalyser() {
		super("StaffAnalyser", 0, Category.WORLD);
		
		thread = new CheckThread();
		thread.start();
		
		Nightmare.instance.settingsManager.rSetting(new Setting("Delay", this, 60.0, 10.0, 120.0, true));
	}
	
    @EventTarget
    public void onPreUpdate(final EventPreMotionUpdate event) {
        if (ServerUtils.isOnHypixel() && this.timer.delay(3000.0) && StaffAnalyser.key == null) {
            StaffAnalyser.mc.thePlayer.sendChatMessage("/api new");
            this.timer.reset();
        }
    }
    
    @EventTarget
    public void onReceivePacket(final EventReceivePacket event) {
        if (event.getPacket() instanceof S02PacketChat) {
            final S02PacketChat chatPacket = (S02PacketChat)event.getPacket();
            final String chatMessage = chatPacket.getChatComponent().getUnformattedText();
            if (chatMessage.matches("Your new API key is ........-....-....-....-............")) {
                event.setCancelled(true);
                StaffAnalyser.key = chatMessage.replace("Your new API key is ", "");
            }
        }
    }
    
    @EventTarget
    public void onRespawn(final EventRespawn event) {
        StaffAnalyser.key = null;
    }
}
