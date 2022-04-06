package nightmare.file.position;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import nightmare.Nightmare;
import nightmare.gui.window.Window;

public class PositionManager {

	private static Minecraft mc = Minecraft.getMinecraft();
	
	public File getPositionDir() {
		return positionDir;
	}

	public void setPositionDir(File positionDir) {
		this.positionDir = positionDir;
	}

	public File getDataFile() {
		return dataFile;
	}

	public void setDataFile(File dataFile) {
		this.dataFile = dataFile;
	}

	private File positionDir;
	
	private File dataFile;
	
	public PositionManager() {
		
		positionDir = new File(mc.mcDataDir, "nightmare/config");
		dataFile = new File(positionDir, "Position.txt");
		
		if(!dataFile.exists()) {
			try {
				dataFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.load();
	}
	
	public void save() {
		
		ArrayList<String> toSave = new ArrayList<String>();
		
		for (Window window : Nightmare.instance.windowManager.getWindows()) {
			toSave.add("POS:" + window.getName() + ":" + window.getX() + ":" + window.getY() + ":" + window.getWidth() + ":" + window.getHeight());
		}
		
		try {
			PrintWriter pw = new PrintWriter(this.dataFile);
			for (String str : toSave) {
				pw.println(str);
			}
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void load() {
		
		ArrayList<String> lines = new ArrayList<String>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.dataFile));
			String line = reader.readLine();
			while (line != null) {
				lines.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (String s : lines) {
			
			String[] args = s.split(":");
			
			if (s.toLowerCase().startsWith("pos:")) {
				Window window = Nightmare.instance.windowManager.getWindowByName(args[1]);
				if (window != null) {
					window.setX(Integer.parseInt(args[2]));
					window.setY(Integer.parseInt(args[3]));
					window.setWidth(Integer.parseInt(args[4]));
					window.setHeight(Integer.parseInt(args[5]));
				}
			}
		}
	}
}