package nightmare;

import nightmare.clickgui.ClickGUI;
import nightmare.event.EventManager;
import nightmare.event.EventTarget;
import nightmare.event.impl.EventKey;
import nightmare.file.FileManager;
import nightmare.fonts.api.FontManager;
import nightmare.fonts.impl.SimpleFontManager;
import nightmare.gui.window.WindowManager;
import nightmare.module.ModuleManager;
import nightmare.settings.SettingsManager;

public class Nightmare {
	
	public static Nightmare instance = new Nightmare();
	
	private String name = "Nightmare", version = "1.3";
	
	public SettingsManager settingsManager;
	public EventManager eventManager;
	public ModuleManager moduleManager;
	public ClickGUI clickGUI;
	public WindowManager windowManager;
	public FileManager fileManager;
	
	public FontManager fontManager = SimpleFontManager.create();
	
	public void startClient() {
		settingsManager = new SettingsManager();
		eventManager = new EventManager();
		moduleManager = new ModuleManager();
		clickGUI = new ClickGUI();
		windowManager = new WindowManager();
		fileManager = new FileManager();
		
		eventManager.register(this);
	}
	
	public void stopClient() {
		eventManager.unregister(this);
	}
	
    @EventTarget
    public void onKey(EventKey event) {
        moduleManager.getModules().stream().filter(module -> module.getKey() == event.getKey()).forEach(module -> module.toggle());
    }
    
	public String getName() {
		return this.name;
	}
	
	public String getVersion() {
		return this.version;
	}
}
