package net.darkhax.wawla.modules;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.wawla.modules.addons.ModuleTinkers;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;

public class ModuleHarvest extends Module {
    
    private String showTool = "wawla.harvest.showTool";
    private String showHarvestable = "wawla.harvest.showHarvest";
    private String showTier = "wawla.harvest.showTier";
    
    public ModuleHarvest(boolean enabled) {
    
        super(enabled);
    }
    
    @Override
    public void onWailaBlockDescription (ItemStack stack, List<String> tooltip, IWailaDataAccessor access, IWailaConfigHandler config) {
    
        MovingObjectPosition pos = access.getPosition();
        Block block = access.getBlock();
        ItemStack item = access.getPlayer().getHeldItem();
        String tool = (block != null) ? block.getHarvestTool(access.getMetadata()) : "";
        int blockLevel = block.getHarvestLevel(access.getMetadata());
        int itemLevel = (item != null) ? item.getItem().getHarvestLevel(item, tool) : 0;
        
        // Shows if the tool is the right tier.
        if (item != null && (item.getItem().getToolClasses(item).contains(tool) || ModuleTinkers.canHarvest(item, tool))) {
            
            // When the block is harvestable.
            if (config.getConfig(showHarvestable) && (blockLevel <= itemLevel || blockLevel == 0))
                tooltip.add(StatCollector.translateToLocal("tooltip.wawla.canHarvest") + ": " + ((EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocal("tooltip.wawla.yes"))));
            
            // When it's not harvestable.
            else {
                
                tooltip.add(StatCollector.translateToLocal("tooltip.wawla.canHarvest") + ": " + EnumChatFormatting.RED + StatCollector.translateToLocal("tooltip.wawla.no"));
                
                if (config.getConfig(showTier))
                    tooltip.add(StatCollector.translateToLocal("tooltip.wawla.blockLevel") + ": " + blockLevel);
            }
            
            return;
        }
        
        // Shows correct tool type.
        if (tool != null && config.getConfig(showTool))
            tooltip.add(StatCollector.translateToLocal("tooltip.wawla.toolType") + ": " + tool);
    }
    
    @Override
    public void onWailaRegistrar (IWailaRegistrar register) {
    
        register.addConfig("Wawla", showTool);
        register.addConfig("Wawla", showHarvestable);
        register.addConfig("Wawla", showTier);
    }
}
