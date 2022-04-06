package moonlight.mixin.mixins.render;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import moonlight.Moonlight;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.passive.EntityBat;

@Mixin(RendererLivingEntity.class)
public class MixinRendererLivingEntity {

    @Inject(method = "doRender", at = @At("HEAD"))
    private <T extends EntityLivingBase> void startChams(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (Moonlight.instance.moduleManager.getModuleByName("Chams").isToggled() && Moonlight.instance.settingsManager.getSettingByName(Moonlight.instance.moduleManager.getModuleByName("Chams"), "Player").getValBoolean()) {
            GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
            GL11.glPolygonOffset(1.0F, -1000000F);
        }
    }
    
    @Inject(method = "doRender", at = @At("HEAD"), cancellable = true)
    private <T extends EntityLivingBase> void hideArmorStand(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
    	if(Moonlight.instance.moduleManager.getModuleByName("FPSBoost").isToggled() && Moonlight.instance.settingsManager.getSettingByName(Moonlight.instance.moduleManager.getModuleByName("FPSBoost"), "HideArmorStand").getValBoolean()) {
    		if(entity instanceof EntityArmorStand) {
    			ci.cancel();
    		}
    	}
    }
    
    @Inject(method = "doRender", at = @At("HEAD"), cancellable = true)
    private <T extends EntityLivingBase> void hideBat(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
    	if(Moonlight.instance.moduleManager.getModuleByName("FPSBoost").isToggled() && Moonlight.instance.settingsManager.getSettingByName(Moonlight.instance.moduleManager.getModuleByName("FPSBoost"), "HideBat").getValBoolean()) {
    		if(entity instanceof EntityBat) {
    			ci.cancel();
    		}
    	}
    }

    @Inject(method = "doRender", at = @At("RETURN"))
    private <T extends EntityLivingBase> void stopChams(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (Moonlight.instance.moduleManager.getModuleByName("Chams").isToggled() && Moonlight.instance.settingsManager.getSettingByName(Moonlight.instance.moduleManager.getModuleByName("Chams"), "Player").getValBoolean()) {
            GL11.glPolygonOffset(1.0F, 1000000F);
            GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
        }
    }
}
