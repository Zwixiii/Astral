package nightmare.gui.notification;

import net.minecraft.client.Minecraft;
import nightmare.fonts.impl.Fonts;
import nightmare.utils.ColorUtils;
import nightmare.utils.TimerUtils;
import nightmare.utils.render.RenderUtils;

public class Notification {
    public static Minecraft mc = Minecraft.getMinecraft();

    public boolean isClassicNotification;
    public String message;
    public String title;
    TimerUtils timer;
    private float animationX;
    private float animationY;
    private float width;
    private final float height;
    private int delay;
   
    private float x1, x2, y1, y2;
    
	public static float rotateDirection = 0;
	public static boolean animationDone = false;

	public static double delta;
	
    public Notification(String title, String message, int delay) {

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

        animationX = Notification.animation(this.animationX, target, (Math.max(10, (Math.abs(this.animationX - (target))) * 40) * 0.4f));
        animationY = Notification.animation(animationY, offsetY, (Math.max(10, (Math.abs(animationY - (offsetY))) * 40) * 0.3f));

        x1 = x - width + this.animationX;
        x2 = x + animationX + 0;

        y1 = animationY - 2;
        y2 = y1 + height;

        RenderUtils.drawRect(x1 + 35, y1, x2, y2, ColorUtils.getBackgroundColor());
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

	public static float animation(float animation, float finalState, float speed) {
		final float add = (float) (delta * (speed / 1000f));
		if (animation < finalState) {
			if (animation + add < finalState) {
				animation += add;
			} else {
				animation = finalState;
			}
		} else if (animation - add > finalState) {
			animation -= add;
		} else {
			animation = finalState;
		}
		return animation;
	}
}
