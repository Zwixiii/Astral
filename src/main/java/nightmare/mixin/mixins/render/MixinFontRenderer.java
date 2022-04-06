package nightmare.mixin.mixins.render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.client.gui.FontRenderer;
import nightmare.Nightmare;
import nightmare.event.impl.EventText;

@Mixin(FontRenderer.class)
public class MixinFontRenderer {

    @ModifyVariable(method = "renderString", at = @At("HEAD"), ordinal = 0)
    private String renderString(String text) {
    	if(text == null || Nightmare.instance.eventManager == null) {
    		return text;
    	}
    	
    	EventText event = new EventText(text);
    	event.call();
    	
    	return event.getOutputText();
    }
}
