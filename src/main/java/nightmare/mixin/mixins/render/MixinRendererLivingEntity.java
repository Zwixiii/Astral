package nightmare.mixin.mixins.render;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.passive.EntityBat;
import nightmare.Nightmare;

@Mixin(RendererLivingEntity.class)
public abstract class MixinRendererLivingEntity<T extends EntityLivingBase> extends MixinRender<T>{

    @Inject(method = "doRender", at = @At("HEAD"))
    private void startChams(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (Nightmare.instance.moduleManager.getModuleByName("Chams").isToggled() && Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("Chams"), "Player").getValBoolean()) {
            GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
            GL11.glPolygonOffset(1.0F, -1000000F);
        }
    }
    
    @Inject(method = "doRender", at = @At("HEAD"), cancellable = true)
    private void hideArmorStand(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
    	if(Nightmare.instance.moduleManager.getModuleByName("FPSBoost").isToggled() && Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("FPSBoost"), "HideArmorStand").getValBoolean()) {
    		if(entity instanceof EntityArmorStand) {
    			ci.cancel();
    		}
    	}
    }
    
    @Inject(method = "doRender", at = @At("HEAD"), cancellable = true)
    private void hideBat(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
    	if(Nightmare.instance.moduleManager.getModuleByName("FPSBoost").isToggled() && Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("FPSBoost"), "HideBat").getValBoolean()) {
    		if(entity instanceof EntityBat) {
    			ci.cancel();
    		}
    	}
    }

    @Inject(method = "doRender", at = @At("RETURN"))
    private void stopChams(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (Nightmare.instance.moduleManager.getModuleByName("Chams").isToggled() && Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("Chams"), "Player").getValBoolean()) {
            GL11.glPolygonOffset(1.0F, 1000000F);
            GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
        }
    }
}
