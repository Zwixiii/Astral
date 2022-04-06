package moonlight.hooks;

import org.lwjgl.input.Keyboard;

import moonlight.mixin.mixins.accessor.KeyBindingAccessor;
import net.minecraft.client.settings.KeyBinding;

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
