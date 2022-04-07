package nightmare.mixin.mixins.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.authlib.GameProfile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MovementInput;
import net.minecraft.world.World;
import nightmare.event.impl.EventPreMotionUpdate;
import nightmare.event.impl.EventUpdate;

@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP extends AbstractClientPlayer{

	@Shadow
	protected Minecraft mc;
	
	@Shadow
	protected int sprintToggleTimer;
	
	@Shadow
	public int sprintingTicksLeft;
	
	@Shadow
	public float prevTimeInPortal;
	
	@Shadow
	public MovementInput movementInput;
	
	@Shadow
	public float timeInPortal;
	
	@Shadow
	private int horseJumpPowerCounter;
	
	@Shadow
	private float horseJumpPower;
	
	@Shadow
	public abstract boolean isRidingHorse();
	
	@Shadow
	protected abstract boolean isCurrentViewEntity();
	
	@Shadow
	protected abstract void sendHorseJump();
	
	public MixinEntityPlayerSP(World worldIn, GameProfile playerProfile) {
		super(worldIn, playerProfile);
	}

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
