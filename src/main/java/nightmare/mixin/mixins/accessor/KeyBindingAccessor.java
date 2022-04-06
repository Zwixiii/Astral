package nightmare.mixin.mixins.accessor;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.settings.KeyBinding;

@Mixin(KeyBinding.class)
public interface KeyBindingAccessor {
	
    @Accessor
    static List<KeyBinding> getKeybindArray() {
        throw new UnsupportedOperationException("Mixin failed to inject!");
    }
}
