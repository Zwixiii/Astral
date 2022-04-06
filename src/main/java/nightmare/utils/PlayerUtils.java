package nightmare.utils;

import net.minecraft.client.Minecraft;

public class PlayerUtils {
	
	private static Minecraft mc = Minecraft.getMinecraft();
	
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
