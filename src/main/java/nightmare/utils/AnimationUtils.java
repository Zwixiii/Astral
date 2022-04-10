package nightmare.utils;

public class AnimationUtils {

	public static double delta;
	
	public static float setAnimation(float animation, float finalState, float speed) {
		
		float add = (float) (delta * (speed / 1000f));
		
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
