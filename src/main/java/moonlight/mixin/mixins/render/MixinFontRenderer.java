package moonlight.mixin.mixins.render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import moonlight.Moonlight;
import moonlight.event.impl.EventText;
import net.minecraft.client.gui.FontRenderer;

@Mixin(FontRenderer.class)
public class MixinFontRenderer {

    @ModifyVariable(method = "renderString", at = @At("HEAD"), ordinal = 0)
    private String renderString(String text) {
    	if(text == null || Moonlight.instance.eventManager == null) {
    		return text;
    	}
    	
    	EventText event = new EventText(text);
    	event.call();
    	
    	return event.getOutputText();
    }
}
