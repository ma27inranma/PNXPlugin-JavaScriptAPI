package ma27inranma.javascript_api.js_class.util;

import java.util.Map;

import org.graalvm.polyglot.HostAccess;

import cn.nukkit.math.Vector3;

public class JsVector3Util {
  @HostAccess.Export
  public Vector3 fromVector3(Map<String, Object> vec){
    JsVector3 pnxVec = new JsVector3(vec);

    return pnxVec.toVector3();
  }
}
