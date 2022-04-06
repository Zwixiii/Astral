package moonlight.module.misc;

import java.util.ArrayList;
import java.util.Random;

import moonlight.Moonlight;
import moonlight.event.EventTarget;
import moonlight.event.impl.EventTick;
import moonlight.module.Category;
import moonlight.module.Module;
import moonlight.settings.Setting;
import net.minecraft.client.entity.EntityPlayerSP;

public class Spin extends Module{

    private float yaw;
    
    private Random random = new Random();
    
    private int randomval;
	public Spin() {
		super("Spin", 0, Category.MISC);

		ArrayList<String> options = new ArrayList<>();
		
		options.add("Left");
		options.add("Right");
		options.add("Random");
		
		Moonlight.instance.settingsManager.rSetting(new Setting("Speed", this, 55, 1, 100, false));
		Moonlight.instance.settingsManager.rSetting(new Setting("Mode", this, "Left", options));
	}
	
	@EventTarget
	public void onTick(EventTick event) {
		
		String mode = Moonlight.instance.settingsManager.getSettingByName(this, "Mode").getValString();
		
		if(mode.equals("Left")) {
			this.spinLeft();
		}else if(mode.equals("Right")) {
			this.spinRight();
		}else if(mode.equals("Random")) {
			
			if(randomval == 0) {
				this.spinLeft();
			}
			
			if(randomval == 1) {
				this.spinRight();
			}
		}
	}
	
	@Override
	public void onEnable() {
		randomval = random.nextInt(2);
		if(mc.thePlayer != null & mc.theWorld != null) {
	        this.yaw = mc.thePlayer.rotationYaw;
		}
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		if(mc.thePlayer != null & mc.theWorld != null) {
	        this.yaw = 0.0F;
		}
		super.onDisable();
	}
	
	public void spinLeft() {
        double left = (double)this.yaw + 360 - (double)mc.thePlayer.rotationYaw;
        EntityPlayerSP player;
        if (left < Moonlight.instance.settingsManager.getSettingByName(this, "Speed").getValDouble()) {
            player = mc.thePlayer;
            player.rotationYaw = (float)((double)player.rotationYaw + left);
        	toggle();
        } else {
            player = mc.thePlayer;
            player.rotationYaw = (float) ((double)player.rotationYaw + Moonlight.instance.settingsManager.getSettingByName(this, "Speed").getValDouble());
            if ((double)mc.thePlayer.rotationYaw >= (double)this.yaw + 360) {
            	toggle();
            }
        }
	}
	
	public void spinRight() {
        double right = (double)this.yaw - 360 - (double)mc.thePlayer.rotationYaw;
        EntityPlayerSP player;
        if (right > -Moonlight.instance.settingsManager.getSettingByName(this, "Speed").getValDouble()) {
            player = mc.thePlayer;
            player.rotationYaw = (float)((double)player.rotationYaw + right);
        	toggle();
        } else {
            player = mc.thePlayer;
            player.rotationYaw = (float) ((double)player.rotationYaw - Moonlight.instance.settingsManager.getSettingByName(this, "Speed").getValDouble());
            if ((double)mc.thePlayer.rotationYaw <= (double)this.yaw - 360) {
            	toggle();
            }
        }
	}
}