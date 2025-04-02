package ma27inranma.javascript_api;

import cn.nukkit.Server;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Logger;

public class JavaScriptApiPlugin extends PluginBase {
  public Logger logger;
  public JavaScriptApiPlugin instance;
  public Server server;

  @Override
  public void onLoad() {
    this.instance = this;
    this.logger = getLogger();
    this.server = getServer();
  }

  @Override
  public void onEnable() {
    
  }

  @Override
  public void onDisable() {
    
  }
}
