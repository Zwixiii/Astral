package moonlight.module.combat;

import moonlight.Moonlight;
import moonlight.event.EventTarget;
import moonlight.event.impl.EventUpdate;
import moonlight.module.Category;
import moonlight.module.Module;
import moonlight.settings.Setting;
import net.minecraft.util.EnumChatFormatting;

public class Reach extends Module{

	public Reach() {
		super("Reach", 0, Category.COMBAT);
		
		Moonlight.instance.settingsManager.rSetting(new Setting("Range", this, 3.5D, 3.0D, 6.0D, false));
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		this.setDisplayName("Reach " + EnumChatFormatting.GRAY + Moonlight.instance.settingsManager.getSettingByName(Moonlight.instance.moduleManager.getModuleByName("Reach"), "Range").getValDouble());
	}
	
	public static double getMaxRange(){
        double range = Moonlight.instance.settingsManager.getSettingByName(Moonlight.instance.moduleManager.getModuleByName("Reach"), "Range").getValDouble();
        double buildRange = range = 4.5;

        if(range > buildRange) {
        	return range;
        }else {
        	return buildRange;
        }
	}
}