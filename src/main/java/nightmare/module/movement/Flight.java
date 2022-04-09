package nightmare.module.movement;

import org.lwjgl.input.Keyboard;

import nightmare.Nightmare;
import nightmare.event.EventTarget;
import nightmare.event.impl.EventUpdate;
import nightmare.module.Category;
import nightmare.module.Module;
import nightmare.settings.Setting;

public class Flight extends Module{

	public float speed = 1F;
	
	public Flight() {
		super("Flight", 0, Category.MOVEMENT);
		
		this.setBlatantModule(true);
		Nightmare.instance.settingsManager.rSetting(new Setting("Speed", this, 1, 1, 5, false));
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(mc.thePlayer.isSprinting()) {
			this.speed = 1.5F;
		}else {
			this.speed = 1.0F;
		}
		
        mc.thePlayer.noClip = true;
        mc.thePlayer.fallDistance = 0.0F;
        mc.thePlayer.onGround = false;
        mc.thePlayer.capabilities.isFlying = false;
        mc.thePlayer.motionX = 0.0D;
        mc.thePlayer.motionY = 0.0D;
        mc.thePlayer.motionZ = 0.0D;
        float moveSpeed = (float) Nightmare.instance.settingsManager.getSettingByName(this, "Speed").getValDouble();
        mc.thePlayer.jumpMovementFactor = moveSpeed;
        if(Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode())) {
        	mc.thePlayer.motionY += moveSpeed;
        }
        if(Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())) {
        	mc.thePlayer.motionY -= moveSpeed;
        }
	}
	
	@Override
	public void onDisable() {
		
		if(mc.thePlayer == null || mc.theWorld == null) {
			return;
		}
		
        mc.thePlayer.capabilities.isCreativeMode = false;
        mc.thePlayer.noClip = false;
        mc.thePlayer.capabilities.isFlying = false;
		super.onDisable();
	}
}
