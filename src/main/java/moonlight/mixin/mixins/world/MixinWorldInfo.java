package moonlight.mixin.mixins.world;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import moonlight.Moonlight;
import net.minecraft.world.storage.WorldInfo;

@Mixin(WorldInfo.class)
public class MixinWorldInfo {

	@Shadow
	private long worldTime;
	
	@Overwrite
    public void setWorldTime(long time)
    {
        this.worldTime = Moonlight.instance.moduleManager.getModuleByName("TimeChanger").isToggled() ? (long) Moonlight.instance.settingsManager.getSettingByName(Moonlight.instance.moduleManager.getModuleByName("TimeChanger"), "Time").getValDouble() : time;
    }
}
