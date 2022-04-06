package nightmare.gui.window.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import nightmare.fonts.impl.Fonts;
import nightmare.gui.window.Window;
import nightmare.utils.ColorUtils;

public class Inventory extends Window{

	private static Minecraft mc = Minecraft.getMinecraft();
	
	public Inventory() {
		super("Inventory", 30, 30, 180, 60, true, true);
	}
	
	@Override
	public void onRender() {
        int startX = this.getX() + 2;
        int startY = this.getY() + 2;
        int curIndex = 0;
        Gui.drawRect(this.getX(), this.getY(), this.getWidth(), this.getHeight(), ColorUtils.getBackgroundColor());
        Gui.drawRect(this.getX(), this.getY() - 14, this.getWidth(), this.getY(), ColorUtils.getClientColor());
        
		Fonts.REGULAR.REGULAR_20.REGULAR_20.drawString("Inventory", this.getX() + 4, this.getY() - 10, -1, false);
        for(int i = 9; i < 36; ++i) {
            ItemStack slot = mc.thePlayer.inventory.mainInventory[i];
            if(slot == null) {
                startX += 20;
                curIndex += 1;

                if(curIndex > 8) {
                    curIndex = 0;
                    startY += 20;
                    startX = this.getX() + 2;
                }

                continue;
            }

            this.drawItemStack(slot, startX, startY);
            startX += 20;
            curIndex += 1;
            if(curIndex > 8) {
                curIndex = 0;
                startY += 20;
                startX = this.getX() + 2;
            }
        }
	}
	
    private void drawItemStack(ItemStack stack, int x, int y) {
        GlStateManager.pushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.disableAlpha();
        GlStateManager.clear(256);
        mc.getRenderItem().zLevel = -150.0F;
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        mc.getRenderItem().renderItemIntoGUI(stack, x, y);
        mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRendererObj, stack, x, y, null);
        mc.getRenderItem().zLevel = 0.0F;
        GlStateManager.enableAlpha();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();
    }
}