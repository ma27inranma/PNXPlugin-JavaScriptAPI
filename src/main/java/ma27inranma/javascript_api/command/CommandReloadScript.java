package ma27inranma.javascript_api.command;

import java.util.Map.Entry;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.command.tree.ParamList;
import cn.nukkit.command.utils.CommandLogger;
import cn.nukkit.permission.Permission;
import ma27inranma.javascript_api.JavaScriptApiPlugin;

public class CommandReloadScript extends PluginCommand<JavaScriptApiPlugin> {
  public CommandReloadScript(){
    super("reloadscript", JavaScriptApiPlugin.instance);

    this.setPermission(Permission.DEFAULT_OP);

    this.enableParamTree();
  }

  @Override
  public int execute(CommandSender sender, String commandLabel, Entry<String, ParamList> result, CommandLogger log) {
    JavaScriptApiPlugin.instance.reloadScripts();

    return 1;
  }
}
