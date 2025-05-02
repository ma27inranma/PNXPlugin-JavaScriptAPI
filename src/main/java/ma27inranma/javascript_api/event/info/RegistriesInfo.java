package ma27inranma.javascript_api.event.info;

import java.util.ArrayList;
import java.util.Map;

import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.item.Item;
import cn.nukkit.registry.RegisterException;
import cn.nukkit.registry.Registries;
import cn.nukkit.registry.EntityRegistry.CustomEntityDefinition;
import ma27inranma.javascript_api.JavaScriptApiPlugin;
import ma27inranma.javascript_api.registry.block.BlockDefinition;
import ma27inranma.javascript_api.registry.entity.EntityDefinition;
import ma27inranma.javascript_api.registry.item.ItemDefinition;

public class RegistriesInfo {
  public static ArrayList<Class<? extends Item>> itemClasses = new ArrayList<>();
  public static ArrayList<Class<? extends Block>> blockClasses = new ArrayList<>();
  public static ArrayList<Class<? extends Entity>> entityClasses = new ArrayList<>();

  public RegistriesInfo(){
  }

  public void registerItem(String typeId, Map<String, Object> obj) throws RegisterException, NoSuchMethodException, Throwable{
    Class<? extends Item> itemClass = ItemDefinition.newDefinition(typeId, obj).buildClassDef();
    itemClasses.add(itemClass);
    
    Registries.ITEM.registerCustomItem(JavaScriptApiPlugin.instance, itemClass);
  }

  public void registerBlock(String typeId, Map<String, Object> obj) throws RegisterException, NoSuchMethodException, Throwable{
    Class<? extends Block> blockClass = BlockDefinition.newDefinition(typeId, obj).buildClassDef();
    blockClasses.add(blockClass);
    
    Registries.BLOCK.registerCustomBlock(JavaScriptApiPlugin.instance, blockClass);
  }

  public void registerEntity(String typeId, Map<String, Object> obj) throws RegisterException, NoSuchMethodException, Throwable{
    Class<? extends Entity> entityClass = EntityDefinition.newDefinition(typeId, obj).buildClassDef();
    entityClasses.add(entityClass);

    Registries.ENTITY.registerCustomEntity(JavaScriptApiPlugin.instance, new CustomEntityDefinition(typeId, "", true, true), entityClass);
  }

  @Override
  public String toString() {
    return "RegistriesInfo {}";
  }
}
