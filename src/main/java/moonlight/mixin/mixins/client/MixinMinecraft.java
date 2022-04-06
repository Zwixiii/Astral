package moonlight.mixin.mixins.client;

import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import moonlight.Moonlight;
import moonlight.event.impl.EventKey;
import moonlight.event.impl.EventTick;
import moonlight.hooks.MinecraftHook;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

@Mixin(Minecraft.class)
public class MixinMinecraft{
	
    @Shadow
    public Session session;
    
	@Shadow
	private int rightClickDelayTimer;
	
	@Shadow
	private int leftClickCounter;
	
    @Inject(method = "startGame", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;ingameGUI:Lnet/minecraft/client/gui/GuiIngame;", shift = At.Shift.AFTER))
    private void startGame(CallbackInfo ci) {
    	Moonlight.instance.startClient();	
    }
    
    @Inject(method = "shutdown", at = @At("HEAD"))
    private void onShutdown(CallbackInfo ci) {
    	Moonlight.instance.stopClient();
    }
    
    @Inject(method = "runTick", at = @At("TAIL"))
    private void onTick(final CallbackInfo ci) {
    	EventTick event = new EventTick();
    	event.call();
    }
    
    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;dispatchKeypresses()V", shift = At.Shift.AFTER))
    private void onKey(CallbackInfo ci) {
        if (Keyboard.getEventKeyState() && Minecraft.getMinecraft().currentScreen == null) {
        	EventKey event = new EventKey(Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey());
        	event.call();
        }
    }
    
    @Inject(method = "rightClickMouse", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;rightClickDelayTimer:I", shift = At.Shift.AFTER))
    private void rightClickMouse(final CallbackInfo ci) {
    	if(Moonlight.instance.moduleManager.getModuleByName("FastPlace").isToggled()) {
    		rightClickDelayTimer = (int) Moonlight.instance.settingsManager.getSettingByName(Moonlight.instance.moduleManager.getModuleByName("FastPlace"), "Delay").getValDouble();
    	}
    }
    
    @Inject(method = "clickMouse", at = @At("HEAD"))
    private void clickMouse(CallbackInfo ci) {
    	leftClickCounter = 0;
    }
    
    @Inject(method = "setIngameFocus", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/MouseHelper;grabMouseCursor()V"))
    private void setIngameFocus(CallbackInfo ci) {
        MinecraftHook.updateKeyBindState();
    }
}
