package ma27inranma.javascript_api;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import org.graalvm.polyglot.HostAccess;

import cn.nukkit.Server;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.level.Level;
import ma27inranma.javascript_api.event.EventBus;

public class ApiRoot {
  public final EventBus eventBus;

  public ApiRoot(){
    this.eventBus = new EventBus();
  }

  @HostAccess.Export
  public EventBus getEvents(){
    return this.eventBus;
  }

  @HostAccess.Export
  @Nullable
  public Level getLevel(String name){
    Level level = JavaScriptApiPlugin.server.getLevelByName(name);
    if(level == null) return null;

    return level;
  }

  @HostAccess.Export
  public Level getDefaultLevel(){
    Level level = JavaScriptApiPlugin.server.getDefaultLevel();
    return level;
  }

  @HostAccess.Export
  public Server getServer(){
    return JavaScriptApiPlugin.server;
  }

  @HostAccess.Export
  public Map<String, PlayerInteractEvent.Action> getInteractActionEnum(){
    HashMap<String, PlayerInteractEvent.Action> map = new HashMap<>();
    map.put("LEFT_CLICK_BLOCK", PlayerInteractEvent.Action.LEFT_CLICK_BLOCK);
    map.put("LEFT_CLICK_AIR", PlayerInteractEvent.Action.LEFT_CLICK_AIR);
    map.put("RIGHT_CLICK_BLOCK", PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK);
    map.put("RIGHT_CLICK_AIR", PlayerInteractEvent.Action.RIGHT_CLICK_AIR);
    map.put("PHYSICAL", PlayerInteractEvent.Action.PHYSICAL);

    return map;
  }

  @Override
  public String toString() {
    return "[object ApiRoot]";
  }
}
