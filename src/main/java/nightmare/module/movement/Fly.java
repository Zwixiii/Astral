package nightmare.module.movement;

import nightmare.event.EventTarget;
import nightmare.event.impl.EventUpdate;
import nightmare.module.Category;
import nightmare.module.Module;

public class Fly extends Module{

	public Fly() {
		super("Fly", 0, Category.MOVEMENT);
		
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
