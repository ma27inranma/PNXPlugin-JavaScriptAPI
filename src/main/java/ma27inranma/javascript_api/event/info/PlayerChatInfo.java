package ma27inranma.javascript_api.event.info;

import cn.nukkit.Player;

public class PlayerChatInfo {
  public final String message;
  public final Player player;

  public PlayerChatInfo(String message, Player player){
    this.message = message;
    this.player = player;
  }

  @Override
  public String toString() {
    return "PlayerChatInfo {message: " + this.message + ", player: " + this.player.toString() + "}";
  }
}
