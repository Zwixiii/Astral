package moonlight.event.impl;

import moonlight.event.Event;

public class EventKey extends Event {
	
    public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	private int key;

    public EventKey(int key) {
        this.key = key;
    }
}
