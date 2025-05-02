package ma27inranma.javascript_api.registry.interceptors.entity;

import cn.nukkit.entity.custom.CustomEntity;
import ma27inranma.javascript_api.registry.entity.EntityDefinition;
import net.bytebuddy.implementation.bind.annotation.This;
import org.graalvm.polyglot.*;

public class InterceptorSaveNbt {
  public final EntityDefinition entityDefinition;
  public final Value onSaveNbt;

  public InterceptorSaveNbt(EntityDefinition entityDefinition, Value onSaveNbt){
    this.entityDefinition = entityDefinition;
    this.onSaveNbt = onSaveNbt;
  }

  public void saveNbt(@This Object self){
    if(!(self instanceof CustomEntity customEntity)) throw new RuntimeException("SaveNbt is called on non-custom entity object");

    if(onSaveNbt != null && onSaveNbt.canExecute()){
      try{
        onSaveNbt.execute(customEntity);
      }catch(PolyglotException e){
        e.printStackTrace();
      }
    }
  }
}
