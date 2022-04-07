package nightmare.module.player;

import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import nightmare.event.EventTarget;
import nightmare.event.impl.EventSlowDown;
import nightmare.event.impl.EventUpdate;
import nightmare.module.Category;
import nightmare.module.Module;

public class NoSlow extends Module{

	public NoSlow() {
		super("NoSlow", 0, Category.PLAYER);
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
        if(mc.thePlayer.isUsingItem()) {
            if(mc.thePlayer.ticksExisted % 3 == 0) {
                mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            }else{
                mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
            }
        }
	}
	
	@EventTarget
	public void onSlowDown(EventSlowDown event) {
		event.setCancelled(true);
	}
}
