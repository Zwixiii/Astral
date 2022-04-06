package moonlight.module.world;

import moonlight.Moonlight;
import moonlight.event.EventTarget;
import moonlight.event.impl.EventTick;
import moonlight.event.impl.EventUpdate;
import moonlight.module.Category;
import moonlight.module.Module;
import moonlight.settings.Setting;
import moonlight.utils.TimerUtils;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;

public class ChestStealer extends Module{

	private TimerUtils timer = new TimerUtils();
	
	public ChestStealer() {
		super("ChestStealer", 0, Category.WORLD);
		
		Moonlight.instance.settingsManager.rSetting(new Setting("Delay", this, 50, 0, 1000, false));
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(!this.isToggled()) {
			return;
		}
		if((mc.thePlayer.openContainer != null) && ((mc.thePlayer.openContainer instanceof ContainerChest))) {
	        ContainerChest chest = (ContainerChest) mc.thePlayer.openContainer;
	        for(int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
	            if((chest.getLowerChestInventory().getStackInSlot(i) != null) && (timer.hasReached(Moonlight.instance.settingsManager.getSettingByName(this, "Delay").getValDouble()))) {
	                mc.playerController.windowClick(chest.windowId, i, 0, 1, mc.thePlayer);
	                timer.reset();
	            }
	        }
	    }
	}
	
	@EventTarget
    private void onTick(final EventTick event) {
        if (mc.theWorld == null || isFullInv()) {
            return;
        }
	}
	
	public static boolean isFullInv() {
        for (int i = 9; i < 45; ++i) {
            final ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (itemStack == null || itemStack.getUnlocalizedName().contains("air")) {
                return false;
            }
        }
        return true;
    }
}