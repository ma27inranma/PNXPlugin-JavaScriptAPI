package ma27inranma.javascript_api;

import org.graalvm.polyglot.HostAccess;

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

  @Override
  public String toString() {
    return "[object ApiRoot]";
  }
}
