package ma27inranma.javascript_api.event;

import java.util.ArrayList;

import org.graalvm.polyglot.*;

public class Event {
  public final ArrayList<Value> listeners = new ArrayList<>();

  @HostAccess.Export
  public void subscribe(Value callback){
    if(!callback.canExecute()){
      throw new IllegalArgumentException("What you are trying to subscribe is not a function.");
    }

    this.listeners.add(callback);
  }

  public void emit(Object data){
    for (Value listener : new ArrayList<>(this.listeners)) {
      try{
        listener.execute(data);
      }catch(PolyglotException e){
        if(e.getMessage().equals("Context execution was cancelled.")){
          this.listeners.remove(listener);
          return;
        }

        e.printStackTrace();
      }
    }
  }
}
