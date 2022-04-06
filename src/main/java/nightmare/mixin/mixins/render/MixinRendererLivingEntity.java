package nightmare.mixin.mixins.render;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.passive.EntityBat;
import nightmare.Nightmare;
import nightmare.utils.OutlineUtils;

@Mixin(RendererLivingEntity.class)
public abstract class MixinRendererLivingEntity<T extends EntityLivingBase> extends MixinRender<T>{

	@Shadow
	protected ModelBase mainModel;
	
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
    
    @Overwrite
    protected void renderModel(T entitylivingbaseIn, float p_77036_2_, float p_77036_3_, float p_77036_4_, float p_77036_5_, float p_77036_6_, float scaleFactor) {
        boolean flag = !entitylivingbaseIn.isInvisible();
        boolean flag1 = !flag && !entitylivingbaseIn.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer);

        if (flag || flag1)
        {
            if (!this.bindEntityTexture(entitylivingbaseIn))
            {
                return;
            }

            if (flag1)
            {
                GlStateManager.pushMatrix();
                GlStateManager.color(1.0F, 1.0F, 1.0F, 0.15F);
                GlStateManager.depthMask(false);
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(770, 771);
                GlStateManager.alphaFunc(516, 0.003921569F);
            }
            
            if (Nightmare.instance.moduleManager.getModuleByName("ESP").isToggled() && Minecraft.getMinecraft().theWorld != null) {
                OutlineUtils.renderOne();
                this.mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                OutlineUtils.renderTwo();
                this.mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                OutlineUtils.renderThree();
                OutlineUtils.renderFour();
                this.mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                OutlineUtils.renderFive();
            }
            this.mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
            if (flag1)
            {
                GlStateManager.disableBlend();
                GlStateManager.alphaFunc(516, 0.1F);
                GlStateManager.popMatrix();
                GlStateManager.depthMask(true);
            }
        }
    }
}
