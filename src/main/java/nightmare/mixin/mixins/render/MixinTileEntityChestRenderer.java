package nightmare.mixin.mixins.render;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
import nightmare.Nightmare;

@Mixin(TileEntityChestRenderer.class)
public class MixinTileEntityChestRenderer {

    @Inject(method = "renderTileEntityAt", at = @At("HEAD"))
    private void startChams(CallbackInfo ci) {
        if (Nightmare.instance.moduleManager.getModuleByName("Chams").isToggled() && Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("Chams"), "Chest").getValBoolean()) {
            GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
            GL11.glPolygonOffset(1.0F, -1000000F);
        }
    }

    @Inject(method = "renderTileEntityAt", at = @At("RETURN"))
    private void stopChams(CallbackInfo ci) {
        if (Nightmare.instance.moduleManager.getModuleByName("Chams").isToggled() && Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("Chams"), "Chest").getValBoolean()) {
            GL11.glPolygonOffset(1.0F, 1000000F);
            GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
        }
    }
}
