package ma27inranma.javascript_api.registry.block;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockProperties;
import cn.nukkit.block.BlockSolid;
import cn.nukkit.block.BlockState;
import cn.nukkit.block.BlockTransparent;
import cn.nukkit.block.property.type.BlockPropertyType;
import cn.nukkit.registry.RegisterException;
import ma27inranma.javascript_api.JavaScriptApiPlugin;
import ma27inranma.javascript_api.registry.interceptors.block.InterceptorGetBlockDefinition;
import net.bytebuddy.ByteBuddy;
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
import net.bytebuddy.implementation.bytecode.constant.IntegerConstant;
import net.bytebuddy.implementation.bytecode.constant.TextConstant;
import net.bytebuddy.implementation.bytecode.member.FieldAccess;
import net.bytebuddy.implementation.bytecode.member.MethodInvocation;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.matcher.ElementMatchers;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class BlockDefinition {
  public static BlockDefinition newDefinition(String typeId, Map<String, Object> obj) throws RegisterException{
    BlockDefinition definition = new BlockDefinition();

    definition.typeId = typeId;

    if(obj.get("blockType") != null){
      if(obj.get("blockType").equals("Solid")){
        definition.subclass = BlockSolid.class;
      }else if(obj.get("blockType").equals("Transparent")){
        definition.subclass = BlockTransparent.class;
      }
    }

    if(obj.get("states") instanceof List states){
      for(Object element : states){
        if(!(element instanceof Map state)) continue;

        BlockStateDefinition blockStateDefinition = new BlockStateDefinition();
        
        if(state.get("typeId") instanceof String blockStateTypeId){
          blockStateDefinition.typeId = blockStateTypeId;
        }else{
          throw new RegisterException("typeId is missing in the block state definition");
        }

        if(state.get("rangeStart") instanceof Integer rangeStart){
          blockStateDefinition.rangeStart = rangeStart;
        }else{
          throw new RegisterException("typeId is missing in the block state definition");
        }

        if(state.get("rangeEnd") instanceof Integer rangeEnd){
          blockStateDefinition.rangeEnd = rangeEnd;
        }else{
          throw new RegisterException("typeId is missing in the block state definition");
        }

        definition.blockStates.add(blockStateDefinition);
      }
    }

    if(obj.get("materials") instanceof Map materialsObj){
      BlockMaterialDefinition matDef = new BlockMaterialDefinition();

      for(Object keyElm : materialsObj.keySet()){
        if(!(keyElm instanceof String key)) throw new RegisterException("Unreachable");
        if(!(materialsObj.get(key) instanceof Map materialObj)) throw new RegisterException("Expected object (in materials)");

        if(!(materialObj.get("material") instanceof String material)) throw new RegisterException("Expected string (in materials -> " + key + " -> material)");
        if(!(materialObj.get("texture") instanceof String texture)) throw new RegisterException("Expected string (in materials -> " + key + " -> texture)");

        matDef.face = key;
        matDef.material = material;
        matDef.texture = texture;
      }

      definition.matDefs.add(matDef);
    }

    if(obj.get("geometry") instanceof String geometry){
      definition.geometry = geometry;
    }else if(obj.get("geometry") != null){
      throw new RegisterException("Expected string (in geometry)");
    }

    if(obj.get("breakTime") instanceof Number breakTime){
      definition.breakTime = breakTime.doubleValue();
    }else if(obj.get("breakTime") != null){
      throw new RegisterException("Expected number (in breakTime)");
    }

    // TODO: permutations support. this needs a separate parseBlockDefinition fn.
    // if(obj.get("permutations") instanceof List permutations){
    //   for(Object element : permutations){
    //     if(!(element instanceof Map permutation)) continue;

    //     BlockStateDefinition blockStateDefinition = new BlockStateDefinition();
        
    //     if(permutation.get("condition") instanceof String condition){
          
    //     }else{
    //       throw new RegisterException("typeId is missing in the block state definition");
    //     }

    //     if(permutation.get("rangeEnd") instanceof Integer rangeEnd){
    //       blockStateDefinition.rangeEnd = rangeEnd;
    //     }else{
    //       throw new RegisterException("typeId is missing in the block state definition");
    //     }

    //     definition.blockStates.add(blockStateDefinition);
    //   }
    // }

    return definition;
  }

  public String typeId = "jsapi:null";

  public String geometry = "mineccraft:geometry.full_block";
  public double breakTime = 0;
  public ArrayList<BlockStateDefinition> blockStates = new ArrayList<>();
  public ArrayList<BlockMaterialDefinition> matDefs = new ArrayList<>();

  public Class<? extends Block> subclass = BlockSolid.class;

  public BlockDefinition(){

  }

  public Class<? extends Block> buildClassDef() throws NoSuchMethodException, ClassNotFoundException, IllegalAccessException, NoSuchFieldException{
    BlockProperties properties = new BlockProperties(this.typeId, new BlockPropertyType[]{});

    DynamicType.Builder<? extends Block> buddy = new ByteBuddy().subclass(this.subclass);

    buddy = buddy.defineField("PROPERTIES", BlockProperties.class, Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL).initializer(new ByteCodeAppender() {
      /*
        ICONST_0
        ANEWARRAY BlockPropertyType
        NEW BlockProperties
        DUP
        LDC "typeId"
        ICONST_0
        ANEWARRAY BlockPropertyType
        INVOKESPECIAL BlockProperties.constructor(String, BlockPropertyType[])
        PUTSTATIC subclass.PROPERTIES
       */

      @Override
      public Size apply(MethodVisitor visitor, Context context, MethodDescription methodDescription) {
        TypeDescription propertyTypeDesc = new TypeDescription.ForLoadedType(BlockPropertyType.class);
    
        // new BlockPropertyType[0]
        StackManipulation newArray = new StackManipulation.Compound(
          IntegerConstant.forValue(0), // ICONST_0
          ArrayFactory.forType(TypeDescription.ForLoadedType.of(BlockPropertyType.class).asGenericType())
            .withValues(Collections.emptyList()) // ANEWARRAY BlockPropertyType
        );
    
        // new BlockProperties(typeId, new BlockPropertyType[0])
        StackManipulation blockPropsCtorCall = new StackManipulation.Compound(
          TypeCreation.of(new TypeDescription.ForLoadedType(BlockProperties.class)), // NEW BlockProperties
          Duplication.SINGLE, // DUP
          new TextConstant(typeId), // LDC "typeId"
          newArray, // ICONST_0 + ANEWARRAY BlockPropertyType
          MethodInvocation.invoke(
            new TypeDescription.ForLoadedType(BlockProperties.class)
              .getDeclaredMethods()
              .filter(ElementMatchers.isConstructor())
              .filter(ElementMatchers.takesArguments(int.class, BlockPropertyType[].class))
              .getOnly()
          ), // INVOKESPECIAL BlockProperties.constructor(String, BlockPropertyType[])
          FieldAccess.forField(
            new TypeDescription.ForLoadedType(subclass)
              .getDeclaredFields()
              .filter(ElementMatchers.named("PROPERTIES"))
              .getOnly()
          ).write() // PUTSTATIC subclass.PROPERTIES : BlockProperties
        );
    
        StackManipulation.Size newArraySize = newArray.apply(visitor, context);
        StackManipulation.Size blockPropsCtorCallSize = blockPropsCtorCall.apply(visitor, context);
    
        return new Size(
          newArraySize.getMaximalSize() + blockPropsCtorCallSize.getMaximalSize(),
          methodDescription.getStackSize()
        );
      }
    });

    // buddy = buddy.defineConstructor(Modifier.PUBLIC).withParameters(new Type[0]).intercept(MethodCall.invoke(this.subclass.getConstructor(BlockState.class)).withField("PROPERTIES"));
    buddy = buddy.defineConstructor(Modifier.PUBLIC).withParameters(new Type[0]).intercept(MethodCall.invoke(this.subclass.getConstructor(BlockState.class)).with(properties.getDefaultState()));
    // buddy = buddy.defineConstructor(Modifier.PUBLIC).withParameters(BlockState.class).intercept(MethodCall.invoke(this.subclass.getConstructor(BlockState.class)).withArgument(0));

    buddy = buddy.method(named("getProperties")).intercept(FixedValue.value(properties));

    buddy = buddy.method(named("getDefinition")).intercept(MethodDelegation.to(new InterceptorGetBlockDefinition(this)));

    ClassLoader pluginLoader = JavaScriptApiPlugin.instance.getPluginClassLoader();

    Class<? extends Block> blockClass = buddy.make().load(pluginLoader, ClassLoadingStrategy.Default.INJECTION).getLoaded(); // somehow it used its own class loader, so i need to inject it to prevent it
    blockClass.getField("PROPERTIES").set(null, properties); // here

    return blockClass;
  }
}
