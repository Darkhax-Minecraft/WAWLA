package net.darkhax.wawla.modules.addons;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.darkhax.wawla.modules.Module;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.DimensionManager;

public class ModuleThaumcraft extends Module {

    public Class classTileJarFillable = null;
    public Class classTileMirror = null;
    public Class classTileMirrorEssentia = null;

    public ModuleThaumcraft(boolean enabled) {

        super(enabled);

        if (enabled) {

            try {

                classTileJarFillable = Class.forName("thaumcraft.common.tiles.TileJarFillable");
                classTileMirror = Class.forName("thaumcraft.common.tiles.TileMirror");
                classTileMirrorEssentia = Class.forName("thaumcraft.common.tiles.TileMirrorEssentia");
            }

            catch (ClassNotFoundException e) {

                e.printStackTrace();
            }
        }
    }

    @Override
    public void onWailaBlockDescription(ItemStack stack, List<String> tooltip, IWailaDataAccessor access, IWailaConfigHandler config) {

        if (access.getBlock() != null && access.getTileEntity() != null) {

            if (Utilities.compareTileEntityByClass(access.getTileEntity(), classTileJarFillable)) {

                String aspect = access.getNBTData().getString("Aspect");
                int amount = access.getNBTData().getShort("Amount");

                if (true && aspect != null && !aspect.isEmpty())
                    tooltip.add("Asepct: " + Utilities.upperCase(aspect));

                if (true && amount > 0)
                    tooltip.add("Amount: " + amount);
            }
            
            
            if ((Utilities.compareTileEntityByClass(access.getTileEntity(), classTileMirror) || Utilities.compareTileEntityByClass(access.getTileEntity(), classTileMirrorEssentia)) && access.getNBTData().getBoolean("linked")) {
                
                int x = access.getNBTData().getInteger("linkX");
                int y = access.getNBTData().getInteger("linkY");
                int z = access.getNBTData().getInteger("linkZ");
                
                if (true) 
                    tooltip.add("Linked: X:" + x + " Y:"+ y + " Z:" + z);
                
                if (true) 
                    tooltip.add("Dimension: " + DimensionManager.getProvider(access.getNBTData().getInteger("linkDim")).getDimensionName());
            }
        }
    }
}
