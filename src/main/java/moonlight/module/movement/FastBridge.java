package moonlight.module.movement;

import org.lwjgl.input.Keyboard;

import moonlight.Moonlight;
import moonlight.event.EventTarget;
import moonlight.event.impl.EventUpdate;
import moonlight.module.Category;
import moonlight.module.Module;
import moonlight.settings.Setting;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;

public class FastBridge extends Module{

	public FastBridge() {
		super("FastBridge", 0, Category.MOVEMENT);
		
		Moonlight.instance.settingsManager.rSetting(new Setting("SmartCheck", this, false));
		Moonlight.instance.settingsManager.rSetting(new Setting("HeldItem", this, false));
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		ItemStack i = mc.thePlayer.getCurrentEquippedItem();
		BlockPos bP = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 0.5D, mc.thePlayer.posZ);
		
		if(this.isToggled()) {
			
			if(!(mc.currentScreen instanceof Gui)) {
				KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()));
			}
			
			if(Moonlight.instance.settingsManager.getSettingByName(this, "HeldItem").getValBoolean()) {
				if(i == null && !Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())) {
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
				}
			}
			
			if(Moonlight.instance.settingsManager.getSettingByName(this, "SmartCheck").getValBoolean()) {
				if((!mc.thePlayer.onGround && !Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()) || (Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()) && !Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())))) {
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
				}
				if(mc.thePlayer.isSprinting() || Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode())) {
					return;
				}
			}
			
			if(Moonlight.instance.settingsManager.getSettingByName(this, "HeldItem").getValBoolean() ? (i != null && i.getItem() instanceof ItemBlock) : true) {
				if(mc.thePlayer.onGround) {
					
					if(!Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())) {
						KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
					}
					
					if(mc.theWorld.getBlockState(bP).getBlock() == Blocks.air) {
						KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
					}
				}
			}
		}
	}
}
