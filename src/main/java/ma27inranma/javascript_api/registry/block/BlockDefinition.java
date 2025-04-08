package ma27inranma.javascript_api.registry.block;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockSolid;
import cn.nukkit.block.BlockTransparent;
import cn.nukkit.registry.RegisterException;
import ma27inranma.javascript_api.JavaScriptApiPlugin;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodCall;

public class BlockDefinition {
  public static BlockDefinition newDefinition(String typeId, Map<String, Object> obj) throws RegisterException{
    BlockDefinition definition = new BlockDefinition();

    definition.typeId = typeId;

    if(obj.get("blockType").equals("Solid")){
      definition.subclass = BlockSolid.class;
    }else if(obj.get("blockType").equals("Transparent")){
      definition.subclass = BlockTransparent.class;
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

    if(obj.get("breakTime") instanceof Double breakTime){
      definition.breakTime = breakTime;
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

  public Class<? extends Block> buildClassDef() throws NoSuchMethodException, ClassNotFoundException{
    DynamicType.Builder<? extends Block> buddy = new ByteBuddy().subclass(this.subclass);

    buddy = buddy.defineConstructor(Modifier.PUBLIC).intercept(MethodCall.invoke(this.subclass.getConstructor(String.class)).with(this.typeId));

    // CustomItemDefinitionInfo info = new CustomItemDefinitionInfo();
    // info.name = this.name;
    // info.creativeCategory = this.creativeCategory;
    // info.texture = this.texture;
    // buddy = buddy.method(named("getDefinition")).intercept(MethodDelegation.to(new InterceptorGetDefinition(info)));

    ClassLoader pluginLoader = JavaScriptApiPlugin.instance.getPluginClassLoader();

    return buddy.make().load(pluginLoader, ClassLoadingStrategy.Default.INJECTION).getLoaded(); // somehow it used its own class loader, so i need to inject it to prevent it
  }
}
