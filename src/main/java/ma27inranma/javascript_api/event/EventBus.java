package ma27inranma.javascript_api.event;

import java.util.ArrayList;
import java.util.HashMap;

import org.graalvm.polyglot.HostAccess;

import ma27inranma.javascript_api.event.event_impl.PlayerChatEvent;

public class EventBus {
  public static ArrayList<EventBus> existingEventBusses = new ArrayList<>();

  public static void emit(String eventName, Object object){
    existingEventBusses.forEach(eventBus -> {
      try{
        eventBus.getEvent(eventName).emit(object);
      }catch(Throwable t){
        t.printStackTrace();
      }
    });
  }

  public HashMap<String, Event> events = new HashMap<>();

  public EventBus(){
    events.put("PlayerChat", new PlayerChatEvent());

    existingEventBusses.add(this);
  }

  @HostAccess.Export
  public Event getEvent(String name){
    return this.events.get(name);
  }

  @Override
  public String toString() {
    return "[object EventBus]";
  }
}
