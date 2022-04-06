package nightmare.module.combat;

import net.minecraft.util.EnumChatFormatting;
import nightmare.Nightmare;
import nightmare.event.EventTarget;
import nightmare.event.impl.EventUpdate;
import nightmare.module.Category;
import nightmare.module.Module;
import nightmare.settings.Setting;

public class HitBox extends Module{

	public HitBox() {
		super("HitBox", 0, Category.COMBAT);
		
		Nightmare.instance.settingsManager.rSetting(new Setting("Size", this, 0.1, 0.1, 1.0, false));
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		float size = (float) Nightmare.instance.settingsManager.getSettingByName(this, "Size").getValDouble();
		
		this.setDisplayName(this.getName() + " " + EnumChatFormatting.GRAY + size);
	}

}