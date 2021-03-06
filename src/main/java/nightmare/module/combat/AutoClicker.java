package nightmare.module.combat;

import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import nightmare.Nightmare;
import nightmare.event.EventTarget;
import nightmare.event.impl.EventTick;
import nightmare.event.impl.EventUpdate;
import nightmare.module.Category;
import nightmare.module.Module;
import nightmare.settings.Setting;
import nightmare.utils.TimerUtils;

public class AutoClicker extends Module{

	private TimerUtils timer = new TimerUtils();
	
	public AutoClicker() {
		super("AutoClicker", 0, Category.COMBAT);
		
		Nightmare.instance.settingsManager.rSetting(new Setting("MinCPS", this, 12, 1, 20, false));
		Nightmare.instance.settingsManager.rSetting(new Setting("MaxCPS", this, 15, 1, 20, false));
		Nightmare.instance.settingsManager.rSetting(new Setting("Weapons Only", this, false));
		Nightmare.instance.settingsManager.rSetting(new Setting("JitterAim", this, false));
	}
	
	@EventTarget
	public void onTick(EventTick event) {
		if (mc.gameSettings.keyBindAttack.isKeyDown() && (!Nightmare.instance.settingsManager.getSettingByName(this, "Weapons Only").getValBoolean() || mc.thePlayer.getHeldItem() != null && (mc.thePlayer.getHeldItem().getItem() instanceof ItemTool || mc.thePlayer.getHeldItem().getItem() instanceof ItemSword))) {
            if (timer.delay(1000 / ThreadLocalRandom.current().nextInt((int) Nightmare.instance.settingsManager.getSettingByName(this, "MinCPS").getValDouble(), (int) Nightmare.instance.settingsManager.getSettingByName(this, "MaxCPS").getValDouble() + 1))) {
            	KeyBinding.onTick(mc.gameSettings.keyBindAttack.getKeyCode());
                timer.reset();
            }
        }
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		
		if(mc.thePlayer == null) {
			return;
		}
		
		if(Nightmare.instance.settingsManager.getSettingByName(this, "JitterAim").getValBoolean()) {
			if (mc.gameSettings.keyBindAttack.isKeyDown() && (!Nightmare.instance.settingsManager.getSettingByName(this, "Weapons Only").getValBoolean() || mc.thePlayer.getHeldItem() != null && (mc.thePlayer.getHeldItem().getItem() instanceof ItemTool || mc.thePlayer.getHeldItem().getItem() instanceof ItemSword))) {
				mc.thePlayer.rotationPitch =  mc.thePlayer.rotationPitch + ThreadLocalRandom.current().nextInt(-1, 2);
			}
		}
	}
}
