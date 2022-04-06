package moonlight.module.movement;

import moonlight.Moonlight;
import moonlight.module.Category;
import moonlight.module.Module;
import moonlight.settings.Setting;

public class VClip extends Module{

	public VClip() {
		super("VClip", 0, Category.MOVEMENT);
		
		Moonlight.instance.settingsManager.rSetting(new Setting("Distance", this, -3, -5, 5, true));
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		if(!(Moonlight.instance.settingsManager.getSettingByName(this, "Distance").getValDouble() == 0)) {
			mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + Moonlight.instance.settingsManager.getSettingByName(this, "Distance").getValDouble(), mc.thePlayer.posZ);
			toggle();
		}else {
			this.setToggled(false);
		}
	}
}