package moonlight.event.impl;

import moonlight.event.Event;
import net.minecraft.network.Packet;

public class EventSendPacket extends Event{
	
    public Packet getPacket() {
		return packet;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}

	private Packet packet;

    public EventSendPacket(Packet packet) {
        this.packet = packet;
    }
}