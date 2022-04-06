package moonlight.module.movement;

import org.lwjgl.input.Keyboard;

import moonlight.Moonlight;
import moonlight.event.EventTarget;
import moonlight.event.impl.EventUpdate;
import moonlight.module.Category;
import moonlight.module.Module;
import moonlight.settings.Setting;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;

public class GuiMove extends Module{

	public GuiMove() {
		super("GuiMove", 0, Category.MOVEMENT);
		
		Moonlight.instance.settingsManager.rSetting(new Setting("Sneak", this, false));
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		
		if(mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat)) {
	         KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()));
	         KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode()));
	         KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode()));
	         KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode()));
	         KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode()));
	         
	         if(Moonlight.instance.settingsManager.getSettingByName(this, "Sneak").getValBoolean()) {
		         KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()));
	         }
		}
	}
}