package moonlight.module.player;

import moonlight.Moonlight;
import moonlight.module.Category;
import moonlight.module.Module;
import moonlight.settings.Setting;

public class FastPlace extends Module{

	public FastPlace() {
		super("FastPlace", 0, Category.PLAYER);
		
		Moonlight.instance.settingsManager.rSetting(new Setting("Delay", this, 1, 0, 4, true));
	}

}
