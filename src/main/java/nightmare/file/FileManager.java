package nightmare.file;

import java.io.File;

import net.minecraft.client.Minecraft;
import nightmare.file.config.ConfigManager;
import nightmare.file.position.PositionManager;

public class FileManager {

	private Minecraft mc = Minecraft.getMinecraft();
	
	private File dir;
	
	private ConfigManager configManager;
	private PositionManager positionManager;
	
	public FileManager() {
		
		dir = new File(mc.mcDataDir, "nightmare");
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
