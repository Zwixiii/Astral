package nightmare.module.movement;

import nightmare.Nightmare;
import nightmare.module.Category;
import nightmare.module.Module;
import nightmare.settings.Setting;

public class VClip extends Module{

	public VClip() {
		super("VClip", 0, Category.MOVEMENT);
		
		Nightmare.instance.settingsManager.rSetting(new Setting("Distance", this, -3, -5, 5, true));
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		if(!(Nightmare.instance.settingsManager.getSettingByName(this, "Distance").getValDouble() == 0)) {
			mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + Nightmare.instance.settingsManager.getSettingByName(this, "Distance").getValDouble(), mc.thePlayer.posZ);
			toggle();
		}else {
			this.setToggled(false);
		}
	}
}