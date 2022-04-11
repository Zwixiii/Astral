package nightmare.module.render;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import nightmare.Nightmare;
import nightmare.module.Category;
import nightmare.module.Module;
import nightmare.settings.Setting;

public class ClickGUI extends Module{

	public ClickGUI() {
		super("ClickGUI", Keyboard.KEY_RSHIFT, Category.RENDER);
		
		ArrayList<String> options = new ArrayList<>();
		
		options.add("Classic");
		options.add("New");
		
		Nightmare.instance.settingsManager.rSetting(new Setting("Visual", this, "Classic", options));
	}

    @Override
    public void onEnable() {
        super.onEnable();
    	mc.displayGuiScreen(Nightmare.instance.clickGUI);
        this.setToggled(false);
    }
}