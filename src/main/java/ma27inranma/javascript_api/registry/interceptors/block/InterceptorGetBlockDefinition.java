package ma27inranma.javascript_api.registry.interceptors.block;

import cn.nukkit.block.customblock.CustomBlock;
import cn.nukkit.block.customblock.CustomBlockDefinition;
import cn.nukkit.block.customblock.data.Geometry;
import cn.nukkit.block.customblock.data.Materials;
import ma27inranma.javascript_api.registry.block.BlockDefinition;
import ma27inranma.javascript_api.registry.block.BlockMaterialDefinition;
import net.bytebuddy.implementation.bind.annotation.This;

public class InterceptorGetBlockDefinition {
  public final BlockDefinition blockDefinition;

  public InterceptorGetBlockDefinition(BlockDefinition blockDefinition){
    this.blockDefinition = blockDefinition;
  }

  public CustomBlockDefinition getDefinition(@This Object self){
    if(!(self instanceof CustomBlock customBlock)) throw new RuntimeException("GetBlockDefinition is called on non-custom block object");

    Materials materials = Materials.builder();
    for(BlockMaterialDefinition matDef : blockDefinition.matDefs){
      materials.process(matDef.face, true, true, matDef.material, matDef.texture);
    }

    // TODO: permutation support
    // Permutation[] permutations = new Permutation[blockDefinition.permutations.size()];

    return CustomBlockDefinition.builder(customBlock)
      .materials(materials)
      .geometry(new Geometry(blockDefinition.geometry))
      .breakTime(blockDefinition.breakTime)
      .build();
  }
}
