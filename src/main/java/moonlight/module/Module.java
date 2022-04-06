package moonlight.module;

import moonlight.Moonlight;
import net.minecraft.client.Minecraft;

public class Module {
	
    protected static Minecraft mc = Minecraft.getMinecraft();

    private String name;

    private String displayName;
    
    private int key;
    
    private Category category;
    
    protected boolean toggled;
	public boolean visible = true;
	
    public Module(String name, int key, Category category) {
        this.name = name;
        this.key = key;
        this.category = category;
        toggled = false;
    }

    public void onEnable() {
    	Moonlight.instance.eventManager.register(this);
    }
    public void onDisable() {
    	Moonlight.instance.eventManager.unregister(this);
    }

    public void onToggle() {}
    public void toggle() {
        toggled = !toggled;
        onToggle();
        if(toggled) {
            onEnable();
        }
        else {
            onDisable();
        }
        if(Moonlight.instance.fileManager != null) {
        	Moonlight.instance.fileManager.getConfigManager().save();
        }
    }
    
    public boolean isToggled() {
        return toggled;
    }
    
    public boolean isDisabled() {
        return !toggled;
    }
    
    public String getDisplayName() {
        return displayName == null ? name : displayName;
    }
    
	public void setToggled(boolean toggled) {
		this.toggled = toggled;
		
		if (this.toggled) {
			this.onEnable();
		} else {
			this.onDisable();
		}
        if(Moonlight.instance.fileManager != null) {
        	Moonlight.instance.fileManager.getConfigManager().save();
        }
	}
	
	public Category getCategory() {
		return this.category;
	}
	
	public int getKey() {
		return this.key;
	}
	
	public int setKey(int key) {
		return this.key = key;
	}
	
	public String setDisplayName(String displayName) {
		return this.displayName = displayName;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String setName(String name) {
		return this.name = name;
	}
}