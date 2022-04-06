package moonlight.module.render;

import java.awt.Color;

import moonlight.Moonlight;
import moonlight.event.EventTarget;
import moonlight.event.impl.EventRender2D;
import moonlight.fonts.impl.Fonts;
import moonlight.gui.window.Window;
import moonlight.module.Category;
import moonlight.module.Module;
import moonlight.settings.Setting;
import moonlight.utils.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class HUD extends Module{

	private int stringWidth;
	
	public HUD() {
		super("HUD", 0, Category.RENDER);
		
		Moonlight.instance.settingsManager.rSetting(new Setting("ActiveMods", this, true));
		Moonlight.instance.settingsManager.rSetting(new Setting("ClientName", this, false));
		Moonlight.instance.settingsManager.rSetting(new Setting("Inventory", this, true));
		Moonlight.instance.settingsManager.rSetting(new Setting("Red", this, 0, 0, 255, true));
		Moonlight.instance.settingsManager.rSetting(new Setting("Green", this, 210, 0, 255, true));
		Moonlight.instance.settingsManager.rSetting(new Setting("Blue", this, 255, 0, 255, true));
	}

	@EventTarget
	public void onRender(EventRender2D event) {
		
		if(Moonlight.instance.settingsManager.getSettingByName(this, "ClientName").getValBoolean()) {
			String title = Moonlight.instance.getName() + " | " + Minecraft.getDebugFPS() + "fps";
			stringWidth = Fonts.REGULAR.REGULAR_18.REGULAR_18.getStringWidth(title) + 6;
			Gui.drawRect(8 - 2, 8 - 2, 8 + stringWidth + 2, 8 + 15, new Color(60, 60, 60).getRGB());
			Gui.drawRect(8, 8, 8 + stringWidth, 8 + 13, new Color(25, 25, 25).getRGB());
			Gui.drawRect(8, 8 + 12, 8 + stringWidth, 8 + 13, ColorUtils.getClientColor());
			Fonts.REGULAR.REGULAR_18.REGULAR_18.drawString(title, 8 + 3, 8 + 3, -1, true);
		}
		
		for(Window window : Moonlight.instance.windowManager.getWindows()) {
			if(Moonlight.instance.settingsManager.getSettingByName(this, window.getName()).getValBoolean()) {
				Moonlight.instance.windowManager.getWindowByName(window.getName()).onRender();
			}
		}
	}
}
