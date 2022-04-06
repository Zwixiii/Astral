package nightmare.mixin.mixins.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.entity.Entity;
import nightmare.Nightmare;

@Mixin(Entity.class)
public class MixinEntity {

	@Overwrite
	public float getCollisionBorderSize() {
		return Nightmare.instance.moduleManager.getModuleByName("HitBox").isToggled() ? (float) Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("HitBox"), "Size").getValDouble() : 0.1F;
	}
}
