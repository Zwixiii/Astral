package nightmare.event.impl;

import nightmare.event.Event;

public class EventRender3D extends Event{
	
    public float partialTicks;

    public EventRender3D(float partialTicks)
    {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks()
    {
        return partialTicks;
    }
}
