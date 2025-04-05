package ma27inranma.javascript_api;

import javax.annotation.Nullable;

import org.graalvm.polyglot.HostAccess;

import cn.nukkit.level.Level;
import ma27inranma.javascript_api.event.EventBus;
import ma27inranma.javascript_api.js_class.world.JsLevel;

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
  public JsLevel getLevel(String name){
    Level level = JavaScriptApiPlugin.server.getLevelByName(name);
    if(level == null) return null;

    return new JsLevel(level);
  }

  @HostAccess.Export
  public JsLevel getDefaultLevel(){
    Level level = JavaScriptApiPlugin.server.getDefaultLevel();
    return new JsLevel(level);
  }

  @Override
  public String toString() {
    return "[object ApiRoot]";
  }
}
