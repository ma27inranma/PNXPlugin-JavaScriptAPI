package ma27inranma.javascript_api;

import org.graalvm.polyglot.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import cn.nukkit.Server;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Logger;

public class JavaScriptApiPlugin extends PluginBase {
  public Logger logger;
  public JavaScriptApiPlugin instance;
  public Server server;

  Context context;

  @Override
  public void onLoad() {
    this.instance = this;
    this.logger = getLogger();
    this.server = getServer();

    this.context = Context.create();

    File scriptMainFolder = Path.of("./scripts/js_main/").toFile();
    if(!scriptMainFolder.exists()) scriptMainFolder.mkdirs();
    if(!scriptMainFolder.isDirectory()){
      getLogger().error(scriptMainFolder.getAbsolutePath() + " Must be a Directory!");
      return;
    }

    for(File file : scriptMainFolder.listFiles()){
      String content = "";
      try{
        content = Files.readString(file.toPath(), StandardCharsets.UTF_8);
      }catch(IOException e){
        e.printStackTrace();
        return;
      }

      this.context.eval("js", content);
    };
  }

  @Override
  public void onEnable() {
    
  }

  @Override
  public void onDisable() {
    
  }
}
