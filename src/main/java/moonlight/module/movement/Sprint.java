package moonlight.module.movement;

import moonlight.event.EventTarget;
import moonlight.event.impl.EventUpdate;
import moonlight.module.Category;
import moonlight.module.Module;
import net.minecraft.client.settings.KeyBinding;

public class Sprint extends Module{

	public Sprint() {
		super("Sprint", 0, Category.MOVEMENT);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), false);
	}
}
