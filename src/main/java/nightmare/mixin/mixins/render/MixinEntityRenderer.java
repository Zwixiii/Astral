package nightmare.mixin.mixins.render;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.common.base.Predicates;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import nightmare.Nightmare;
import nightmare.module.combat.Reach;
import nightmare.utils.motionblur.MotionBlurUtils;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {

    @Shadow
    private Entity pointedEntity;

    @Shadow
    private Minecraft mc;
    
    @Shadow
    private ShaderGroup theShaderGroup;
    
    @Shadow
    private boolean useShader;
    
	@Inject(method = "updateCameraAndRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/shader" + "/Framebuffer;bindFramebuffer(Z)V", shift = At.Shift.BEFORE))
    public void updateCameraAndRender(float partialTicks, long nanoTime, CallbackInfo ci) {
		
        List<ShaderGroup> shaders = new ArrayList<ShaderGroup>();

        if (this.theShaderGroup != null && this.useShader)
        {
            shaders.add(this.theShaderGroup);
        }

        ShaderGroup motionBlur = MotionBlurUtils.instance.getShader();

        if(Nightmare.instance.moduleManager.getModuleByName("MotionBlur").isToggled()) {
            if (motionBlur != null)
            {
                shaders.add(motionBlur);
            }

            for (ShaderGroup shader : shaders)
            {
                GlStateManager.pushMatrix();
                GlStateManager.loadIdentity();
                shader.loadShaderGroup(partialTicks);
                GlStateManager.popMatrix();
            }
        }
    }
    
    @Inject(method = "updateShaderGroupSize", at = @At("RETURN"))
    public void updateShaderGroupSize(int width, int height, CallbackInfo ci) {
    	
		if(mc.theWorld == null) {
			return;
		}
		
        if (OpenGlHelper.shadersSupported) {
         	ShaderGroup motionBlur = MotionBlurUtils.instance.getShader();

        	if (motionBlur != null)
        	{
        	    motionBlur.createBindFramebuffers(width, height);
        	}
        }
    }
    
    @Overwrite
    public void getMouseOver(float p_getMouseOver_1_) {
        Entity entity = this.mc.getRenderViewEntity();
        if(entity != null && this.mc.theWorld != null) {
            this.mc.mcProfiler.startSection("pick");
            this.mc.pointedEntity = null;

            double d0 = Nightmare.instance.moduleManager.getModuleByName("Reach").isToggled() ? Reach.getMaxRange() : (double) this.mc.playerController.getBlockReachDistance();
            this.mc.objectMouseOver = entity.rayTrace(Nightmare.instance.moduleManager.getModuleByName("Reach").isToggled() ? this.mc.playerController.getBlockReachDistance() : d0, p_getMouseOver_1_);
            double d1 = d0;
            Vec3 vec3 = entity.getPositionEyes(p_getMouseOver_1_);
            boolean flag = false;
            if(this.mc.playerController.extendedReach()) {
                d0 = 6.0D;
                d1 = 6.0D;
            }else if(d0 > 3.0D) {
                flag = true;
            }

            if(this.mc.objectMouseOver != null) {
                d1 = this.mc.objectMouseOver.hitVec.distanceTo(vec3);
            }

            if(Nightmare.instance.moduleManager.getModuleByName("Reach").isToggled()) {
            	
                d1 = Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("Reach"), "Range").getValDouble();

                final MovingObjectPosition movingObjectPosition = entity.rayTrace(d1, p_getMouseOver_1_);

                if(movingObjectPosition != null) d1 = movingObjectPosition.hitVec.distanceTo(vec3);
            }

            Vec3 vec31 = entity.getLook(p_getMouseOver_1_);
            Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
            this.pointedEntity = null;
            Vec3 vec33 = null;
            float f = 1.0F;
            List<Entity> list = this.mc.theWorld.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand((double) f, (double) f, (double) f), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
            double d2 = d1;

            for (Entity entity1 : list) {
                float f1 = entity1.getCollisionBorderSize();
                AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand((double) f1, (double) f1, (double) f1);
                MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);
                if (axisalignedbb.isVecInside(vec3)) {
                    if (d2 >= 0.0D) {
                        this.pointedEntity = entity1;
                        vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
                        d2 = 0.0D;
                    }
                } else if (movingobjectposition != null) {
                    double d3 = vec3.distanceTo(movingobjectposition.hitVec);
                    if (d3 < d2 || d2 == 0.0D) {
                        if (entity1 == entity.ridingEntity && !entity.canRiderInteract()) {
                            if (d2 == 0.0D) {
                                this.pointedEntity = entity1;
                                vec33 = movingobjectposition.hitVec;
                            }
                        } else {
                            this.pointedEntity = entity1;
                            vec33 = movingobjectposition.hitVec;
                            d2 = d3;
                        }
                    }
                }
            }

            if (this.pointedEntity != null && flag && vec3.distanceTo(vec33) > (Nightmare.instance.moduleManager.getModuleByName("Reach").isToggled() ? Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("Reach"), "Range").getValDouble() : 3.0D)) {
                this.pointedEntity = null;
                this.mc.objectMouseOver = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec33, null, new BlockPos(vec33));
            }

            if(this.pointedEntity != null && (d2 < d1 || this.mc.objectMouseOver == null)) {
                this.mc.objectMouseOver = new MovingObjectPosition(this.pointedEntity, vec33);
                if(this.pointedEntity instanceof EntityLivingBase || this.pointedEntity instanceof EntityItemFrame) {
                    this.mc.pointedEntity = this.pointedEntity;
                }
            }

            this.mc.mcProfiler.endSection();
        }
    }
}
