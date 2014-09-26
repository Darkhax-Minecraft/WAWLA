package net.darkhax.wawla.modules.addons;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.darkhax.wawla.modules.Module;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.item.ItemStack;

public class ModuleThaumcraft extends Module {

    public Class classTileJarFillable = null;
    
    public ModuleThaumcraft(boolean enabled) {
        
        super(enabled);
        
        if (enabled) {
            
            try {
                
                classTileJarFillable = Class.forName("thaumcraft.common.tiles.TileJarFillable");
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
                    tooltip.add("Asepct: " + aspect);
                
                if (true && amount > 0)
                    tooltip.add("Amount: " + amount);
            }
        }
    }
}
