package ma27inranma.javascript_api;

import org.graalvm.polyglot.*;
import org.graalvm.polyglot.io.IOAccess;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.item.Item;
import cn.nukkit.level.ParticleEffect;
import cn.nukkit.math.SimpleAxisAlignedBB;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Logger;
import ma27inranma.javascript_api.command.CommandReloadScript;
import ma27inranma.javascript_api.event.EventBus;
import ma27inranma.javascript_api.event.EventListener;
import ma27inranma.javascript_api.event.info.RegistriesInfo;
import ma27inranma.javascript_api.js_class.JsScheduler;
import ma27inranma.javascript_api.js_class.util.JsClsUtil;
import ma27inranma.javascript_api.js_class.util.JsCommandUtil;
import ma27inranma.javascript_api.js_class.util.JsLocationUtils;
import ma27inranma.javascript_api.js_class.util.JsVector3Util;

public class JavaScriptApiPlugin extends PluginBase {
  public static Logger logger;
  public static JavaScriptApiPlugin instance;
  public static Server server;

  public boolean isEnabled = false;

  Context context;

  @Override
  public void onLoad() {
    this.instance = this;
    this.logger = getLogger();
    this.server = getServer();

    this.context = Context.create();

    reloadScripts();

    EventBus.emit("Registries", new RegistriesInfo());
  }

  @Override
  public void onEnable() {
    getServer().getCommandMap().register("reloadscript", new CommandReloadScript());

    getServer().getPluginManager().registerEvents(new EventListener(), this);

    this.isEnabled = true;
    EventBus.emit("Loaded", new HashMap<>());
  }

  @Override
  public void onDisable() {
    EventBus.emit("Unload", new HashMap<>());
  }

  public void reloadScripts(){
    if(this.context != null){
      EventBus.emit("Unload", new HashMap<>());

      this.context.close(true);
      this.context = null;
    }

    EventBus.reload();

    this.context = Context.newBuilder("js").allowAllAccess(true).allowIO(IOAccess.ALL).hostClassLoader(getPluginClassLoader()).option("js.esm-eval-returns-exports", "true").build();

    File scriptMainFolder = Path.of("./scripts/js_main/").toFile();
    if(!scriptMainFolder.exists()) scriptMainFolder.mkdirs();
    if(!scriptMainFolder.isDirectory()){
      getLogger().error(scriptMainFolder.getAbsolutePath() + " Must be a Directory!");
      return;
    }

    registerApis(this.context);

    for(File file : scriptMainFolder.listFiles()){
      String content = "";
      try{
        content = Files.readString(file.toPath(), StandardCharsets.UTF_8);
      }catch(IOException e){
        e.printStackTrace();
        return;
      }

      try{
        this.context.eval(Source.newBuilder("js", content, file.toPath().toAbsolutePath().toString()).uri(file.toPath().toAbsolutePath().toUri()).mimeType("application/javascript+module")/*this is esmodule*/.build());
      }catch(Exception e){
        e.printStackTrace();
      }
    };

    if(this.isEnabled){
      EventBus.emit("Loaded", new HashMap<>());
    }
  }

  public void registerApis(Context context){
    Value jsRoot = context.getBindings("js");

    jsRoot.putMember("Api", new ApiRoot());
    jsRoot.putMember("Vector3s", new JsVector3Util());
    jsRoot.putMember("Locations", new JsLocationUtils());
    jsRoot.putMember("Commands", new JsCommandUtil());
    jsRoot.putMember("Block", Block.class);
    jsRoot.putMember("Item", Item.class);
    jsRoot.putMember("Class", new JsClsUtil());
    jsRoot.putMember("ParticleEffect", ParticleEffect.class);
    jsRoot.putMember("SimpleAxisAlignedBB", SimpleAxisAlignedBB.class);
    jsRoot.putMember("Scheduler", new JsScheduler());
  }
}
