package moonlight;

import moonlight.clickgui.ClickGUI;
import moonlight.event.EventManager;
import moonlight.event.EventTarget;
import moonlight.event.impl.EventKey;
import moonlight.file.FileManager;
import moonlight.fonts.api.FontManager;
import moonlight.fonts.impl.SimpleFontManager;
import moonlight.gui.window.WindowManager;
import moonlight.module.ModuleManager;
import moonlight.settings.SettingsManager;

public class Moonlight {
	
	public static Moonlight instance = new Moonlight();
	
	private String name = "Moonlight", version = "1.0";
	
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
