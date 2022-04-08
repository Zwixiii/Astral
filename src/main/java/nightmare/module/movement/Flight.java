package nightmare.module.movement;

import nightmare.event.EventTarget;
import nightmare.event.impl.EventUpdate;
import nightmare.module.Category;
import nightmare.module.Module;

public class Flight extends Module{

	public Flight() {
		super("Flight", 0, Category.MOVEMENT);
		
		this.setBlatantModule(true);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		mc.thePlayer.capabilities.isFlying = true;
	}
	
	@Override
	public void onDisable() {
		
		if(mc.thePlayer == null || mc.theWorld == null) {
			return;
		}
		
		mc.thePlayer.capabilities.isFlying = false;
		super.onDisable();
	}
}
