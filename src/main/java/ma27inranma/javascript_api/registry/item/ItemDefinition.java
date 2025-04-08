package ma27inranma.javascript_api.registry.item;

import java.lang.reflect.Modifier;
import java.util.Map;

import cn.nukkit.item.Item;
import cn.nukkit.item.ItemTool;
import cn.nukkit.item.customitem.ItemCustom;
import cn.nukkit.item.customitem.ItemCustomArmor;
import cn.nukkit.item.customitem.ItemCustomTool;
import cn.nukkit.item.customitem.data.CreativeCategory;
import ma27inranma.javascript_api.JavaScriptApiPlugin;
import ma27inranma.javascript_api.registry.interceptors.item.InterceptorGetDefinition;
import ma27inranma.javascript_api.registry.interceptors.item.InterceptorIsAxe;
import ma27inranma.javascript_api.registry.interceptors.item.InterceptorIsHoe;
import ma27inranma.javascript_api.registry.interceptors.item.InterceptorIsPickaxe;
import ma27inranma.javascript_api.registry.interceptors.item.InterceptorIsShovel;
import ma27inranma.javascript_api.registry.interceptors.item.InterceptorIsSword;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.implementation.MethodDelegation;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class ItemDefinition {
  public static ItemDefinition newDefinition(String typeId, Map<String, Object> obj){
    ItemDefinition definition = new ItemDefinition();

    definition.typeId = typeId;

    if(obj.get("isAxe") instanceof Boolean isAxe){
      definition.isAxe = isAxe;
    }else if(obj.get("isPickaxe") instanceof Boolean isPickaxe){
      definition.isPickaxe = isPickaxe;
    }else if(obj.get("isShovel") instanceof Boolean isShovel){
      definition.isShovel = isShovel;
    }else if(obj.get("isSword") instanceof Boolean isSword){
      definition.isSword = isSword;
    }else if(obj.get("isHoe") instanceof Boolean isHoe){
      definition.isHoe = isHoe;
    }else if(obj.get("itemType") instanceof String itemType){
      switch (itemType) {
        case "Tool" -> {
          definition.subclass = ItemCustomTool.class;
        }
        case "Item" -> {
          definition.subclass = ItemCustom.class;
        }
        case "Armor" -> {
          definition.subclass = ItemCustomArmor.class;
        }
      }
    }

    if(obj.get("name") instanceof String name){
      definition.name = name;
    }

    if(obj.get("creativeCategory") instanceof String creativeCategory){
      switch (creativeCategory) {
        case "Nature" -> definition.creativeCategory = CreativeCategory.NATURE;
        case "Construction" -> definition.creativeCategory = CreativeCategory.CONSTRUCTOR;
        case "Equipment" -> definition.creativeCategory = CreativeCategory.EQUIPMENT;
        case "Items" -> definition.creativeCategory = CreativeCategory.ITEMS;
      }
    }

    if(obj.get("texture") instanceof String texture){
      definition.texture = texture;
    }

    return definition;
  }

  public String typeId = "jsapi:null";

  public boolean isAxe = false;
  public boolean isPickaxe = false;
  public boolean isShovel = false;
  public boolean isSword = false;
  public boolean isHoe = false;

  public String name = "jsapi:null";
  public CreativeCategory creativeCategory = CreativeCategory.NONE;
  public String texture = "jsapi.null";

  public Class<? extends Item> subclass = ItemCustom.class;

  public ItemDefinition(){

  }

  public Class<? extends Item> buildClassDef() throws NoSuchMethodException, ClassNotFoundException{
    DynamicType.Builder<? extends Item> buddy = new ByteBuddy().subclass(this.subclass);

    buddy = buddy.defineConstructor(Modifier.PUBLIC).intercept(MethodCall.invoke(this.subclass.getConstructor(String.class)).with(this.typeId));

    if(this.subclass == ItemTool.class){
      buddy = buddy.method(named("isAxe")).intercept(MethodDelegation.to(new InterceptorIsAxe(isAxe)));
      buddy = buddy.method(named("isPickaxe")).intercept(MethodDelegation.to(new InterceptorIsPickaxe(isPickaxe)));
      buddy = buddy.method(named("isShovel")).intercept(MethodDelegation.to(new InterceptorIsShovel(isShovel)));
      buddy = buddy.method(named("isSword")).intercept(MethodDelegation.to(new InterceptorIsSword(isSword)));
      buddy = buddy.method(named("isHoe")).intercept(MethodDelegation.to(new InterceptorIsHoe(isHoe)));
    }

    CustomItemDefinitionInfo info = new CustomItemDefinitionInfo();
    info.name = this.name;
    info.creativeCategory = this.creativeCategory;
    info.texture = this.texture;
    buddy = buddy.method(named("getDefinition")).intercept(MethodDelegation.to(new InterceptorGetDefinition(info)));

    ClassLoader pluginLoader = JavaScriptApiPlugin.instance.getPluginClassLoader();
    pluginLoader.loadClass("ma27inranma.javascript_api.registry.interceptors.item.InterceptorIsAxe");
    pluginLoader.loadClass("ma27inranma.javascript_api.registry.interceptors.item.InterceptorIsPickaxe");
    pluginLoader.loadClass("ma27inranma.javascript_api.registry.interceptors.item.InterceptorIsShovel");
    pluginLoader.loadClass("ma27inranma.javascript_api.registry.interceptors.item.InterceptorIsSword");
    pluginLoader.loadClass("ma27inranma.javascript_api.registry.interceptors.item.InterceptorIsHoe");
    pluginLoader.loadClass("ma27inranma.javascript_api.registry.interceptors.item.InterceptorGetDefinition");

    return buddy.make().load(pluginLoader, ClassLoadingStrategy.Default.INJECTION).getLoaded(); // somehow it used its own class loader, so i need to inject it to prevent it
  }
}
