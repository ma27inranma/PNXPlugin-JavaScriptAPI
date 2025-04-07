package ma27inranma.javascript_api.registry.interceptors.item;

import net.bytebuddy.implementation.bind.annotation.RuntimeType;

public class InterceptorIsSword {
  public final boolean isSword;

  public InterceptorIsSword(boolean isSword){
    this.isSword = isSword;
  }

  @RuntimeType
  public boolean isSword(){
    return this.isSword;
  }
}
