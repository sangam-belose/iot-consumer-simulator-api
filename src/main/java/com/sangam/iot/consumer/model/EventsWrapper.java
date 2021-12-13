package com.sangam.iot.consumer.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Synchronized;
import org.springframework.stereotype.Component;

@Component
public class EventsWrapper {

    private List<IotEvent> events = new ArrayList<>();

    public List<IotEvent> getEvents() {
        return events;
    }

    private Object lock = new Object();

    public void addEvent(IotEvent iotEvent) {
        synchronized(lock) {
            this.events.add(iotEvent);
        }
    }
}
