package moonlight.module.misc;

import moonlight.event.EventTarget;
import moonlight.event.impl.EventText;
import moonlight.module.Category;
import moonlight.module.Module;

public class NameProtect extends Module{
	
	public NameProtect() {
		super("NameProtect", 0, Category.MISC);
	}
	
	@EventTarget
	public void onText(EventText event) {
		if(mc.thePlayer != null && mc.theWorld != null) {
			event.replace(mc.thePlayer.getName(), "You");
		}
	}
}