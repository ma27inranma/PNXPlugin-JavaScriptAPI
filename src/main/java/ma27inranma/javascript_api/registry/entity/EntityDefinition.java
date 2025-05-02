package ma27inranma.javascript_api.registry.entity;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.graalvm.polyglot.Value;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockProperties;
import cn.nukkit.block.BlockSolid;
import cn.nukkit.block.BlockState;
import cn.nukkit.block.BlockTransparent;
import cn.nukkit.block.customblock.CustomBlock;
import cn.nukkit.block.property.type.BlockPropertyType;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.custom.CustomEntity;
import cn.nukkit.entity.mob.EntityMob;
import cn.nukkit.level.format.IChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.registry.RegisterException;
import ma27inranma.javascript_api.JavaScriptApiPlugin;
import ma27inranma.javascript_api.registry.interceptors.entity.InterceptorInitEntity;
import ma27inranma.javascript_api.registry.interceptors.entity.InterceptorSaveNbt;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.field.FieldDescription;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.Implementation.Context;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.implementation.bytecode.Duplication;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.TypeCreation;
import net.bytebuddy.implementation.bytecode.collection.ArrayAccess;
import net.bytebuddy.implementation.bytecode.collection.ArrayFactory;
import net.bytebuddy.implementation.bytecode.constant.ClassConstant;
import net.bytebuddy.implementation.bytecode.constant.IntegerConstant;
import net.bytebuddy.implementation.bytecode.constant.TextConstant;
import net.bytebuddy.implementation.bytecode.member.FieldAccess;
import net.bytebuddy.implementation.bytecode.member.MethodInvocation;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.jar.asm.Type;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class EntityDefinition {
  public static EntityDefinition newDefinition(String typeId, Map<String, Object> obj) throws RegisterException{
    EntityDefinition definition = new EntityDefinition();

    definition.typeId = typeId;

    if(obj.get("entityType") instanceof String entityType){
      switch (entityType) {
        case "Entity" -> {
          definition.entityType = Entity.class;
        }
        case "Mob" -> {
          definition.entityType = EntityMob.class;
        }
        default -> {
          throw new RegisterException("Expected any of Entity, Mob (at entityType)");
        }
      }
    }

    if(obj.get("onInitEntity") instanceof Value onInitEntity){
      definition.onInitEntity = onInitEntity;
    }

    if(obj.get("onSaveNbt") instanceof Value onSaveNbt){
      definition.onSaveNbt = onSaveNbt;
    }

    if(obj.get("height") instanceof Number height){
      definition.height = height.floatValue();
    }

    if(obj.get("width") instanceof Number width){
      definition.width = width.floatValue();
    }

    return definition;
  }

  public String typeId = "jsapi:null";

  public Class<? extends Entity> entityType = Entity.class;
  public Value onInitEntity;
  public Value onSaveNbt;
  public float height = 0.1f;
  public float width = 0.1f;
  public boolean canBePushed = false;

  public EntityDefinition(){

  }

  public Class<? extends Entity> buildClassDef() throws NoSuchMethodException, ClassNotFoundException, IllegalAccessException, NoSuchFieldException{
    DynamicType.Builder<? extends Entity> buddy = new ByteBuddy().subclass(this.entityType).implement(CustomEntity.class);

    // buddy = buddy.defineConstructor(Modifier.PUBLIC).withParameters(IChunk.class, CompoundTag.class).intercept(MethodCall.invoke(this.entityType.getConstructor(IChunk.class, CompoundTag.class)).withArgument(0).withArgument(1));

    buddy = buddy.method(named("getIdentifier")).intercept(FixedValue.value(this.typeId));
    buddy = buddy.method(named("initEntity")).intercept(MethodDelegation.to(new InterceptorInitEntity(this, this.onInitEntity)));
    buddy = buddy.method(named("saveNBT")).intercept(MethodDelegation.to(new InterceptorSaveNbt(this, this.onSaveNbt)));
    buddy = buddy.method(named("getHeight")).intercept(FixedValue.value(this.height));
    buddy = buddy.method(named("getWidth")).intercept(FixedValue.value(this.width));
    buddy = buddy.method(named("canBePushed")).intercept(FixedValue.value(this.canBePushed));

    return buddy.make().load(JavaScriptApiPlugin.instance.getPluginClassLoader(), ClassLoadingStrategy.Default.INJECTION).getLoaded();
  }
}
