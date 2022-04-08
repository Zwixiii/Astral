package nightmare.module.misc;

import nightmare.gui.notification.NotificationManager;
import nightmare.gui.notification.NotificationType;
import nightmare.module.Category;
import nightmare.module.Module;

public class BlatantMode extends Module{

	public BlatantMode() {
		super("BlatantMode", 0, Category.MISC);
	}
	
	@Override
	public void onEnable() {
		NotificationManager.show(NotificationType.WARNING, "Module", "Not recommended to use as it is likely to be banned", 5000);
		super.onEnable();
	}
}
