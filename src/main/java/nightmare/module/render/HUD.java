package nightmare.module.render;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import nightmare.Nightmare;
import nightmare.event.EventTarget;
import nightmare.event.impl.EventRender2D;
import nightmare.fonts.impl.Fonts;
import nightmare.gui.notification.NotificationManager;
import nightmare.gui.window.Window;
import nightmare.module.Category;
import nightmare.module.Module;
import nightmare.settings.Setting;
import nightmare.utils.ColorUtils;
import nightmare.utils.ScreenUtils;

public class HUD extends Module{

	private int stringWidth;
	
	public HUD() {
		super("HUD", 0, Category.RENDER);
		
		Nightmare.instance.settingsManager.rSetting(new Setting("ActiveMods", this, true));
		Nightmare.instance.settingsManager.rSetting(new Setting("ClientName", this, false));
		Nightmare.instance.settingsManager.rSetting(new Setting("Inventory", this, true));
		Nightmare.instance.settingsManager.rSetting(new Setting("Notification", this, true));
		Nightmare.instance.settingsManager.rSetting(new Setting("TargetHUD", this, true));
		Nightmare.instance.settingsManager.rSetting(new Setting("Distance", this, 6.5F, 3, 15, false));
		Nightmare.instance.settingsManager.rSetting(new Setting("Red", this, 0, 0, 255, true));
		Nightmare.instance.settingsManager.rSetting(new Setting("Green", this, 210, 0, 255, true));
		Nightmare.instance.settingsManager.rSetting(new Setting("Blue", this, 255, 0, 255, true));
	}

	@EventTarget
	public void onRender(EventRender2D event) {
		
		if(Nightmare.instance.settingsManager.getSettingByName(this, "ClientName").getValBoolean()) {
			String title = Nightmare.instance.getName() + " | " + Minecraft.getDebugFPS() + "fps";
			stringWidth = Fonts.REGULAR.REGULAR_18.REGULAR_18.getStringWidth(title) + 6;
			Gui.drawRect(8 - 2, 8 - 2, 8 + stringWidth + 2, 8 + 15, new Color(60, 60, 60).getRGB());
			Gui.drawRect(8, 8, 8 + stringWidth, 8 + 13, new Color(25, 25, 25).getRGB());
			Gui.drawRect(8, 8 + 12, 8 + stringWidth, 8 + 13, ColorUtils.getClientColor());
			Fonts.REGULAR.REGULAR_18.REGULAR_18.drawString(title, 8 + 3, 8 + 3, -1, true);
		}
		
		if(Nightmare.instance.settingsManager.getSettingByName(this, "Notification").getValBoolean()) {
			NotificationManager.doRender(ScreenUtils.getWidth(), ScreenUtils.getHeight());
		}
		
		for(Window window : Nightmare.instance.windowManager.getWindows()) {
			if(Nightmare.instance.settingsManager.getSettingByName(this, window.getName()).getValBoolean()) {
				Nightmare.instance.windowManager.getWindowByName(window.getName()).onRender();
			}
		}
	}
}
