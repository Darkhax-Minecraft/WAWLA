package net.darkhax.icse.plugins.tile;

import net.darkhax.icse.lib.DataAccess;
import net.darkhax.icse.plugins.InfoPlugin;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class PluginMonsterEggFix extends InfoPlugin {
    
    @Override
    public DataAccess overrideTile (DataAccess data) {
        
        if (data.block == Blocks.MONSTER_EGG) {
            
            final int meta = data.block.getMetaFromState(data.state);
            
            switch (meta) {
                
                case 0:
                    data.override(new ItemStack(Blocks.STONE, 1, 0));
                    break;
                    
                case 1:
                    data.override(new ItemStack(Blocks.COBBLESTONE, 1, 0));
                    break;
                    
                case 2:
                    data.override(new ItemStack(Blocks.STONEBRICK, 1, 0));
                    break;
                    
                case 3:
                    data.override(new ItemStack(Blocks.STONEBRICK, 1, 1));
                    break;
                    
                case 4:
                    data.override(new ItemStack(Blocks.STONEBRICK, 1, 2));
                    break;
                    
                case 5:
                    data.override(new ItemStack(Blocks.STONEBRICK, 1, 3));
                    break;
            }
        }
        
        return data;
    }
    
    @Override
    public boolean requireTileOverride (DataAccess data) {
        
        return data.block == Blocks.MONSTER_EGG;
    }
}
