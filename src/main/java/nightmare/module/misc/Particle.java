package nightmare.module.misc;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumParticleTypes;
import nightmare.Nightmare;
import nightmare.event.EventTarget;
import nightmare.event.impl.EventAttackEntity;
import nightmare.event.impl.EventTick;
import nightmare.gui.notification.NotificationManager;
import nightmare.gui.notification.NotificationType;
import nightmare.module.Category;
import nightmare.module.Module;
import nightmare.settings.Setting;

public class Particle extends Module{

	public Particle() {
		super("Particle", 0, Category.MISC);
		
		Nightmare.instance.settingsManager.rSetting(new Setting("AlwaysSharpness", this, false));
		Nightmare.instance.settingsManager.rSetting(new Setting("AlwaysCriticals", this, false));
		Nightmare.instance.settingsManager.rSetting(new Setting("Criticals", this, false));
		Nightmare.instance.settingsManager.rSetting(new Setting("SharpnessVal", this, 4, 1, 10, true));
		Nightmare.instance.settingsManager.rSetting(new Setting("CriticalsVal", this, 4, 1, 10, true));
	}

	@EventTarget
	public void onTick(EventTick event) {
		if(!Nightmare.instance.settingsManager.getSettingByName(this, "Criticals").getValBoolean() && Nightmare.instance.settingsManager.getSettingByName(this, "AlwaysCriticals").getValBoolean()) {
			Nightmare.instance.settingsManager.getSettingByName(this, "AlwaysCriticals").setValBoolean(false);
			NotificationManager.show(NotificationType.WARNING, "Module", "Please toggle Criticals Setting", 3000);
		}
	}
	
	@EventTarget
	public void onAttackEntity(EventAttackEntity event) {
		
		int sharpnessVal = (int) Nightmare.instance.settingsManager.getSettingByName(this, "SharpnessVal").getValDouble();
		int criticalsVal = (int) Nightmare.instance.settingsManager.getSettingByName(this, "CriticalsVal").getValDouble();
		
		EntityPlayer player = mc.thePlayer;

		boolean isCritical = player.fallDistance > 0.0F && !player.onGround && !player.isOnLadder() && !player.isInWater() && !player.isPotionActive(Potion.blindness) && player.ridingEntity == null;
		
		if(!(event.entity instanceof EntityLivingBase)) {
			return;
		}
		
		if(Nightmare.instance.settingsManager.getSettingByName(this, "AlwaysSharpness").getValBoolean() || (EnchantmentHelper.getModifierForCreature(player.getHeldItem(), ((EntityLivingBase) event.entity).getCreatureAttribute()) > 0)) {
			for(int i = 0; i < sharpnessVal; i++) {
				mc.effectRenderer.emitParticleAtEntity(event.entity, EnumParticleTypes.CRIT_MAGIC);
			}
		}
		
		if((Nightmare.instance.settingsManager.getSettingByName(this, "Criticals").getValBoolean() && Nightmare.instance.settingsManager.getSettingByName(this, "AlwaysCriticals").getValBoolean()) || (Nightmare.instance.settingsManager.getSettingByName(this, "Criticals").getValBoolean() && isCritical)) {
			for(int i = 0; i < criticalsVal; i++) {
				mc.effectRenderer.emitParticleAtEntity(event.entity, EnumParticleTypes.CRIT);
			}
		}
	}
}
