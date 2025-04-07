package ma27inranma.javascript_api.registry.interceptors.item;

import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.item.customitem.ItemCustom;
import cn.nukkit.item.customitem.CustomItemDefinition.SimpleBuilder;
import ma27inranma.javascript_api.registry.CustomItemDefinitionInfo;
import net.bytebuddy.asm.Advice.This;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

public class InterceptorGetDefinition {
  public final CustomItemDefinitionInfo info;

  public InterceptorGetDefinition(CustomItemDefinitionInfo info){
    this.info = info;
  }

  public CustomItemDefinition getDefinition(@net.bytebuddy.implementation.bind.annotation.This ItemCustom self){ // NOT OTHER @THIS. ONLY THIS WILL WORK
    SimpleBuilder builder = CustomItemDefinition.simpleBuilder((ItemCustom) self);
    builder.name(info.name);
    builder.creativeCategory(info.creativeCategory);
    builder.texture(info.texture);

    return builder.build();
  }
}
