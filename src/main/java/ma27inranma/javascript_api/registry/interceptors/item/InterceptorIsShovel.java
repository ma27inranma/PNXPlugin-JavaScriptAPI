package ma27inranma.javascript_api.registry.interceptors.item;

import net.bytebuddy.implementation.bind.annotation.RuntimeType;

public class InterceptorIsShovel {
  public final boolean isShovel;

  public InterceptorIsShovel(boolean isShovel){
    this.isShovel = isShovel;
  }

  @RuntimeType
  public boolean isShovel(){
    return this.isShovel;
  }
}
