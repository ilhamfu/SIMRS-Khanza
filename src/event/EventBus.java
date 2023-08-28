/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hp2k
 */
public class EventBus {

    private final Map<Class<? extends AbstractEvent>, List<EventListener<? extends AbstractEvent>>> listenersMap = new HashMap<>();

    public <T extends AbstractEvent> void subscribe(Class<T> eventType, EventListener<T> listener) {
        listenersMap.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
    }

    public <T extends AbstractEvent> void unsubscribe(Class<T> eventType, EventListener<T> listener) {
        List<EventListener<? extends AbstractEvent>> listeners = listenersMap.get(eventType);
        if (listeners != null) {
            listeners.remove(listener);
        }
    }

    public <T extends AbstractEvent> void publish(T event) {
        Class<? extends AbstractEvent> eventType = event.getClass();
        List<EventListener<? extends AbstractEvent>> listeners = listenersMap.get(eventType);
        if (listeners != null) {
            for (EventListener<? extends AbstractEvent> listener : listeners) {
                EventListener<T> typedListener = (EventListener<T>) listener;
                typedListener.onEvent(event);
            }
        }
    }
}
