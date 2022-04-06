package moonlight.module.render;

import moonlight.Moonlight;
import moonlight.module.Category;
import moonlight.module.Module;
import moonlight.settings.Setting;

public class Chams extends Module{

	public Chams() {
		super("Chams", 0, Category.RENDER);
		
		Moonlight.instance.settingsManager.rSetting(new Setting("Player", this, false));
		Moonlight.instance.settingsManager.rSetting(new Setting("Chest", this, false));
	}

}