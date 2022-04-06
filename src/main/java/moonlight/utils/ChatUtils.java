package moonlight.utils;

import moonlight.Moonlight;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ChatUtils {

    public static void sendPrivateChatMessage(String message) {
    	
    	message = "[" + Moonlight.instance.getName() + "]" +  " " + message;
    	
    	Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
    } 
}
