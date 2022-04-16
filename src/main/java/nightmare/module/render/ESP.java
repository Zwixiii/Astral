package nightmare.module.render;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import nightmare.Nightmare;
import nightmare.event.EventTarget;
import nightmare.event.impl.EventRender3D;
import nightmare.mixin.mixins.accessor.RenderManagerAccessor;
import nightmare.module.Category;
import nightmare.module.Module;
import nightmare.settings.Setting;
import nightmare.utils.ColorUtils;
import nightmare.utils.render.Render3DUtils;

public class ESP extends Module{
	
	public ESP() {
		super("ESP", 0, Category.RENDER);
		
		Nightmare.instance.settingsManager.rSetting(new Setting("Animals", this, false));
		Nightmare.instance.settingsManager.rSetting(new Setting("Mobs", this, false));
		Nightmare.instance.settingsManager.rSetting(new Setting("Players", this, true));
		Nightmare.instance.settingsManager.rSetting(new Setting("Villagers", this, true));
		Nightmare.instance.settingsManager.rSetting(new Setting("LineWidth", this, 2.0, 0.5, 5, false));
	}
	
	@EventTarget
	public void onRender3D(EventRender3D event) {
		for(Entity entity : mc.theWorld.loadedEntityList) {
			
            double posX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) event.getPartialTicks() - ((RenderManagerAccessor)mc.getRenderManager()).getRenderPosX();
            double posY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) event.getPartialTicks() - ((RenderManagerAccessor)mc.getRenderManager()).getRenderPosY();
            double posZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) event.getPartialTicks() - ((RenderManagerAccessor)mc.getRenderManager()).getRenderPosZ();
            
            if(entity == mc.thePlayer) {
            	continue;
            }
            
            if(this.isValid(entity)) {
            	Render3DUtils.drawBox(posX, posY, posZ, 0.4, entity.getEntityBoundingBox().maxY - entity.getEntityBoundingBox().minY, (float) Nightmare.instance.settingsManager.getSettingByName(this, "LineWidth").getValDouble(), ColorUtils.getClientColorRaw().getRed(), ColorUtils.getClientColorRaw().getGreen(), ColorUtils.getClientColorRaw().getBlue(), 255);
            }
		}
	}
	
	private boolean isValid(Entity entity) {
		
        if(Nightmare.instance.settingsManager.getSettingByName(this, "Animals").getValBoolean() && entity instanceof EntityAnimal) {
        	return true;
        }
        
        if(Nightmare.instance.settingsManager.getSettingByName(this, "Mobs").getValBoolean() && entity instanceof EntityMob) {
        	return true;
        }
        
        if(Nightmare.instance.settingsManager.getSettingByName(this, "Players").getValBoolean() && entity instanceof EntityPlayer) {
        	return true;
        }
        
        if(Nightmare.instance.settingsManager.getSettingByName(this, "Villagers").getValBoolean() && entity instanceof EntityVillager) {
        	return true;
        }
        
        return false;
	}
}
