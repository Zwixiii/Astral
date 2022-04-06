package moonlight.mixin.mixins.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import moonlight.Moonlight;
import net.minecraft.entity.Entity;

@Mixin(Entity.class)
public class MixinEntity {

	@Overwrite
	public float getCollisionBorderSize() {
		return Moonlight.instance.moduleManager.getModuleByName("HitBox").isToggled() ? (float) Moonlight.instance.settingsManager.getSettingByName(Moonlight.instance.moduleManager.getModuleByName("HitBox"), "Size").getValDouble() : 0.1F;
	}
}
