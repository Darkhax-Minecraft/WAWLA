package net.darkhax.wawla.plugins.vanilla;

import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IRegistrar;
import net.darkhax.wawla.lib.Feature;
import net.minecraft.block.Blocks;
import net.minecraft.block.TrappedChestBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

public class FeatureHiddenBlocks extends Feature implements IComponentProvider {
    
    private static final ResourceLocation HIDE_TRAP_CHEST = new ResourceLocation("wawla", "hide_trap_chest");
    
    private static ItemStack CHEST = new ItemStack(Items.CHEST);
    
    @Override
    public void initialize (IRegistrar hwyla) {
        
        hwyla.addSyncedConfig(HIDE_TRAP_CHEST, true);
        hwyla.registerStackProvider(this, TrappedChestBlock.class);
    }

    @Override
    public ItemStack getStack (IDataAccessor accessor, IPluginConfig config) {
        
        if (config.get(HIDE_TRAP_CHEST) && accessor.getBlock() == Blocks.TRAPPED_CHEST) {
            
            return CHEST;
        }
        
        return accessor.getStack();
    }
}