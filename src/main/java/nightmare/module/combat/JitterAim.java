package nightmare.module.combat;

import java.util.concurrent.ThreadLocalRandom;

import nightmare.Nightmare;
import nightmare.event.EventTarget;
import nightmare.event.impl.EventUpdate;
import nightmare.module.Category;
import nightmare.module.Module;
import nightmare.settings.Setting;

public class JitterAim extends Module{
	
	public JitterAim() {
		super("JitterAim", 0, Category.COMBAT);
		
		Nightmare.instance.settingsManager.rSetting(new Setting("ClickJitterAim", this, false));
		Nightmare.instance.settingsManager.rSetting(new Setting("Silent", this, false));
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		
		if(mc.thePlayer == null) {
			return;
		}
		
		if (Nightmare.instance.settingsManager.getSettingByName(this, "ClickJitterAim").getValBoolean() && !mc.gameSettings.keyBindAttack.isKeyDown()) {
			return;
		}
		
		mc.thePlayer.rotationPitch =  mc.thePlayer.rotationPitch + ThreadLocalRandom.current().nextInt(-1, 2);
	}
}
