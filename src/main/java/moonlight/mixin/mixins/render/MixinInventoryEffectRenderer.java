package moonlight.mixin.mixins.render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import moonlight.Moonlight;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.inventory.Container;

@Mixin(InventoryEffectRenderer.class)
public abstract class MixinInventoryEffectRenderer extends GuiContainer{

	@Shadow
    private boolean hasActivePotionEffects;
	
	public MixinInventoryEffectRenderer(Container inventorySlotsIn) {
		super(inventorySlotsIn);
	}
	
	@Overwrite
    protected void updateActivePotionEffects()
    {
        if (!this.mc.thePlayer.getActivePotionEffects().isEmpty())
        {
        	if(Moonlight.instance.moduleManager.getModuleByName("NoPotionShift").isToggled()) {
        		this.guiLeft = (this.width - this.xSize) / 2;
        	}else {
        		this.guiLeft = 160 + (this.width - this.xSize - 200) / 2;
        	}
        	
            this.hasActivePotionEffects = true;
        }
        else
        {
            this.guiLeft = (this.width - this.xSize) / 2;
            this.hasActivePotionEffects = false;
        }
    }
}
