package moonlight.file;

import java.io.File;

import moonlight.file.config.ConfigManager;
import moonlight.file.position.PositionManager;
import net.minecraft.client.Minecraft;

public class FileManager {

	private Minecraft mc = Minecraft.getMinecraft();
	
	private File dir;
	
	private ConfigManager configManager;
	private PositionManager positionManager;
	
	public FileManager() {
		
		dir = new File(mc.mcDataDir, "moonlight");
		if(!dir.exists()) {
			dir.mkdir();
		}
		
		configManager = new ConfigManager();
		positionManager = new PositionManager();
	}
	
	public File getDir() {
		return dir;
	}

	public void setDir(File dir) {
		this.dir = dir;
	}

	public ConfigManager getConfigManager() {
		return configManager;
	}
	
	public PositionManager getPositionManager() {
		return positionManager;
	}
}
