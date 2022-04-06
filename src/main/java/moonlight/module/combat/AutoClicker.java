package moonlight.module.combat;

import java.util.concurrent.ThreadLocalRandom;

import moonlight.Moonlight;
import moonlight.event.EventTarget;
import moonlight.event.impl.EventTick;
import moonlight.module.Category;
import moonlight.module.Module;
import moonlight.settings.Setting;
import moonlight.utils.TimerUtils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

public class AutoClicker extends Module{

	private TimerUtils timer = new TimerUtils();
	
	public AutoClicker() {
		super("AutoClicker", 0, Category.COMBAT);
		
		Moonlight.instance.settingsManager.rSetting(new Setting("MinCPS", this, 12, 1, 20, false));
		Moonlight.instance.settingsManager.rSetting(new Setting("MaxCPS", this, 15, 1, 20, false));
		Moonlight.instance.settingsManager.rSetting(new Setting("Weapons Only", this, false));
	}
	
	@EventTarget
	public void onTick(EventTick event) {
		if (mc.gameSettings.keyBindAttack.isKeyDown() && (!Moonlight.instance.settingsManager.getSettingByName(this, "Weapons Only").getValBoolean() || mc.thePlayer.getHeldItem() != null && (mc.thePlayer.getHeldItem().getItem() instanceof ItemTool || mc.thePlayer.getHeldItem().getItem() instanceof ItemSword))) {
            if (timer.delay(1000 / ThreadLocalRandom.current().nextInt((int) Moonlight.instance.settingsManager.getSettingByName(this, "MinCPS").getValDouble(), (int) Moonlight.instance.settingsManager.getSettingByName(this, "MaxCPS").getValDouble() + 1))) {
            	KeyBinding.onTick(mc.gameSettings.keyBindAttack.getKeyCode());
                timer.reset();
            }
        }
	}

}
