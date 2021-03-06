package nightmare.mixin.mixins.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.entity.EntityPlayerSP;
import nightmare.event.impl.EventPreMotionUpdate;
import nightmare.event.impl.EventUpdate;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {

	@Inject(method = "onUpdate", at = @At("HEAD"))
	public void onUpdate(CallbackInfo ci) {
		EventUpdate event = new EventUpdate();
		event.call();
	}
	
	@Inject(method = "onUpdateWalkingPlayer", at = @At("HEAD"))
    public void onUpdateWalkingPlayer(CallbackInfo ci) {
    	EventPreMotionUpdate event = new EventPreMotionUpdate();
    	event.call();
    }
}
