package nightmare.mixin.mixins.network;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S01PacketJoinGame;
import nightmare.event.impl.EventRespawn;

@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {

	@Inject(method = "handleJoinGame", at = @At("TAIL"))
	public void handleJoinGame(S01PacketJoinGame packetIn, CallbackInfo ci) {
		EventRespawn event = new EventRespawn();
		event.call();
	}
}
