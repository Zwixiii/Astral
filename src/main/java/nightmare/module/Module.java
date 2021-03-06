package nightmare.module;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import nightmare.Nightmare;
import nightmare.gui.notification.NotificationManager;
import nightmare.gui.notification.NotificationType;

public class Module {
	
    protected static Minecraft mc = Minecraft.getMinecraft();

    private String name;

    private String displayName;
    
    private int key;
    
    private Category category;
    
    protected boolean toggled, blatantModule;
    
	public boolean visible = true;
	
    public Module(String name, int key, Category category) {
        this.name = name;
        this.key = key;
        this.category = category;
        toggled = false;
        blatantModule = false;
    }

    public void onEnable() {
    	Nightmare.instance.eventManager.register(this);
    }
    public void onDisable() {
    	Nightmare.instance.eventManager.unregister(this);
    }

    public void onToggle() {}
    public void toggle() {
        toggled = !toggled;
        onToggle();
        if(toggled) {
        	if(isBlatantModule() && Nightmare.instance.moduleManager.getModuleByName("BlatantMode").isDisabled()) {
        		setToggled(false);
        		NotificationManager.show(NotificationType.ERROR, "Module", "This is BlatantModule!, Please toggle BlatantMode", 3000);
        	}else {
                onEnable();
                NotificationManager.show(NotificationType.SUCCESS,"Module", EnumChatFormatting.GREEN + "Enable " + EnumChatFormatting.WHITE + name, 2500);
        	}
        }
        else {
            onDisable();
            NotificationManager.show(NotificationType.ERROR,"Module", EnumChatFormatting.RED + "Disable " + EnumChatFormatting.WHITE + name, 2500);
        }
        if(Nightmare.instance.fileManager != null) {
        	Nightmare.instance.fileManager.getConfigManager().save();
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
        if(Nightmare.instance.fileManager != null) {
        	Nightmare.instance.fileManager.getConfigManager().save();
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
	
	public boolean setBlatantModule(boolean blatant) {
		return this.blatantModule = blatant;
	}
	
	public boolean isBlatantModule() {
		return this.blatantModule;
	}
}