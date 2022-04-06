package moonlight.utils;

import java.awt.Color;

import moonlight.Moonlight;

public class ColorUtils {
	public static int getBackgroundColor() {
		return new Color(0, 0, 0,110).getRGB();
	}
	
	public static int getClientColor() {
		return new Color((int) Moonlight.instance.settingsManager.getSettingByName(Moonlight.instance.moduleManager.getModuleByName("HUD"), "Red").getValDouble(), (int) Moonlight.instance.settingsManager.getSettingByName(Moonlight.instance.moduleManager.getModuleByName("HUD"), "Green").getValDouble(), (int) Moonlight.instance.settingsManager.getSettingByName(Moonlight.instance.moduleManager.getModuleByName("HUD"), "Blue").getValDouble()).getRGB();
	}
}
