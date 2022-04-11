package nightmare.module.world;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import nightmare.Nightmare;
import nightmare.event.EventTarget;
import nightmare.event.impl.EventTick;
import nightmare.mixin.mixins.accessor.MinecraftAccessor;
import nightmare.module.Category;
import nightmare.module.Module;
import nightmare.settings.Setting;

public class Timer extends Module{

	private MinecraftAccessor mcAccessor = (MinecraftAccessor) Minecraft.getMinecraft();
	
	public Timer() {
		super("Timer", 0, Category.WORLD);
		
		Nightmare.instance.settingsManager.rSetting(new Setting("Timer", this, 1.50, 0.10, 3, false));
	}
	
	@EventTarget
	public void onTick(EventTick event) {
		if(!(mc.currentScreen instanceof Gui)) {
			mcAccessor.timer().timerSpeed = (float) Nightmare.instance.settingsManager.getSettingByName(this, "Timer").getValDouble();
		}else {
			mcAccessor.timer().timerSpeed = 1.0F;
		}
	}

	@Override
	public void onDisable() {
		
		if(mc.thePlayer == null || mc.theWorld == null) {
			return;
		}
		
		mcAccessor.timer().timerSpeed = 1.0F;
		
		super.onDisable();
	}
}