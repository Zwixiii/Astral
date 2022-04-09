package nightmare.utils.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;
import nightmare.Nightmare;
import nightmare.mixin.mixins.accessor.MinecraftAccessor;
import nightmare.utils.AccessorUtils;
import nightmare.utils.ScreenUtils;

public class BlurUtils {
	
	private static ShaderGroup blurShader;
	private static Minecraft mc = Minecraft.getMinecraft();
	private static MinecraftAccessor mcAccessor = (MinecraftAccessor)mc;
	private static Framebuffer buffer;
	
    private static float lastScale = 0;
    private static float lastScaleWidth = 0;
    private static float lastScaleHeight = 0;
    
    private static void reinitShader() {
		try {
			buffer = new Framebuffer(mc.displayWidth, mc.displayHeight, true);
			buffer.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
			blurShader = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(), new ResourceLocation("shaders/post/blurArea.json"));
			blurShader.createBindFramebuffers(mc.displayWidth, mc.displayHeight);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static void drawBlurRect(float x, float y, float width, float height) {
    	
        int factor = ScreenUtils.getScaleFactor();
        int factor2 = ScreenUtils.getWidth();
        int factor3 = ScreenUtils.getHeight();
        
        if (lastScale != factor || lastScaleWidth != factor2 || lastScaleHeight != factor3) {
            reinitShader();
        }
        
        lastScale = factor;
        lastScaleWidth = factor2;
        lastScaleHeight = factor3;
        
        ((AccessorUtils)blurShader).getListShaders().get(0).getShaderManager().getShaderUniform("BlurXY").set(x * (ScreenUtils.getScaleFactor() / 2.0F), (factor3 - height) * (ScreenUtils.getScaleFactor() / 2.0F));
        ((AccessorUtils)blurShader).getListShaders().get(1).getShaderManager().getShaderUniform("BlurXY").set(x * (ScreenUtils.getScaleFactor() / 2.0F), (factor3 - height) * (ScreenUtils.getScaleFactor() / 2.0F));
        ((AccessorUtils)blurShader).getListShaders().get(0).getShaderManager().getShaderUniform("BlurCoord").set((width - x) * (ScreenUtils.getScaleFactor() / 2.0F), (height - y) * (ScreenUtils.getScaleFactor() / 2.0F));
        ((AccessorUtils)blurShader).getListShaders().get(1).getShaderManager().getShaderUniform("BlurCoord").set((width - x) * (ScreenUtils.getScaleFactor() / 2.0F), (height - y) * (ScreenUtils.getScaleFactor() / 2.0F));
        ((AccessorUtils)blurShader).getListShaders().get(0).getShaderManager().getShaderUniform("Radius").set((int) Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("Blur"), "Radius").getValDouble());
        ((AccessorUtils)blurShader).getListShaders().get(1).getShaderManager().getShaderUniform("Radius").set((int) Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("Blur"), "Radius").getValDouble());
        blurShader.loadShaderGroup(mcAccessor.timer().renderPartialTicks);
        mc.getFramebuffer().bindFramebuffer(true);
    }
}