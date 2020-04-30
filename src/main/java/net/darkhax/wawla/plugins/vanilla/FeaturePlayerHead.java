package net.darkhax.wawla.plugins.vanilla;

import java.util.List;

import com.mojang.authlib.GameProfile;

import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.TooltipPosition;
import net.darkhax.wawla.lib.Feature;
import net.minecraft.block.SkullPlayerBlock;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class FeaturePlayerHead extends Feature implements IComponentProvider {
    
    private static final ResourceLocation ENABLED = new ResourceLocation("wawla", "skull_owner");
    
    @Override
    public void initialize (IRegistrar hwyla) {
        
        hwyla.addConfig(ENABLED, true);
        hwyla.registerComponentProvider(this, TooltipPosition.BODY, SkullPlayerBlock.class);
    }
    
    @Override
    public void appendBody (List<ITextComponent> info, IDataAccessor accessor, IPluginConfig config) {
        
        if (config.get(ENABLED)) {
            
            final TileEntity tile = accessor.getTileEntity();
            
            if (tile instanceof SkullTileEntity) {
                
                final GameProfile profile = ((SkullTileEntity) tile).getPlayerProfile();
                
                if (profile != null) {
                    
                    this.addInfo(info, "player", profile.getName());
                }
            }
        }
    }
}