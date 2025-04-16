package ma27inranma.javascript_api.js_class.util;

import org.graalvm.polyglot.HostAccess;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.command.ExecutorCommandSender;
import cn.nukkit.entity.Entity;
import cn.nukkit.level.Location;

public class JsCommandUtil {
  @HostAccess.Export
  public CommandSender getCommandSenderAsAt(Entity entity, Location location){
    return new ExecutorCommandSender(new ConsoleCommandSender(), entity, location);
  }

  @HostAccess.Export
  public CommandSender getConsoleCommandSender(){
    return new ConsoleCommandSender();
  }
}
