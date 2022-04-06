package nightmare.mixin.mixins.world;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.world.storage.WorldInfo;
import nightmare.Nightmare;

@Mixin(WorldInfo.class)
public class MixinWorldInfo {

	@Shadow
	private long worldTime;
	
	@Overwrite
    public void setWorldTime(long time)
    {
        this.worldTime = Nightmare.instance.moduleManager.getModuleByName("TimeChanger").isToggled() ? (long) Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("TimeChanger"), "Time").getValDouble() : time;
    }
}
