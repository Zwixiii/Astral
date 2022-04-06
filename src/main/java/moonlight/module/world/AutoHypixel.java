package moonlight.module.world;

import moonlight.Moonlight;
import moonlight.event.EventTarget;
import moonlight.event.impl.EventReceivePacket;
import moonlight.event.impl.EventSendPacket;
import moonlight.event.impl.EventUpdate;
import moonlight.module.Category;
import moonlight.module.Module;
import moonlight.settings.Setting;
import moonlight.utils.TimerUtils;
import net.minecraft.item.Item;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.server.S02PacketChat;

public class AutoHypixel extends Module{

	private TimerUtils timer = new TimerUtils();
	
    public String playCommand = "";
    
    private boolean notification = true;
    
    private boolean autoplay = false, autogg = false;
    
	public AutoHypixel() {
		super("AutoHypixel", 0, Category.WORLD);
		
		Moonlight.instance.settingsManager.rSetting(new Setting("AutoGG", this, false));
		Moonlight.instance.settingsManager.rSetting(new Setting("AutoPlay", this, false));
		Moonlight.instance.settingsManager.rSetting(new Setting("Delay", this, 3, 0, 5, true));
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		
		int delay = (int) Moonlight.instance.settingsManager.getSettingByName(this, "Delay").getValDouble();
		
		if(this.autoplay == true) {
			if(notification == true) {
				notification = false;
			}
			if(timer.delay(1000 * delay)) {
				mc.thePlayer.sendChatMessage(playCommand);
				timer.reset();
				this.autoplay = false;
				notification = true;
			}
		}else {
			timer.reset();
		}
		
		if(this.autogg == true) {
			mc.thePlayer.sendChatMessage("/achat gg");
			this.autogg = false;
		}
	}
	
    @EventTarget
    public void onReceivePacket(EventReceivePacket event) {
        if (event.getPacket() instanceof S02PacketChat) {
            S02PacketChat chatPacket = (S02PacketChat) event.getPacket();
            String chatMessage = chatPacket.getChatComponent().getUnformattedText();
            if(chatMessage.contains("WINNER!") ||  chatMessage.contains("1st Killer -") || chatMessage.contains("Top Survivors")) {
            	this.autogg = true;
            }
            
            if(chatMessage.contains("WINNER!") ||  chatMessage.contains("1st Killer -") || chatMessage.contains("Top Survivors") || chatMessage.contains("You died!")) {
            	this.autoplay = true;
            }
        }
    }
    
    @EventTarget
    public void onSendPacket(EventSendPacket e) {
    	
        if (playCommand.startsWith("/play ")) {
            String display = playCommand.replace("/play ", "").replace("_", " ");
            boolean nextUp = true;
            String result = "";
            for (char c : display.toCharArray()) {
                if (c == ' ') {
                    nextUp = true;
                    result += " ";
                    continue;
                }
                if (nextUp) {
                    nextUp = false;
                    result += Character.toUpperCase(c);
                } else {
                    result += c;
                }
            }
        }

        if (e.getPacket() instanceof C0EPacketClickWindow) {
        	
            C0EPacketClickWindow packet = (C0EPacketClickWindow) e.getPacket();
            String itemname;
            
            if(packet.getClickedItem() == null) {
            	return;
            }
            
            itemname = packet.getClickedItem().getDisplayName();
            
            if (packet.getClickedItem().getDisplayName().startsWith("\247a")) {
                int itemID = Item.getIdFromItem(packet.getClickedItem().getItem());
                if (itemID == 381 || itemID == 368) {
                    if (itemname.contains("SkyWars")) {
                        if (itemname.contains("Doubles")) {
                            if (itemname.contains("Normal")) {
                                playCommand = "/play teams_normal";
                            } else if (itemname.contains("Insane")) {
                                playCommand = "/play teams_insane";
                            }
                        } else if (itemname.contains("Solo")) {
                            if (itemname.contains("Normal")) {
                                playCommand = "/play solo_normal";
                            } else if (itemname.contains("Insane")) {
                                playCommand = "/play solo_insane";
                            }
                        }
                    }
                } else if (itemID == 355) {
                    if (itemname.contains("Bed Wars")) {
                        if (itemname.contains("4v4")) {
                            playCommand = "/play bedwars_four_four";
                        } else if (itemname.contains("3v3")) {
                            playCommand = "/play bedwars_four_three";
                        } else if (itemname.contains("Doubles")) {
                            playCommand = "/play bedwars_eight_two";
                        } else if (itemname.contains("Solo")) {
                            playCommand = "/play bedwars_eight_one";
                        }
                    }
                }
            }
        } else if (e.getPacket() instanceof C01PacketChatMessage) {
            C01PacketChatMessage packet = (C01PacketChatMessage) e.getPacket();
            if (packet.getMessage().startsWith("/play")) {
                playCommand = packet.getMessage();
            }
        }
    }
}