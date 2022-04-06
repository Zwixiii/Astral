package nightmare.module.combat;

import net.minecraft.util.EnumChatFormatting;
import nightmare.Nightmare;
import nightmare.event.EventTarget;
import nightmare.event.impl.EventUpdate;
import nightmare.module.Category;
import nightmare.module.Module;
import nightmare.settings.Setting;

public class Reach extends Module{

	public Reach() {
		super("Reach", 0, Category.COMBAT);
		
		Nightmare.instance.settingsManager.rSetting(new Setting("Range", this, 3.5D, 3.0D, 6.0D, false));
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		this.setDisplayName("Reach " + EnumChatFormatting.GRAY + Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("Reach"), "Range").getValDouble());
	}
	
	public static double getMaxRange(){
        double range = Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("Reach"), "Range").getValDouble();
        double buildRange = range = 4.5;

        if(range > buildRange) {
        	return range;
        }else {
        	return buildRange;
        }
	}
}