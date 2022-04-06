package moonlight.module.world;

import moonlight.Moonlight;
import moonlight.module.Category;
import moonlight.module.Module;
import moonlight.settings.Setting;

public class TimeChanger extends Module{

	public TimeChanger() {
		super("TimeChanger", 0, Category.WORLD);
		
		Moonlight.instance.settingsManager.rSetting(new Setting("Time", this, 0.0D, 0.0D, 15000.0D, true));
	}
}