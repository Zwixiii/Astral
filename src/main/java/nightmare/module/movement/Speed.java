package nightmare.module.movement;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import nightmare.Nightmare;
import nightmare.event.EventTarget;
import nightmare.event.impl.EventPreMotionUpdate;
import nightmare.event.impl.EventUpdate;
import nightmare.mixin.mixins.accessor.MinecraftAccessor;
import nightmare.module.Category;
import nightmare.module.Module;
import nightmare.settings.Setting;
import nightmare.utils.PlayerUtils;

public class Speed extends Module{

	private float speed;
	
	private MinecraftAccessor mcAccessor = (MinecraftAccessor)mc;
	
	public Speed() {
		super("Speed", 0, Category.MOVEMENT);
		
		ArrayList<String> options = new ArrayList<>();
		
		options.add("Normal");
		options.add("Smooth");
		options.add("DoubleJump");
		options.add("Legit");
		
		Nightmare.instance.settingsManager.rSetting(new Setting("Mode", this, "Normal", options));
		
		this.setBlatantModule(true);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		
		String mode = Nightmare.instance.settingsManager.getSettingByName(this, "Mode").getValString();
		
		if(Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode()) || mc.thePlayer.isInWater()) {
			return;
		}
		
		if(mode.equals("Normal")) {
            PlayerUtils.setSpeed(PlayerUtils.getDefaultPlayerSpeed() * speed);
		}
		
		if(mode.equals("Legit")) {
            PlayerUtils.setSpeed(PlayerUtils.getDefaultPlayerSpeed() * speed);
		}
		
		if(mode.equals("Smooth")) {
            speed -= 0.004;
            PlayerUtils.setSpeed(PlayerUtils.getDefaultPlayerSpeed() * speed);
		}
	}
	
	@EventTarget
	public void onPreUpdate(EventPreMotionUpdate event) {
		
		String mode = Nightmare.instance.settingsManager.getSettingByName(this, "Mode").getValString();
		
		if(Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode()) || mc.thePlayer.isInWater()) {
			return;
		}
		
		if(mode.equals("Normal")) {
			if(mc.thePlayer.onGround) {
		        if (PlayerUtils.isMoving()) {
		            mc.thePlayer.jump();
		            speed = 1.5f;
		        }
			}
		}
		
		if(mode.equals("Smooth")) {
			if(mc.thePlayer.onGround) {
		        if (PlayerUtils.isMoving()) {
		            mc.thePlayer.jump();
		            speed = 1.10f;
		        }
			}
		}
		
		if(mode.equals("DoubleJump")) {
			if(mc.thePlayer.onGround) {
		        if (PlayerUtils.isMoving()) {
		        	for(int i = 0; i < 2; i++) {
			            mc.thePlayer.jump();
		        	}
		        }
			}
		}
		
		if(mode.equals("Legit")) {
			if(mc.thePlayer.onGround) {
		        if (PlayerUtils.isMoving()) {
		            mc.thePlayer.jump();
		            speed = 0.9f;
		        }
			}
		}
	}
	
    @Override
    public void onEnable() {
        speed = 1.1f;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        mc.thePlayer.motionX = mc.thePlayer.motionZ = 0;
        mcAccessor.timer().timerSpeed = 1;
        super.onDisable();
    }
}
