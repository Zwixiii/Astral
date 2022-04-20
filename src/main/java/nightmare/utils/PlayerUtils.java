package nightmare.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;

public class PlayerUtils {
	
	private static Minecraft mc = Minecraft.getMinecraft();
	
    public static void setSpeed(double moveSpeed) {
    	
    	float yaw = mc.thePlayer.rotationYaw;
    	double forward = mc.thePlayer.movementInput.moveForward;
    	double strafe = mc.thePlayer.movementInput.moveStrafe;
        
        if (forward != 0.0D) {
            if (strafe > 0.0D) {
                yaw += ((forward > 0.0D) ? -45 : 45);
            } else if (strafe < 0.0D) {
                yaw += ((forward > 0.0D) ? 45 : -45);
            }
            strafe = 0.0D;
            if (forward > 0.0D) {
                forward = 1.0D;
            } else if (forward < 0.0D) {
                forward = -1.0D;
            }
        }
        if (strafe > 0.0D) {
            strafe = 1.0D;
        } else if (strafe < 0.0D) {
            strafe = -1.0D;
        }
        
        double mathX = Math.cos(Math.toRadians((yaw + 90.0F)));
        double mathZ = Math.sin(Math.toRadians((yaw + 90.0F)));
        
        mc.thePlayer.motionX = forward * moveSpeed * mathX + strafe * moveSpeed * mathZ;
        mc.thePlayer.motionZ = forward * moveSpeed * mathZ - strafe * moveSpeed * mathX;
    }
    
    public static double getDefaultPlayerSpeed() {
    	
        double playerSpeed = mc.thePlayer.capabilities.getWalkSpeed() * 2.873;
        
        if (mc.thePlayer.isPotionActive(Potion.moveSlowdown)) {
        	playerSpeed /= 1.0 + 0.2 * (mc.thePlayer.getActivePotionEffect(Potion.moveSlowdown).getAmplifier() + 1);
        }
        
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
        	playerSpeed *= 1.0 + 0.2 * (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        
        return playerSpeed;
    }
    
    public static void shiftClick(int slot) {
        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 0, 1, mc.thePlayer);
    }

    public static void drop(int slot) {
        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 1, 4, mc.thePlayer);
    }
    
    public static void swap(int slot, int hSlot) {
        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, hSlot, 2, mc.thePlayer);
    }
    
    public static boolean isMoving() {
        return mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f;
    }
}
