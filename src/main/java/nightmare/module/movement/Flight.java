package nightmare.module.movement;

import java.util.ArrayList;

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
		
		ArrayList<String> options = new ArrayList<>();
		
		options.add("Vanilla");
		options.add("FastFly");
		options.add("AirWalk");
		
		Nightmare.instance.settingsManager.rSetting(new Setting("Speed", this, 1, 1, 5, false));
		Nightmare.instance.settingsManager.rSetting(new Setting("Mode", this, "Vanilla", options));
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		
		String mode = Nightmare.instance.settingsManager.getSettingByName(this, "Mode").getValString();
		
		if(mode.equals("Vanilla")) {
	        mc.thePlayer.capabilities.isFlying = true;
		}else if(mode.equals("FastFly")) {
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
		}else if(mode.equals("AirWalk")) {
            mc.thePlayer.onGround = true;
        	mc.thePlayer.motionY = 0;
		}
	}
	
	@Override
	public void onDisable() {
		
		String mode = Nightmare.instance.settingsManager.getSettingByName(this, "Mode").getValString();
		
		if(mc.thePlayer == null || mc.theWorld == null) {
			return;
		}
		
		if(mode.equals("Vanilla")) {
	        mc.thePlayer.capabilities.isFlying = false;
		}else if(mode.equals("FastFly")) {
	        mc.thePlayer.capabilities.isCreativeMode = false;
	        mc.thePlayer.noClip = false;
	        mc.thePlayer.capabilities.isFlying = false;
		}
		super.onDisable();
	}
}
