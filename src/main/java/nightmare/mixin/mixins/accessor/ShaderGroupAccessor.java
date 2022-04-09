package nightmare.mixin.mixins.accessor;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import nightmare.utils.AccessorUtils;

@Mixin(ShaderGroup.class)
public abstract class ShaderGroupAccessor implements AccessorUtils {

	@Override
	@Accessor
	public abstract List<Shader> getListShaders();
}
