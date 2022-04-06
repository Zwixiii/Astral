package moonlight.module.combat;

import moonlight.Moonlight;
import moonlight.event.EventTarget;
import moonlight.event.impl.EventUpdate;
import moonlight.module.Category;
import moonlight.module.Module;
import moonlight.settings.Setting;

public class Velocity extends Module{

	public Velocity() {
		super("Velocity", 0, Category.COMBAT);
		
		Moonlight.instance.settingsManager.rSetting(new Setting("Horizontal", this, 90, 0, 100, true));
		Moonlight.instance.settingsManager.rSetting(new Setting("Vertical", this, 90, 0, 100, true));
		Moonlight.instance.settingsManager.rSetting(new Setting("Chance", this, 50, 0, 100, true));
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		float horizontal = (float) Moonlight.instance.settingsManager.getSettingByName(this, "Horizontal").getValDouble();
		float vertical = (float) Moonlight.instance.settingsManager.getSettingByName(this, "Vertical").getValDouble();
		
		if (mc.thePlayer.hurtTime == mc.thePlayer.maxHurtTime && mc.thePlayer.maxHurtTime > 0) {
			if (Math.random() <= Moonlight.instance.settingsManager.getSettingByName(this, "Chance").getValDouble() / 100) {
				mc.thePlayer.motionX *= (float) horizontal / 100;
				mc.thePlayer.motionY *= (float) vertical / 100;
				mc.thePlayer.motionZ *= (float) horizontal / 100;
			}
		}
	}

}
