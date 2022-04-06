package nightmare.event.impl;

import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import nightmare.event.Event;

public class EventBoundingBox extends Event{
	
	public Block block;
	
	public BlockPos pos;

	public AxisAlignedBB boundingBox;

	public EventBoundingBox(Block block, BlockPos pos, AxisAlignedBB boundingBox) {
		this.block = block;
		this.pos = pos;
		this.boundingBox = boundingBox;
	}
	
	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public BlockPos getPos() {
		return pos;
	}

	public void setPos(BlockPos pos) {
		this.pos = pos;
	}

	public AxisAlignedBB getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(AxisAlignedBB boundingBox) {
		this.boundingBox = boundingBox;
	}
}
