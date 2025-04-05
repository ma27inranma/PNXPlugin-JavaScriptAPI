package ma27inranma.javascript_api.js_class.util;

import java.util.Map;

import cn.nukkit.math.Vector3;

public class JsVector3 {
  public double x;
  public double y;
  public double z;

  public JsVector3(Map<String, Object> obj){
    if(!(obj.get("x") instanceof Number numberX)) throw new IllegalArgumentException("x, y, z fields should be numbers");
    if(!(obj.get("y") instanceof Number numberY)) throw new IllegalArgumentException("x, y, z fields should be numbers");
    if(!(obj.get("z") instanceof Number numberZ)) throw new IllegalArgumentException("x, y, z fields should be numbers");

    this.x = numberX.doubleValue();
    this.y = numberY.doubleValue();
    this.z = numberZ.doubleValue();
  }

  public JsVector3(Vector3 vector){
    this.x = vector.getX();
    this.y = vector.getY();
    this.z = vector.getZ();
  }

  public JsVector3(double x, double y, double z){
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Vector3 toVector3(){
    return new Vector3(x, y, z);
  }
}
