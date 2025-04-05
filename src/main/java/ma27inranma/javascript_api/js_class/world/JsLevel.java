package ma27inranma.javascript_api.js_class.world;

import java.util.Map;

import cn.nukkit.level.Level;
import ma27inranma.javascript_api.js_class.util.JsVector3;
import ma27inranma.javascript_api.js_class.world.block.JsBlock;

public class JsLevel {
  private Level level;

  public JsLevel(Level level){
    this.level = level;
  }

  public JsBlock getBlock(Map<String, Object> loc){
    JsVector3 vec = new JsVector3(loc);
    
    return new JsBlock(this.level.getBlock(vec.toVector3()));
  }
}
