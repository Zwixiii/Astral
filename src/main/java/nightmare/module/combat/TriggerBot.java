package nightmare.module.combat;

import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import nightmare.Nightmare;
import nightmare.event.EventTarget;
import nightmare.event.impl.EventUpdate;
import nightmare.module.Category;
import nightmare.module.Module;
import nightmare.settings.Setting;
import nightmare.utils.TimerUtils;

public class TriggerBot extends Module{
	
	private TimerUtils timer = new TimerUtils();
	
	public TriggerBot() {
		super("TriggerBot", 0, Category.COMBAT);
		
		Nightmare.instance.settingsManager.rSetting(new Setting("MinCPS", this, 12, 1, 20, false));
		Nightmare.instance.settingsManager.rSetting(new Setting("MaxCPS", this, 15, 1, 20, false));
	}
	
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		
		if(!(mc.currentScreen instanceof Gui)) {
			if (timer.delay(1000 / ThreadLocalRandom.current().nextInt((int) Nightmare.instance.settingsManager.getSettingByName(this, "MinCPS").getValDouble(), (int) Nightmare.instance.settingsManager.getSettingByName(this, "MaxCPS").getValDouble() + 1))) {
				if(mc.objectMouseOver != null && mc.objectMouseOver != null && mc.objectMouseOver.entityHit instanceof EntityLivingBase) {
					mc.thePlayer.swingItem();
					mc.playerController.attackEntity(mc.thePlayer, mc.objectMouseOver.entityHit);
				}
			}
		}
	}
}
