package nightmare.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import nightmare.Nightmare;

public class ChatUtils {

    public static void sendPrivateChatMessage(String message) {
    	
    	message = "[" + Nightmare.instance.getName() + "]" +  " " + message;
    	
    	Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
    } 
}
