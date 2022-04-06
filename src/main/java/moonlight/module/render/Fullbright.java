package moonlight.module.render;

import moonlight.module.Category;
import moonlight.module.Module;

public class Fullbright extends Module{

	public Fullbright() {
		super("Fullbright", 0, Category.RENDER);
	}

    @Override
    public void onEnable() {
        super.onEnable();
        mc.gameSettings.gammaSetting = 10F;
    }
    
    @Override
    public void onDisable() {
        mc.gameSettings.gammaSetting = 1F;
    }
}