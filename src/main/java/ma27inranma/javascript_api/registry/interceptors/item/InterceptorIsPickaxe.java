package ma27inranma.javascript_api.registry.interceptors.item;

import net.bytebuddy.implementation.bind.annotation.RuntimeType;

public class InterceptorIsPickaxe {
  public final boolean isPickaxe;

  public InterceptorIsPickaxe(boolean isPickaxe){
    this.isPickaxe = isPickaxe;
  }

  @RuntimeType
  public boolean isPickaxe(){
    return this.isPickaxe;
  }
}
