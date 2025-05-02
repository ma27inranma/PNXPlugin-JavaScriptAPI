package ma27inranma.javascript_api.registry.interceptors.entity;

import cn.nukkit.entity.custom.CustomEntity;
import ma27inranma.javascript_api.registry.entity.EntityDefinition;
import net.bytebuddy.implementation.bind.annotation.This;
import org.graalvm.polyglot.*;

public class InterceptorInitEntity {
  public final EntityDefinition entityDefinition;
  public final Value onInitEntity;

  public InterceptorInitEntity(EntityDefinition entityDefinition, Value onInitEntity){
    this.entityDefinition = entityDefinition;
    this.onInitEntity = onInitEntity;
  }

  public void initEntity(@This Object self){
    if(!(self instanceof CustomEntity customEntity)) throw new RuntimeException("InitEntity is called on non-custom entity object");

    if(onInitEntity != null && onInitEntity.canExecute()){
      try{
        onInitEntity.execute(customEntity);
      }catch(PolyglotException e){
        e.printStackTrace();
      }
    }
  }
}
