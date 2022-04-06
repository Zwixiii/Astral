package nightmare.module.movement;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import nightmare.Nightmare;
import nightmare.event.EventTarget;
import nightmare.event.impl.EventUpdate;
import nightmare.module.Category;
import nightmare.module.Module;
import nightmare.settings.Setting;

public class GuiMove extends Module{

	public GuiMove() {
		super("GuiMove", 0, Category.MOVEMENT);
		
		Nightmare.instance.settingsManager.rSetting(new Setting("Sneak", this, false));
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		
		if(mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat)) {
	         KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()));
	         KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode()));
	         KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode()));
	         KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode()));
	         KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode()));
	         
	         if(Nightmare.instance.settingsManager.getSettingByName(this, "Sneak").getValBoolean()) {
		         KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()));
	         }
		}
	}
}