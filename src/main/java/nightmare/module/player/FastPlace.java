package nightmare.module.player;

import nightmare.Nightmare;
import nightmare.module.Category;
import nightmare.module.Module;
import nightmare.settings.Setting;

public class FastPlace extends Module{

	public FastPlace() {
		super("FastPlace", 0, Category.PLAYER);
		
		Nightmare.instance.settingsManager.rSetting(new Setting("Delay", this, 1, 0, 4, true));
	}

}
