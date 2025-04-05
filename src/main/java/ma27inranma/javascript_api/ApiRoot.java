package ma27inranma.javascript_api;

import javax.annotation.Nullable;

import org.graalvm.polyglot.HostAccess;

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

  @Override
  public String toString() {
    return "[object ApiRoot]";
  }
}
