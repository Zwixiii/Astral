package moonlight.module.misc;

import moonlight.Moonlight;
import moonlight.module.Category;
import moonlight.module.Module;
import moonlight.settings.Setting;

public class FPSBoost extends Module{

	public FPSBoost() {
		super("FPSBoost", 0, Category.MISC);
		
		Moonlight.instance.settingsManager.rSetting(new Setting("HideArmorStand", this, false));
		Moonlight.instance.settingsManager.rSetting(new Setting("HideBat", this, false));
	}

}
