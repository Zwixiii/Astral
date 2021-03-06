package nightmare.module.combat;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import nightmare.Nightmare;
import nightmare.event.EventTarget;
import nightmare.event.impl.EventLoadWorld;
import nightmare.event.impl.EventPreMotionUpdate;
import nightmare.gui.notification.NotificationManager;
import nightmare.gui.notification.NotificationType;
import nightmare.module.Category;
import nightmare.module.Module;
import nightmare.settings.Setting;
import nightmare.utils.RotationUtils;
import nightmare.utils.TimerUtils;

public class LegitAura extends Module {
	
    private EntityLivingBase target;
    private TimerUtils timer = new TimerUtils();
    
    public LegitAura() {
        super("LegitAura", 0, Category.COMBAT);

		ArrayList<String> options = new ArrayList<>();
		
		options.add("Legit");
		options.add("Normal");
		
        Nightmare.instance.settingsManager.rSetting(new Setting("AutoDisable", this, false));
        Nightmare.instance.settingsManager.rSetting(new Setting("HeldItem", this, false));
		Nightmare.instance.settingsManager.rSetting(new Setting("Horizontal", this, 12, 0, 30, false));
		Nightmare.instance.settingsManager.rSetting(new Setting("Vertical", this, 8, 0, 30, false));
		Nightmare.instance.settingsManager.rSetting(new Setting("MinCPS", this, 12, 1, 20, false));
		Nightmare.instance.settingsManager.rSetting(new Setting("MaxCPS", this, 15, 1, 20, false));
		Nightmare.instance.settingsManager.rSetting(new Setting("Range", this, 4.2, 1.0, 8.0, false));
        Nightmare.instance.settingsManager.rSetting(new Setting("FOV", this, 360, 0, 360, true));
        Nightmare.instance.settingsManager.rSetting(new Setting("Teams", this, false));
        Nightmare.instance.settingsManager.rSetting(new Setting("Invisibles", this, false));
		Nightmare.instance.settingsManager.rSetting(new Setting("AttackMode", this, "Legit", options));
    }

    @EventTarget
    public void onPre(EventPreMotionUpdate event) {
    	
    	float horizontalSpeed = (float) Nightmare.instance.settingsManager.getSettingByName(this, "Horizontal").getValDouble();
    	float verticalSpeed = (float) Nightmare.instance.settingsManager.getSettingByName(this, "Vertical").getValDouble();
    	String mode = Nightmare.instance.settingsManager.getSettingByName(this, "AttackMode").getValString();
    	
        target = getClosest(Nightmare.instance.settingsManager.getSettingByName(this, "Range").getValDouble());
        
        if(target != null) {
        	
            if(target.getName().equals(mc.thePlayer.getName())) {
            	return;
            }
            
            if (!Nightmare.instance.settingsManager.getSettingByName(this, "HeldItem").getValBoolean() || mc.thePlayer.getHeldItem() != null && (mc.thePlayer.getHeldItem().getItem() instanceof ItemAxe || mc.thePlayer.getHeldItem().getItem() instanceof ItemSword)) {
            	this.faceTarget(target, horizontalSpeed, verticalSpeed);
            	
            	if (timer.delay(1000 / ThreadLocalRandom.current().nextInt((int) Nightmare.instance.settingsManager.getSettingByName(this, "MinCPS").getValDouble(), (int) Nightmare.instance.settingsManager.getSettingByName(this, "MaxCPS").getValDouble() + 1))) {
                    mc.thePlayer.swingItem();
                    
                    if(mode.equals("Legit")) {
                    	if(mc.objectMouseOver != null && mc.objectMouseOver.entityHit != null) {
                            mc.playerController.attackEntity(mc.thePlayer, target);
                    	}
                    }else {
                    	mc.playerController.attackEntity(mc.thePlayer, target);
                    }
                    
                    timer.reset();
            	}
            }
        }
    }
    
    @EventTarget
    public void onLoadWold(EventLoadWorld event) {
    	if(Nightmare.instance.settingsManager.getSettingByName(this, "AutoDisable").getValBoolean()) {
    		this.setToggled(false);
    		NotificationManager.show(NotificationType.ERROR, "Module", EnumChatFormatting.RED + "Disable " + EnumChatFormatting.WHITE + "(AutoDisable)" + " " + this.getName(), 2500);
    	}
    }
    
	private void faceTarget(Entity target, float yawspeed, float pitchspeed) {
		EntityPlayerSP player = mc.thePlayer;
		float yaw = RotationUtils.getAngles(target)[1];
		float pitch = RotationUtils.getAngles(target)[0];
		player.rotationYaw = RotationUtils.getRotation(player.rotationYaw, yaw, yawspeed);
		player.rotationPitch = RotationUtils.getRotation(player.rotationPitch, pitch, pitchspeed);
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

    private boolean canAttack(EntityLivingBase player) {
    	if(player instanceof EntityVillager || player instanceof EntityArmorStand || player instanceof EntityAnimal || player instanceof EntityMob 
    			|| player.getDisplayName().getFormattedText().contains("[NPC]") || player.getName().contains("#") || player.getName().toLowerCase().contains("shop"))
    		return false;
        if(Nightmare.instance.settingsManager.getSettingByName(this, "Teams").getValBoolean() && player.isOnSameTeam(mc.thePlayer))
            return false;
        if(player.isInvisible() && !Nightmare.instance.settingsManager.getSettingByName(this, "Invisibles").getValBoolean())
            return false;
        if(!isInFOV(player, Nightmare.instance.settingsManager.getSettingByName(this, "FOV").getValDouble()))
            return false;
        return player != mc.thePlayer && player.isEntityAlive() && mc.thePlayer.getDistanceToEntity(player) <= Nightmare.instance.settingsManager.getSettingByName(this, "Range").getValDouble();
    }

    private boolean isInFOV(EntityLivingBase entity, double angle) {
        angle *= .5D;
        double angleDiff = getAngleDifference(mc.thePlayer.rotationYaw, getRotations(entity.posX, entity.posY, entity.posZ)[0]);
        return (angleDiff > 0 && angleDiff < angle) || (-angle < angleDiff && angleDiff < 0);
    }

    private float getAngleDifference(float dir, float yaw) {
        float f = Math.abs(yaw - dir) % 360F;
        float dist = f > 180F ? 360F - f : f;
        return dist;
    }

    private float[] getRotations(double x, double y, double z) {
        double diffX = x + .5D - mc.thePlayer.posX;
        double diffY = (y + .5D) / 2D - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        double diffZ = z + .5D - mc.thePlayer.posZ;

        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180D / Math.PI) - 90F;
        float pitch = (float)-(Math.atan2(diffY, dist) * 180D / Math.PI);

        return new float[] { yaw, pitch };
    }
}