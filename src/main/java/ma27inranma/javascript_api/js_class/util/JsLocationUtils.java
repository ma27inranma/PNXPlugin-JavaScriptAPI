package ma27inranma.javascript_api.js_class.util;

import java.util.Map;

import org.graalvm.polyglot.HostAccess;

import cn.nukkit.level.Location;

public class JsLocationUtils {
  @HostAccess.Export
  public Location fromVector3(Map<String, Object> vec){
    JsVector3 pnxVec = new JsVector3(vec);

    return new Location(pnxVec.x, pnxVec.y, pnxVec.z);
  }
}
