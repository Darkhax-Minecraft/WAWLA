package net.darkhax.wawla.plugins.vanilla;

import java.util.List;

import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.TooltipPosition;
import net.darkhax.wawla.lib.Feature;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

public class FeatureBreakProgress extends Feature implements IComponentProvider {
    
    private static final ResourceLocation ENABLED = new ResourceLocation("wawla", "break_progress");
    
    @Override
    public void initialize (IRegistrar hwyla) {
        
        hwyla.addConfig(ENABLED, true);
        hwyla.registerComponentProvider(this, TooltipPosition.BODY, Block.class);
    }
    
    @Override
    public void appendBody (List<ITextComponent> info, IDataAccessor accessor, IPluginConfig config) {
        
        if (config.get(ENABLED)) {
            
            final int progress = MathHelper.floor(Minecraft.getInstance().playerController.curBlockDamageMP * 100f);
            
            if (progress > 0) {
                
                info.add(this.getInfoComponent("progression", progress));
            }
        }
    }
}