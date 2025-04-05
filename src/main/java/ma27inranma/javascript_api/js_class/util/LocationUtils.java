package ma27inranma.javascript_api.js_class.util;

import org.graalvm.polyglot.HostAccess;

import cn.nukkit.level.Location;
import cn.nukkit.math.Vector3;

public class LocationUtils {
  @HostAccess.Export
  public static Location fromVector3(Vector3 vec){
    return new Location(vec.x, vec.y, vec.z);
  }
}
