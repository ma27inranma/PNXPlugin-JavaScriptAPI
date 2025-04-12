package ma27inranma.javascript_api.registry.entity;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockProperties;
import cn.nukkit.block.BlockSolid;
import cn.nukkit.block.BlockState;
import cn.nukkit.block.BlockTransparent;
import cn.nukkit.block.customblock.CustomBlock;
import cn.nukkit.block.property.type.BlockPropertyType;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.mob.EntityMob;
import cn.nukkit.registry.RegisterException;
import ma27inranma.javascript_api.JavaScriptApiPlugin;
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

    return definition;
  }

  public String typeId = "jsapi:null";

  public Class<? extends Entity> entityType = Entity.class;

  public Class<? extends Block> subclass = BlockSolid.class;

  public EntityDefinition(){

  }

  public Class<? extends Block> buildClassDef() throws NoSuchMethodException, ClassNotFoundException, IllegalAccessException, NoSuchFieldException{
    throw new RuntimeException("Not implemented");
  }
}
