package moonlight.module.render;

import org.lwjgl.input.Keyboard;

import moonlight.Moonlight;
import moonlight.module.Category;
import moonlight.module.Module;

public class ClickGUI extends Module{

	public ClickGUI() {
		super("ClickGUI", Keyboard.KEY_RSHIFT, Category.RENDER);
	}

    @Override
    public void onEnable() {
        super.onEnable();
    	mc.displayGuiScreen(Moonlight.instance.clickGUI);
        this.setToggled(false);
    }
}