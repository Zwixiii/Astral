package nightmare.gui.notification;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;
import nightmare.fonts.impl.Fonts;
import nightmare.utils.AnimationUtils;
import nightmare.utils.ColorUtils;
import nightmare.utils.TimerUtils;
import nightmare.utils.render.RenderUtils;

public class Notification {
	
    public static Minecraft mc = Minecraft.getMinecraft();
    
    public String message;
    public String title;
    private NotificationType notificationType;
    private TimerUtils timer;
    private float animationX;
    private float animationY;
    private float width;
    private final float height;
    private int delay;
   
    private float x1, x2, y1, y2;
    
	public static float rotateDirection = 0;
	public static boolean animationDone = false;
	
	private int notificationColor;
	
    public Notification(NotificationType notificationType, String title, String message, int delay) {
    	this.notificationType = notificationType;
        this.message = message;
        this.title = title;
        this.delay = delay;
        
        if(Fonts.REGULAR.REGULAR_20.REGULAR_20.getStringWidth(title) < Fonts.REGULAR.REGULAR_16.REGULAR_16.getStringWidth(message)) {
            this.width = Fonts.REGULAR.REGULAR_16.REGULAR_16.getStringWidth(message) + 45;
        }else{
            this.width = Fonts.REGULAR.REGULAR_20.REGULAR_20.getStringWidth(title) + 45;
        }
              
        this.height = 22.0f;
        this.animationX = 140.0f;
        this.timer = new TimerUtils();
        timer.reset();
    }

    public void draw(float x, float offsetY) {
    	
        float target = isFinished() ? width : 0;

        if (animationY == 0) {
            animationY = offsetY;
        }

        if(notificationType.equals(NotificationType.INFO)) {
        	notificationColor = new Color(220, 220, 220).getRGB();
        }else if(notificationType.equals(NotificationType.SUCCESS)) {
        	notificationColor = new Color(80, 200, 150).getRGB();
        }else if(notificationType.equals(NotificationType.WARNING)) {
        	notificationColor = new Color(220, 220, 100).getRGB();
        }else if(notificationType.equals(NotificationType.ERROR)) {
        	notificationColor = new Color(250, 55, 55).getRGB();
        }
        
        animationX = AnimationUtils.setAnimation(this.animationX, target, (Math.max(10, (Math.abs(this.animationX - (target))) * 40) * 0.4f));
        animationY = AnimationUtils.setAnimation(animationY, offsetY, (Math.max(10, (Math.abs(animationY - (offsetY))) * 40) * 0.3f));

        x1 = x - width + this.animationX;
        x2 = x + animationX + 0;

        y1 = animationY - 2;
        y2 = y1 + height;
        
        float f1 = MathHelper.clamp_float(timer.getCurrentMS() - timer.getLastMS(), 0, delay);
        float f2 = f1 / delay;
        
        RenderUtils.drawRect(x1 + 35, y1, x2, y2, ColorUtils.getBackgroundColor());
        RenderUtils.drawRect(x1 + 35, y2 - 1, (x1 + 35) + (f2 * (width - 29)), y2, notificationColor);
        Fonts.REGULAR.REGULAR_20.REGULAR_20.drawString(this.title, x1 + 40, y1 + 3, -1);
        Fonts.REGULAR.REGULAR_16.REGULAR_16.drawString(this.message, x1 + 40, y1 + 14, -1);
    }

    public boolean shouldDelete() {
        return isFinished() && this.animationX == width;
    }

    public float getHeight() {
        return height;
    }

    private boolean isFinished() {
        return timer.delay(delay);
    }
}
