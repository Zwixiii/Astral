package moonlight.mixin.mixins.gui;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import moonlight.event.impl.EventRender2D;
import moonlight.utils.GlUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

@Mixin(GuiIngame.class)
public abstract class MixinGuiIngame extends Gui{
	
	@Shadow
    protected static final ResourceLocation widgetsTexPath = new ResourceLocation("textures/gui/widgets.png");
	
	@Shadow
	protected abstract void renderHotbarItem(int index, int xPos, int yPos, float partialTicks, EntityPlayer player);
	
    @Inject(method = "renderTooltip", at = @At("RETURN"))
    private void renderTooltip(ScaledResolution sr, float partialTicks, CallbackInfo ci) {
        EventRender2D event = new EventRender2D();
        event.call();
    }
    
    @Inject(method = "renderHotbarItem", at = @At("HEAD"))
    protected void renderHotbarItemHead(int index, int xPos, int yPos, float partialTicks, EntityPlayer player, CallbackInfo ci) {
    	GlUtils.fixGlint();
    }
}
