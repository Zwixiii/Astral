package moonlight.module.render;

import moonlight.Moonlight;
import moonlight.module.Category;
import moonlight.module.Module;
import moonlight.settings.Setting;

public class Chat extends Module{
	
	public Chat() {
		super("Chat", 0, Category.RENDER);
		
		Moonlight.instance.settingsManager.rSetting(new Setting("SmoothChat", this, false));
		Moonlight.instance.settingsManager.rSetting(new Setting("ClearChat", this, false));
	}
}
