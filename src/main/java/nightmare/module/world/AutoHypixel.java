package nightmare.module.world;

import net.minecraft.item.Item;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.server.S02PacketChat;
import nightmare.Nightmare;
import nightmare.event.EventTarget;
import nightmare.event.impl.EventLoadWorld;
import nightmare.event.impl.EventReceivePacket;
import nightmare.event.impl.EventSendPacket;
import nightmare.event.impl.EventUpdate;
import nightmare.gui.notification.NotificationManager;
import nightmare.gui.notification.NotificationType;
import nightmare.module.Category;
import nightmare.module.Module;
import nightmare.settings.Setting;
import nightmare.utils.TimerUtils;

public class AutoHypixel extends Module{

	private TimerUtils timer = new TimerUtils();
	
    public String playCommand = "";
    
    private boolean notification = true;
    
    private boolean autoplay = false, autogg = false;
    
	public AutoHypixel() {
		super("AutoHypixel", 0, Category.WORLD);
		
		Nightmare.instance.settingsManager.rSetting(new Setting("AutoGG", this, false));
		Nightmare.instance.settingsManager.rSetting(new Setting("AutoPlay", this, false));
		Nightmare.instance.settingsManager.rSetting(new Setting("Delay", this, 3, 0, 5, true));
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		
		int delay = (int) Nightmare.instance.settingsManager.getSettingByName(this, "Delay").getValDouble();
		
		if(Nightmare.instance.settingsManager.getSettingByName(this, "AutoGG").getValBoolean()) {
			if(this.autogg == true) {
				mc.thePlayer.sendChatMessage("/achat gg");
				this.autogg = false;
			}
		}
		
		if(Nightmare.instance.settingsManager.getSettingByName(this, "AutoPlay").getValBoolean()) {
			if(this.autoplay == true) {
				if(notification == true) {
					NotificationManager.show(NotificationType.INFO, "AutoHypixel", "Sending you to next game", delay * 1000);
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
		}	
	}
	
	@EventTarget
	public void onLoadWorld(EventLoadWorld event) {
		this.autoplay = false;
		this.autogg = false;
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
            for (char c : display.toCharArray()) {
                if (c == ' ') {
                    nextUp = true;
                    continue;
                }
                if (nextUp) {
                    nextUp = false;
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