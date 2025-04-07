package ma27inranma.javascript_api.registry.interceptors.item;

import net.bytebuddy.implementation.bind.annotation.RuntimeType;

public class InterceptorIsAxe {
  public final boolean isAxe;

  public InterceptorIsAxe(boolean isAxe){
    this.isAxe = isAxe;
  }

  @RuntimeType
  public boolean isAxe(){
    return this.isAxe;
  }
}
