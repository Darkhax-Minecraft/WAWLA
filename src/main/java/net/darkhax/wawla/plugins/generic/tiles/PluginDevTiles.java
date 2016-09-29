package net.darkhax.wawla.plugins.generic.tiles;

import java.util.List;

import net.darkhax.icse.lib.Constants;
import net.darkhax.icse.lib.Utilities;
import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.minecraft.tileentity.TileEntity;

public class PluginDevTiles extends InfoProvider {
    
    @Override
    public void addTileInfo (List<String> info, InfoAccess data) {
        
        info.add("Block Class: " + data.block.getClass().getCanonicalName());
        
        final TileEntity tile = data.world.getTileEntity(data.pos);
        
        if (tile != null)
            info.add("Tile Class: " + tile.getClass().getCanonicalName());
        
        if (data.player.isSneaking()) {
            
            Utilities.wrapStringToList(data.tag.toString(), 50, true, info);
            Constants.LOG.info(tile.getClass().getCanonicalName());
        }
    }
}
