package nightmare.module.player;

import net.minecraft.network.play.client.C03PacketPlayer;
import nightmare.event.EventTarget;
import nightmare.event.impl.EventUpdate;
import nightmare.module.Category;
import nightmare.module.Module;

public class NoFall extends Module{

	public NoFall() {
		super("NoFall", 0, Category.PLAYER);
		
		this.setBlatantModule(true);
	}

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if(mc.thePlayer.fallDistance > 2F) {
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
        }
    }
}
