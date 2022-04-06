package moonlight.module.combat;

import moonlight.Moonlight;
import moonlight.event.EventTarget;
import moonlight.event.impl.EventUpdate;
import moonlight.module.Category;
import moonlight.module.Module;
import moonlight.settings.Setting;
import net.minecraft.util.EnumChatFormatting;

public class HitBox extends Module{

	public HitBox() {
		super("HitBox", 0, Category.COMBAT);
		
		Moonlight.instance.settingsManager.rSetting(new Setting("Size", this, 0.1, 0.1, 1.0, false));
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		float size = (float) Moonlight.instance.settingsManager.getSettingByName(this, "Size").getValDouble();
		
		this.setDisplayName(this.getName() + " " + EnumChatFormatting.GRAY + size);
	}

}