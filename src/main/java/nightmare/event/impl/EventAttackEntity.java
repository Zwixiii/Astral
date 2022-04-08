package nightmare.event.impl;

import net.minecraft.entity.Entity;
import nightmare.event.Event;

public class EventAttackEntity extends Event{
	
	public Entity entity;
	
	public EventAttackEntity(Entity entity) {
		this.entity = entity;
	}
}
