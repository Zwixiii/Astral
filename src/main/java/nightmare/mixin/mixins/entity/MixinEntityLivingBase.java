package nightmare.mixin.mixins.entity;

import java.util.Iterator;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;

@Mixin(EntityLivingBase.class)
public class MixinEntityLivingBase {

    @Inject(method = "updatePotionEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/potion/PotionEffect;onUpdate(Lnet/minecraft/entity/EntityLivingBase;)Z"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void updatePotionEffects(CallbackInfo ci, Iterator<Integer> iterator, Integer integer, PotionEffect potioneffect) {
    	if(potioneffect == null) {
    		ci.cancel();
    	}
    }
}
