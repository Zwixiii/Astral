package nightmare.module.world;

import net.minecraft.potion.Potion;
import nightmare.Nightmare;
import nightmare.event.EventTarget;
import nightmare.event.impl.EventUpdate;
import nightmare.module.Category;
import nightmare.module.Module;
import nightmare.settings.Setting;

public class AntiInvisible extends Module{
	
	public AntiInvisible() {
		super("AntiInvisible", 0, Category.WORLD);
		
		Nightmare.instance.settingsManager.rSetting(new Setting("OnlyPotion", this, true));
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
        mc.theWorld.playerEntities.stream()
        .filter(player -> player != mc.thePlayer && player.isPotionActive(Potion.invisibility) && (Nightmare.instance.settingsManager.getSettingByName(this, "OnlyPotion").getValBoolean() ? true : player.isInvisible()))
        .forEach(player -> {
            player.removePotionEffect(Potion.invisibility.getId());
            player.setInvisible(false);
        });
	}
}