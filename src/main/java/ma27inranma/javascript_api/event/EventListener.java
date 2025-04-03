package ma27inranma.javascript_api.event;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import ma27inranma.javascript_api.event.info.PlayerChatInfo;
import ma27inranma.javascript_api.js_class.entity.player.PolyglotPlayer;

public class EventListener implements Listener {
  @EventHandler
  public void onChat(PlayerChatEvent event){
    EventBus.emit(EventNames.PlayerChat, new PlayerChatInfo(event.getMessage(), event.getPlayer()));
  }
}
