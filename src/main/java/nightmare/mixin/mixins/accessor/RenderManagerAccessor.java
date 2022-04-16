package nightmare.mixin.mixins.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.renderer.entity.RenderManager;

@Mixin(RenderManager.class)
public interface RenderManagerAccessor {

	@Accessor
    double getRenderPosX();
	
	@Accessor
    double getRenderPosY();
	
	@Accessor
    double getRenderPosZ();
}
