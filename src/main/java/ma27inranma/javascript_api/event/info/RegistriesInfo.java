package ma27inranma.javascript_api.event.info;

import java.util.ArrayList;
import java.util.Map;

import cn.nukkit.item.Item;
import cn.nukkit.registry.RegisterException;
import cn.nukkit.registry.Registries;
import ma27inranma.javascript_api.JavaScriptApiPlugin;
import ma27inranma.javascript_api.registry.item.ItemDefinition;

public class RegistriesInfo {
  public static ArrayList<Class<? extends Item>> itemClasses = new ArrayList<>();

  public RegistriesInfo(){
  }

  public void register(String typeId, Map<String, Object> obj) throws RegisterException, NoSuchMethodException, Throwable{
    Class<? extends Item> itemClass = ItemDefinition.newDefinition(typeId, obj).buildClassDef();
    itemClasses.add(itemClass);

    // JavaScriptApiPlugin.logger.info("Class: " + Class.forName(itemClass.getName()));
    
    Registries.ITEM.registerCustomItem(JavaScriptApiPlugin.instance, itemClass);
  }

  @Override
  public String toString() {
    return "RegistriesInfo {}";
  }
}
