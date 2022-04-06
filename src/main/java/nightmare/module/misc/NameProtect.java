package nightmare.module.misc;

import nightmare.event.EventTarget;
import nightmare.event.impl.EventText;
import nightmare.module.Category;
import nightmare.module.Module;

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