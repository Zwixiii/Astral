package nightmare.gui.window;

import java.util.ArrayList;

import nightmare.gui.window.impl.Inventory;
import nightmare.gui.window.impl.TargetHUD;

public class WindowManager {
	
    private ArrayList<Window> windows = new ArrayList<Window>();
    
    public WindowManager() {
    	windows.add(new Inventory());
    	windows.add(new TargetHUD());
    }
    
    public ArrayList<Window> getWindows() {
        return windows;
    }
    
    public Window getWindowByName(String name) {
        return windows.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
