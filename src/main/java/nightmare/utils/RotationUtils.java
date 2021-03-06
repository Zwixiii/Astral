package nightmare.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.util.MathHelper;

public class RotationUtils {
	
	private static Minecraft mc = Minecraft.getMinecraft();

    public static boolean isVisibleFOV(final Entity e, final float fov) {
        return ((Math.abs(RotationUtils.getRotations(e)[0] - mc.thePlayer.rotationYaw) % 360.0f > 180.0f) ? (360.0f - Math.abs(RotationUtils.getRotations(e)[0] - mc.thePlayer.rotationYaw) % 360.0f) : (Math.abs(RotationUtils.getRotations(e)[0] - mc.thePlayer.rotationYaw) % 360.0f)) <= fov;
    }
    
    private static float[] getRotations(final Entity entity) {
        if (entity == null) {
            return null;
        }
        final double diffX = entity.posX - mc.thePlayer.posX;
        final double diffZ = entity.posZ - mc.thePlayer.posZ;
        double diffY;
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase elb = (EntityLivingBase) entity;
            diffY = elb.posY + (elb.getEyeHeight()) - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        } else {
            diffY = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0 - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        }
        final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        final float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f;
        final float pitch = (float) (-(Math.atan2(diffY, dist) * 180.0 / Math.PI));
        return new float[]{yaw, pitch};
    }
    
	public static float[] getAngles(Entity entity) {
		double x = entity.posX - mc.thePlayer.posX;
		double z = entity.posZ - mc.thePlayer.posZ;
		double y = entity instanceof EntityEnderman ? entity.posY - mc.thePlayer.posY
				: entity.posY + ((double) entity.getEyeHeight() - 1.9) - mc.thePlayer.posY
						+ ((double) mc.thePlayer.getEyeHeight() - 1.9);
		double helper = MathHelper.sqrt_double(x * x + z * z);
		float newYaw = (float) Math.toDegrees(-Math.atan(x / z));
		float newPitch = (float) (-Math.toDegrees(Math.atan(y / helper)));
		if (z < 0.0 && x < 0.0) {
			newYaw = (float) (90.0 + Math.toDegrees(Math.atan(z / x)));
		} else if (z < 0.0 && x > 0.0) {
			newYaw = (float) (-90.0 + Math.toDegrees(Math.atan(z / x)));
		}
		return new float[] { newPitch, newYaw };
	}
	
	public static float getRotation(float currentRotation, float targetRotation, float maxIncrement) {
		float deltaAngle = MathHelper.wrapAngleTo180_float(targetRotation - currentRotation);
		if (deltaAngle > maxIncrement) {
			deltaAngle = maxIncrement;
		}
		if (deltaAngle < -maxIncrement) {
			deltaAngle = -maxIncrement;
		}
		return currentRotation + deltaAngle / 2.0f;
	}
	
    public static float getTrajAngleSolutionLow(float d3, float d1, float velocity) {
        float g = 0.006F;
        float sqrt = velocity * velocity * velocity * velocity - g * (g * (d3 * d3) + 2.0F * d1 * (velocity * velocity));
        return (float) Math.toDegrees(Math.atan((velocity * velocity - Math.sqrt(sqrt)) / (g * d3)));
    }
    
    public static float getYawToEntity(final Entity e) {
        return ((Math.abs(RotationUtils.getRotations(e)[0] - mc.thePlayer.rotationYaw) % 360.0f > 180.0f) ? (360.0f - Math.abs(RotationUtils.getRotations(e)[0] - mc.thePlayer.rotationYaw) % 360.0f) : (Math.abs(RotationUtils.getRotations(e)[0] - mc.thePlayer.rotationYaw) % 360.0f));
    }
}
