package nightmare.hooks;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import nightmare.mixin.mixins.accessor.KeyBindingAccessor;

public class MinecraftHook {
	
    public static void updateKeyBindState() {
        for (KeyBinding keybinding : KeyBindingAccessor.getKeybindArray()) {
            try {
                final int keyCode = keybinding.getKeyCode();
                KeyBinding.setKeyBindState(keyCode, keyCode < 256 && Keyboard.isKeyDown(keyCode));
            } catch (IndexOutOfBoundsException ignored) {
            }
        }
    }
}
