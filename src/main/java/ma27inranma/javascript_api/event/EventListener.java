package ma27inranma.javascript_api.event;

import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.entity.EntityDamageByBlockEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import ma27inranma.javascript_api.event.info.PlayerChatInfo;

public class EventListener implements Listener {
  @EventHandler
  public void onChat(PlayerChatEvent event){
    EventBus.emit(EventNames.PlayerChat, new PlayerChatInfo(event.getMessage(), event.getPlayer()));
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event){
    EventBus.emit(EventNames.BlockBreak, event);
  }

  @EventHandler
  public void onBlockPlace(BlockPlaceEvent event){
    EventBus.emit(EventNames.BlockPlace, event);
  }

  @EventHandler
  public void onInteract(PlayerInteractEvent event){
    EventBus.emit(EventNames.PlayerInteract, event);
  }

  @EventHandler
  public void onDamagedByEntity(EntityDamageByEntityEvent event){
    EventBus.emit(EventNames.EntityDamagedByEntity, event);
  }

  @EventHandler
  public void onDamagedByBlock(EntityDamageByBlockEvent event){
    EventBus.emit(EventNames.EntityDamagedByBlock, event);
  }

  @EventHandler
  public void onDamaged(EntityDamageEvent event){
    EventBus.emit(EventNames.EntityDamage, event);
  }
}
