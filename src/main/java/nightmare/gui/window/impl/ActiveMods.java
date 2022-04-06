package nightmare.gui.window.impl;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import nightmare.Nightmare;
import nightmare.fonts.impl.Fonts;
import nightmare.gui.window.Window;
import nightmare.module.Module;
import nightmare.utils.ColorUtils;

public class ActiveMods extends Window{

	private FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
	
	public ActiveMods() {
		super("ActiveMods", 0, 0, 0, 0, false, false);
	}
	
	@Override
	public void onRender() {
		
        final ArrayList<Module> enabledMods = new ArrayList<Module>();
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int moduleY = 0;
        
        for (final Module m : Nightmare.instance.moduleManager.getModules()) {
            if (m.isToggled()) {
                enabledMods.add(m);
            }
        }
        
        enabledMods.sort((m1, m2) ->  Fonts.REGULAR.REGULAR_20.REGULAR_20.getStringWidth(m2.getDisplayName()) - Fonts.REGULAR.REGULAR_20.REGULAR_20.getStringWidth(m1.getDisplayName()));
        
        for (final Module m : enabledMods) {
        	
        	Fonts.REGULAR.REGULAR_20.REGULAR_20.drawString(m.getDisplayName(), sr.getScaledWidth() - Fonts.REGULAR.REGULAR_20.REGULAR_20.getStringWidth(m.getDisplayName()) - 4, 2 + moduleY * (fr.FONT_HEIGHT + 2), ColorUtils.getClientColor(), true);
        	
        	moduleY++;
        }
	}
}