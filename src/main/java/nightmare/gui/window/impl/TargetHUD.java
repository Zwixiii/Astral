package nightmare.gui.window.impl;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.ResourceLocation;
import nightmare.Nightmare;
import nightmare.clickgui.ClickGUI;
import nightmare.fonts.impl.Fonts;
import nightmare.gui.window.Window;
import nightmare.utils.ColorUtils;
import nightmare.utils.render.BlurUtils;
import nightmare.utils.render.RenderUtils;

public class TargetHUD extends Window{

	private EntityLivingBase target = null;
	
	private float health, armor;
	
	private Minecraft mc = Minecraft.getMinecraft();
	
	public TargetHUD() {
		super("TargetHUD", 30, 30, 131, 34, true, false);
	}
	
	@Override
	public void onRender() {
		
		target = this.getClosest(Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("HUD"), "Distance").getValDouble());
		
		if(mc.currentScreen instanceof ClickGUI) {
			target = mc.thePlayer;
		}
		
		if(target != null) {
			
			health = (target.getHealth() + target.getAbsorptionAmount()) < 20 ? (target.getHealth() + target.getAbsorptionAmount()) : 20;
			armor = target.getTotalArmorValue();
			
			int i1 = (int) (target.hurtTime * 0.35F);
			double d1 = target.hurtTime * 23;
			
			if(Nightmare.instance.moduleManager.getModuleByName("Blur").isToggled() && Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("Blur"), "TargetHUD").getValBoolean()) {
				BlurUtils.drawBlurRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
			}
			
			RenderUtils.drawBorderedRect(this.getX(), this.getY(), this.getWidth(), this.getHeight(), 2, ColorUtils.getBackgroundColor(), new Color(90, 90, 90, 180).getRGB());
			
			RenderUtils.setColor(new Color(255, (int) (255 - d1), (int) (255 - d1)));
			this.renderPlayerFace(this.getX() + 2 + i1 / 2F, this.getY() + 2 + i1 / 2F, 3, 3, 3, 3, 30 - i1, 30 - i1, 24, 24.5F, (AbstractClientPlayer) target);
			RenderUtils.setColor(new Color(255, 255, 255));
			
			Fonts.REGULAR.REGULAR_20.REGULAR_20.drawString(target.getName(), this.getX() + 35, this.getY() + 4, -1);
			Fonts.ICON.ICON_16.ICON_16.drawString("D", this.getX() + 35, this.getY() + 16, new Color(255, 80, 80).getRGB());
			Fonts.ICON.ICON_16.ICON_16.drawString("E", this.getX() + 35, this.getY() + 26, new Color(150, 150, 150).getRGB());
			RenderUtils.drawRect(this.getX() + 45, this.getY() + 14, this.getX() + 128, this.getY() + 20, new Color(100, 100, 100).getRGB());
			RenderUtils.drawRect(this.getX() + 45, this.getY() + 14, this.getX() + (health * 4) + 48, this.getY() + 20, new Color(10, 150, 75).getRGB());
			RenderUtils.drawRect(this.getX() + 45, this.getY() + 24, this.getX() + 128, this.getY() + 30, new Color(100, 100, 100).getRGB());
			RenderUtils.drawRect(this.getX() + 45, this.getY() + 24, this.getX() + (armor * 4) + 48, this.getY() + 30, new Color(12, 180, 250).getRGB());
		}
	}
	
	private boolean canAttack(EntityLivingBase player) {

		if(player instanceof EntityAnimal || player instanceof EntitySquid || player instanceof EntityMob || player instanceof EntityVillager || player instanceof EntityArmorStand || player instanceof EntityBat || player.getDisplayName().getFormattedText().contains("[NPC]")) {
			return false;
		}

		return player != mc.thePlayer && player.isEntityAlive() && mc.thePlayer.getDistanceToEntity(player) <= (float) Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("HUD"), "Distance").getValDouble();
	}
	
	private EntityLivingBase getClosest(double range) {
		double dist = range;
		EntityLivingBase target = null;
		for (Object object : mc.theWorld.loadedEntityList) {
			Entity entity = (Entity) object;
			if (entity instanceof EntityLivingBase) {
				EntityLivingBase player = (EntityLivingBase) entity;
				if (canAttack(player)) {
					double currentDist = mc.thePlayer.getDistanceToEntity(player);
					if (currentDist <= dist) {
						dist = currentDist;
						target = player;
					}
				}
			}
		}
		return target;
	}
	
    public void renderPlayerFace(double x, double y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight, AbstractClientPlayer target) {
        ResourceLocation resourceLocation = target.getLocationSkin();
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        GL11.glEnable(GL11.GL_BLEND);
        this.drawScaledCustomSizeModalRect(x, y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
        GL11.glDisable(GL11.GL_BLEND);
    }
    
    public void drawScaledCustomSizeModalRect(double x, double y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight)
    {
        float f = 1.0F / tileWidth;
        float f1 = 1.0F / tileHeight;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos((double)x, (double)(y + height), 0.0D).tex((double)(u * f), (double)((v + (float)vHeight) * f1)).endVertex();
        worldrenderer.pos((double)(x + width), (double)(y + height), 0.0D).tex((double)((u + (float)uWidth) * f), (double)((v + (float)vHeight) * f1)).endVertex();
        worldrenderer.pos((double)(x + width), (double)y, 0.0D).tex((double)((u + (float)uWidth) * f), (double)(v * f1)).endVertex();
        worldrenderer.pos((double)x, (double)y, 0.0D).tex((double)(u * f), (double)(v * f1)).endVertex();
        tessellator.draw();
    }
}
