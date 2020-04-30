package net.darkhax.wawla.plugins.vanilla;

import java.util.List;

import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.TooltipPosition;
import net.darkhax.wawla.lib.Feature;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class PluginItemFrame extends Feature implements IEntityComponentProvider {
    
    private static final ResourceLocation ENABLED = new ResourceLocation("wawla", "frame_contents");
    
    @Override
    public void initialize (IRegistrar hwyla) {
        
        hwyla.addConfig(ENABLED, true);
        hwyla.registerComponentProvider(this, TooltipPosition.BODY, ItemFrameEntity.class);
    }
    
    @Override
    public void appendBody (List<ITextComponent> info, IEntityAccessor accessor, IPluginConfig config) {

	    if (config.get(ENABLED)) {
	        
	        final Entity entity = accessor.getEntity();
	        
	        if (entity instanceof ItemFrameEntity) {
	            
	            final ItemStack heldItem = ((ItemFrameEntity) entity).getDisplayedItem();
	            
	            if (!heldItem.isEmpty()) {
	                
	                this.addInfo(info, "item", heldItem.getDisplayName());
	            }
	        }
	    }
	}
}