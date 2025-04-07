package ma27inranma.javascript_api.registry.interceptors.item;

import net.bytebuddy.implementation.bind.annotation.RuntimeType;

public class InterceptorIsHoe {
  public final boolean isHoe;

  public InterceptorIsHoe(boolean isHoe){
    this.isHoe = isHoe;
  }

  @RuntimeType
  public boolean isHoe(){
    return this.isHoe;
  }
}
