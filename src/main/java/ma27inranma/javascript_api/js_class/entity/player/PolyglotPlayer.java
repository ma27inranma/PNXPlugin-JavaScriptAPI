package ma27inranma.javascript_api.js_class.entity.player;

import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyObject;

import cn.nukkit.Player;

public class PolyglotPlayer {
  public final Player player;

  public PolyglotPlayer(Player player){
    this.player = player;
  }

  // JS API

  @HostAccess.Export
  public String getName(){
    return this.player.getName();
  }

  @HostAccess.Export
  public void setNameTag(String newName){
    this.player.setNameTag(newName);
  }

  @HostAccess.Export
  public String getNameTag(){
    return this.player.getNameTag();
  }
}
