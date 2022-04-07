package nightmare.module.movement;

import net.minecraft.client.settings.KeyBinding;
import nightmare.Nightmare;
import nightmare.event.EventTarget;
import nightmare.event.impl.EventUpdate;
import nightmare.module.Category;
import nightmare.module.Module;

public class Sprint extends Module{

	public Sprint() {
		super("Sprint", 0, Category.MOVEMENT);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(Nightmare.instance.moduleManager.getModuleByName("NoSlow").isToggled()) {
			if(mc.thePlayer.isUsingItem() && mc.thePlayer.moveForward > 0) {
				mc.thePlayer.setSprinting(true);
			}else {
				KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
			}
		}else {
			KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
		}
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), false);
	}
}
