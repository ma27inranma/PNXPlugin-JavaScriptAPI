package ma27inranma.javascript_api.js_class.world.block;

import org.graalvm.polyglot.HostAccess;

import cn.nukkit.block.Block;
import ma27inranma.javascript_api.js_class.util.JsVector3;

public class JsBlock {
  private Block block;

  public JsBlock(Block block){
    this.block = block;
  }

  @HostAccess.Export
  public JsVector3 getLocation(){
    return new JsVector3(this.block);
  }

  @HostAccess.Export
  public String getTypeId(){
    return this.block.getId();
  }

  public Block getRawBlock(){
    return this.block;
  }
}
