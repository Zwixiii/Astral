package moonlight.gui.window;

import java.util.ArrayList;

import moonlight.gui.window.impl.ActiveMods;
import moonlight.gui.window.impl.Inventory;

public class WindowManager {
	
    private ArrayList<Window> windows = new ArrayList<Window>();
    
    public WindowManager() {
    	windows.add(new ActiveMods());
    	windows.add(new Inventory());
    }
    
    public ArrayList<Window> getWindows() {
        return windows;
    }
    
    public Window getWindowByName(String name) {
        return windows.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
