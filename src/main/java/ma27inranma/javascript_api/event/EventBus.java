package ma27inranma.javascript_api.event;

import java.util.ArrayList;
import java.util.HashMap;

import org.graalvm.polyglot.HostAccess;

import ma27inranma.javascript_api.event.event_impl.PlayerChatEvent;
import ma27inranma.javascript_api.event.event_impl.RegistriesEvent;

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

  public static void reload(){
    existingEventBusses = new ArrayList<>();
  }

  public HashMap<String, Event> events = new HashMap<>();

  public EventBus(){
    events.put("PlayerChat", new PlayerChatEvent());
    events.put("Registries", new RegistriesEvent());
    events.put("BlockBreak", new Event());
    events.put("BlockPlace", new Event());
    events.put("PlayerInteract", new Event());
    events.put("EntityDamagedByEntity", new Event());
    events.put("EntityDamagedByBlock", new Event());
    events.put("EntityDamage", new Event());
    events.put("Loaded", new Event()); // onEnable
    events.put("Unload", new Event()); // onDisable

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
