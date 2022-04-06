package nightmare.mixin.mixins.block;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import nightmare.event.impl.EventBoundingBox;

@Mixin(Block.class)
public abstract class MixinBlock {

	@Shadow
	public abstract AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state);
	
	@Overwrite
	public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
        AxisAlignedBB axisalignedbb = this.getCollisionBoundingBox(worldIn, pos, state);

        EventBoundingBox event = new EventBoundingBox(((Block)(Object)this), pos, this.getCollisionBoundingBox(worldIn, pos, state));
        
        if (collidingEntity == Minecraft.getMinecraft().thePlayer) {
        	event.call();
        	
        	if(event.isCancelled()) {
        		return;
        	}

            axisalignedbb = event.getBoundingBox();
        }
        
        if (axisalignedbb != null && mask.intersectsWith(axisalignedbb))
        {
            list.add(axisalignedbb);
        }
	}
}
