package moonlight.mixin.mixins.accessor;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import moonlight.utils.motionblur.AccessorUtils;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;

@Mixin(ShaderGroup.class)
public abstract class ShaderGroupAccessor implements AccessorUtils {

	@Override
	@Accessor
	public abstract List<Shader> getListShaders();
}
