package ma27inranma.javascript_api.js_class;

import org.graalvm.polyglot.Value;

import cn.nukkit.scheduler.TaskHandler;
import ma27inranma.javascript_api.JavaScriptApiPlugin;

public class JsScheduler {
  public void runTimeout(Value executable, int ticks){
    if(!executable.canExecute()){
      throw new IllegalArgumentException("callback is not executable");
    }

    JavaScriptApiPlugin.server.getScheduler().scheduleDelayedTask(() -> {
      executable.execute();
    }, ticks);
  }

  public TaskHandler runInterval(Value executable, int interval){
    if(!executable.canExecute()){
      throw new IllegalArgumentException("callback is not executable");
    }

    return JavaScriptApiPlugin.server.getScheduler().scheduleRepeatingTask(() -> {
      executable.execute();
    }, interval);
  }
}
