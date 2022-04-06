package nightmare.module.render;

import nightmare.Nightmare;
import nightmare.module.Category;
import nightmare.module.Module;
import nightmare.settings.Setting;

public class MotionBlur extends Module{

	public MotionBlur() {
		super("MotionBlur", 0, Category.RENDER);
		
		Nightmare.instance.settingsManager.rSetting(new Setting("Amount", this, 0.5, 0.1, 0.85, false));
	}

}