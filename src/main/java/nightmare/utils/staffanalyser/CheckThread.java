package nightmare.utils.staffanalyser;

import java.net.URL;

import com.google.gson.Gson;

import nightmare.Nightmare;
import nightmare.gui.notification.NotificationManager;
import nightmare.gui.notification.NotificationType;
import nightmare.module.world.StaffAnalyser;
import nightmare.utils.ChatUtils;

public class CheckThread extends Thread
{
    int lastBannedCount = 0;
    
    @Override
    public void run() {
        while (true) {
            try {
                while (true) {
                    if (StaffAnalyser.key == null) {
                        Thread.sleep(1000L);
                    }
                    else {
                        Thread.sleep((long)(Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("StaffAnalyser"), "Delay").getValDouble() * 1000.0));
                        final String result = HttpUtils.performGetRequest(new URL("https://api.hypixel.net/watchdogStats?key=" + StaffAnalyser.key));
                        final Gson gson = new Gson();
                        final BanQuantityListJSON banQuantityListJSON = (BanQuantityListJSON)gson.fromJson(result, (Class<?>)BanQuantityListJSON.class);
                        final int staffTotal = banQuantityListJSON.getStaffTotal();
                        if (this.lastBannedCount == 0) {
                            this.lastBannedCount = staffTotal;
                        }
                        else {
                            final int banned = staffTotal - this.lastBannedCount;
                            this.lastBannedCount = staffTotal;
                            if (banned > 1) {
                                if (Nightmare.instance.moduleManager.getModuleByName("HUD").isToggled() && Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("HUD"), "Notification").getValBoolean()) {
                                	if(banned >= 5) {
                                        NotificationManager.show(NotificationType.ERROR, "StaffAnalyser", "Staff banned " + banned + " players in " + Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("StaffAnalyser"), "Delay").getValDouble() + "s.", 10000);
                                	}else {
                                        NotificationManager.show(NotificationType.WARNING, "StaffAnalyser", "Staff banned " + banned + " players in " + Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("StaffAnalyser"), "Delay").getValDouble() + "s.", 10000);
                                	}
                                }
                                else {
                                    ChatUtils.sendPrivateChatMessage("ÅòcStaff banned " + banned + " players in " + Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("StaffAnalyser"), "Delay").getValDouble() + "s.");
                                }
                            }
                            else if (Nightmare.instance.moduleManager.getModuleByName("HUD").isToggled() && Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("HUD"), "Notification").getValBoolean()) {
                                NotificationManager.show(NotificationType.SUCCESS, "StaffAnalyser", "Staff didn't ban any player in " + Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("StaffAnalyser"), "Delay").getValDouble() + "s.", 10000);
                            }
                            else {
                            	ChatUtils.sendPrivateChatMessage("ÅòaStaff didn't ban any player in " + Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("StaffAnalyser"), "Delay").getValDouble() + "s.");
                            }
                        }
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}