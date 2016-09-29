package net.darkhax.wawla.plugins.vanilla;

import java.util.List;

import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.minecraft.client.resources.I18n;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraftforge.common.config.Configuration;

public class PluginSkulls extends InfoProvider {
    
    private static boolean enabled = false;
    
    @Override
    public void addTileInfo (List<String> info, InfoAccess data) {
        
        if (enabled) {
            
            final TileEntity tile = data.world.getTileEntity(data.pos);
            
            if (tile instanceof TileEntitySkull) {
                
                final TileEntitySkull skull = (TileEntitySkull) tile;
                
                if (skull.getPlayerProfile() != null && skull.getPlayerProfile().getName() != null)
                    info.add(I18n.format("tooltip.wawla.name") + ": " + skull.getPlayerProfile().getName());
            }
        }
    }
    
    @Override
    public void syncConfig (Configuration config) {
        
        enabled = config.getBoolean("Skull_Names", "vanilla_tiles", true, "If this is enabled, the hud will display the owner of skulls.");
    }
}