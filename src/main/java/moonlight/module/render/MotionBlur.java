package moonlight.module.render;

import moonlight.Moonlight;
import moonlight.module.Category;
import moonlight.module.Module;
import moonlight.settings.Setting;

public class MotionBlur extends Module{

	public MotionBlur() {
		super("MotionBlur", 0, Category.RENDER);
		
		Moonlight.instance.settingsManager.rSetting(new Setting("Amount", this, 0.5, 0.1, 0.85, false));
	}

}