package net.darkhax.wawla.plugins.vanilla;

import java.util.List;

import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.TooltipPosition;
import net.darkhax.wawla.Wawla;
import net.darkhax.wawla.lib.Feature;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class PluginBlastResistance extends Feature implements IComponentProvider {
    
    private static final ResourceLocation ENABLED = new ResourceLocation("wawla", "blast_resistance");
    
    @Override
    public void initialize (IRegistrar hwyla) {
        
        hwyla.addConfig(ENABLED, false);
        hwyla.registerComponentProvider(this, TooltipPosition.BODY, Block.class);
    }
    
    @Override
    public void appendBody (List<ITextComponent> info, IDataAccessor accessor, IPluginConfig config) {
        
        if (config.get(ENABLED)) {
            
            try {
                
                final float blastResistance = accessor.getBlockState().getExplosionResistance(accessor.getWorld(), accessor.getPosition(), null, null);
                info.add(this.getInfoComponent("blastresist", blastResistance));
            }
            
            catch (final Exception e) {
                
                Wawla.LOG.error("Failed to get explosion resistance for block {}.", accessor.getBlockState());
                Wawla.LOG.catching(e);
            }
        }
    }
}