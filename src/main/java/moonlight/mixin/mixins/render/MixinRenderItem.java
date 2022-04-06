package moonlight.mixin.mixins.render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import moonlight.utils.GlUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;

@Mixin(RenderItem.class)
public class MixinRenderItem {

	@Inject(method = "renderItemAndEffectIntoGUI", at = @At("HEAD"))
	public void renderItemAndEffectIntoGUIhead(final ItemStack stack, int xPosition, int yPosition, CallbackInfo ci) {
		GlUtils.fixGlint();
	}
	
	@Inject(method = "renderItemOverlayIntoGUI", at = @At("HEAD"))
	public void renderItemOverlayIntoGUIhead(FontRenderer fr, ItemStack stack, int xPosition, int yPosition, String text, CallbackInfo ci) {
		GlUtils.fixGlint();
	}
}
