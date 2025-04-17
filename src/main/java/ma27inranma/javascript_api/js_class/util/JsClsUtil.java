package ma27inranma.javascript_api.js_class.util;

public class JsClsUtil {
  public Class<?> forName(String name){
    try{
      Class<?> cls = Class.forName(name);
      return cls;
    }catch(ClassNotFoundException t){
      return null;
    }
  }
}
