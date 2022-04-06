package moonlight.module.world;

import moonlight.Moonlight;
import moonlight.event.EventTarget;
import moonlight.event.impl.EventUpdate;
import moonlight.module.Category;
import moonlight.module.Module;
import moonlight.settings.Setting;
import net.minecraft.potion.Potion;

public class AntiInvisible extends Module{
	
	public AntiInvisible() {
		super("AntiInvisible", 0, Category.WORLD);
		
		Moonlight.instance.settingsManager.rSetting(new Setting("OnlyPotion", this, true));
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
        mc.theWorld.playerEntities.stream()
        .filter(player -> player != mc.thePlayer && player.isPotionActive(Potion.invisibility) && (Moonlight.instance.settingsManager.getSettingByName(this, "OnlyPotion").getValBoolean() ? true : player.isInvisible()))
        .forEach(player -> {
            player.removePotionEffect(Potion.invisibility.getId());
            player.setInvisible(false);
        });
	}
}