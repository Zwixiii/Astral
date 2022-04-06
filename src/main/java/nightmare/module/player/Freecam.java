package nightmare.module.player;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import nightmare.Nightmare;
import nightmare.event.EventTarget;
import nightmare.event.impl.EventBoundingBox;
import nightmare.event.impl.EventSendPacket;
import nightmare.event.impl.EventUpdate;
import nightmare.module.Category;
import nightmare.module.Module;
import nightmare.settings.Setting;

public class Freecam extends Module{

	private EntityOtherPlayerMP freecamPlayer;
	
	private double oldX, oldY, oldZ;
	
	public float speed = 1F;
	
	public Freecam() {
		super("Freecam", 0, Category.PLAYER);
		
		Nightmare.instance.settingsManager.rSetting(new Setting("Speed", this, 1, 1, 5, false));
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(mc.thePlayer.isSprinting()) {
			this.speed = 1.5F;
		}else {
			this.speed = 1.0F;
		}
		
        mc.thePlayer.noClip = true;
        mc.thePlayer.fallDistance = 0.0F;
        mc.thePlayer.onGround = false;
        mc.thePlayer.capabilities.isFlying = false;
        mc.thePlayer.motionX = 0.0D;
        mc.thePlayer.motionY = 0.0D;
        mc.thePlayer.motionZ = 0.0D;
        float moveSpeed = (float) Nightmare.instance.settingsManager.getSettingByName(this, "Speed").getValDouble();
        mc.thePlayer.jumpMovementFactor = moveSpeed;
        if(Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode())) {
        	mc.thePlayer.motionY += moveSpeed;
        }
        if(Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())) {
        	mc.thePlayer.motionY -= moveSpeed;
        }
	}
	
    @EventTarget
    public void onPacket(EventSendPacket event) {
    	 if (event.getPacket() instanceof C03PacketPlayer || event.getPacket() instanceof C0BPacketEntityAction) {
    		 event.setCancelled(true);
    	 }
    }
    
    @EventTarget
    public void onBoundingBox(EventBoundingBox event) {
    	event.boundingBox = null;
    }
	
	@Override
	public void onEnable() {
		
		if(mc.thePlayer == null || mc.theWorld == null) {
			return;
		}
		
		this.oldX = mc.thePlayer.posX;
		this.oldY = mc.thePlayer.posY;
		this.oldZ = mc.thePlayer.posZ;
		WorldClient worldClient = mc.theWorld;
		this.freecamPlayer = new EntityOtherPlayerMP(worldClient, mc.thePlayer.getGameProfile());
        EntityOtherPlayerMP entityOtherPlayerMP = this.freecamPlayer;
        entityOtherPlayerMP.clonePlayer(mc.thePlayer, true);
        entityOtherPlayerMP = this.freecamPlayer;
        entityOtherPlayerMP.copyLocationAndAnglesFrom(mc.thePlayer);
        entityOtherPlayerMP = this.freecamPlayer;
        entityOtherPlayerMP.rotationYawHead = mc.thePlayer.rotationYawHead;
        mc.theWorld.addEntityToWorld(-69, this.freecamPlayer);
        super.onEnable();
	}
	
	@Override
	public void onDisable() {
        mc.thePlayer.capabilities.isCreativeMode = false;
        mc.thePlayer.noClip = false;
        mc.thePlayer.capabilities.isFlying = false;
        EntityPlayerSP entityPlayerSP = mc.thePlayer;
        double oldX = this.oldX;
        double oldY = this.oldY;
        double oldZ = this.oldZ;
        float rotationYaw = mc.thePlayer.rotationYaw;
        entityPlayerSP.setPositionAndRotation(oldX, oldY, oldZ, rotationYaw, mc.thePlayer.rotationPitch);
        mc.theWorld.removeEntityFromWorld(-69);
        this.freecamPlayer = null;
        mc.renderGlobal.loadRenderers();
        super.onDisable();
	}

}
