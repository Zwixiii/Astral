package nightmare.utils;

import net.minecraft.client.Minecraft;

public class ServerUtils {

	private static Minecraft mc = Minecraft.getMinecraft();
	
	public static boolean isOnServer() {
		if(mc.getCurrentServerData() != null) {
			return true;
		}
		return false;
	}
	
	public static boolean isOnHypixel() {
		if(isOnServer() && mc.getCurrentServerData().serverIP.contains("hypixel")) {
			return true;
		}
		return false;
	}
	
	public static boolean isOnSyuu() {
		if(isOnServer() && mc.getCurrentServerData().serverIP.contains("syuu")) {
			return true;
		}
		return false;
	}
}
