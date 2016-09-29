package net.darkhax.wawla.plugins.generic.tiles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;

public class PluginHarvestability extends InfoProvider {
    
    private static final Map<String, ItemStack> overrides = new HashMap<>();
    
    private static boolean enabled = true;
    private static boolean oresOnly = true;
    private static boolean showHarvestable = true;
    private static boolean showCorrectTier = true;
    private static boolean showCorrectTool = true;
    
    @Override
    public void addTileInfo (List<String> info, InfoAccess data) {
        
        if (!enabled)
            return;
        
        final ItemStack heldItem = data.player.getHeldItemMainhand();
        final String toolType = this.getEffectiveTool(data.world, data.state, data.pos);
        final int blockLevel = data.block.getHarvestLevel(data.state);
        final int itemLevel = heldItem != null ? heldItem.getItem().getHarvestLevel(heldItem, toolType) : 0;
        final boolean isValidBlock = oresOnly && this.isOre(new ItemStack(data.block)) || !oresOnly;
        
        // Shows harvest information
        if (isValidBlock && heldItem != null && heldItem.getItem().getToolClasses(heldItem).contains(toolType)) {
            
            // When the block is harvestable.
            if (showHarvestable && (blockLevel <= itemLevel || blockLevel == 0))
                info.add(I18n.format("tooltip.wawla.generic.harvestable") + ": " + InfoProvider.getBooleanForDisplay(true));
            
            // When it's not harvestable.
            else {
                
                if (showHarvestable)
                    info.add(I18n.format("tooltip.wawla.generic.harvestable") + ": " + InfoProvider.getBooleanForDisplay(false));
                
                if (showCorrectTier)
                    info.add(I18n.format("tooltip.wawla.generic.showtier") + ": " + blockLevel);
            }
        }
        
        // Shows correct tool type.
        else if (isValidBlock && toolType != null && showCorrectTool) {
            
            final String translation = I18n.format("tooltip.wawla." + toolType);
            info.add(I18n.format("tooltip.wawla.generic.tooltype") + ": " + (translation.startsWith("tooltip.wawla.") ? toolType : translation));
        }
    }
    
    @Override
    public void syncConfig (Configuration config) {
        
        enabled = config.getBoolean("Harvestability", "generic_tiles", true, "If this is enabled, the hud will display information about tool harvestability.");
        oresOnly = config.getBoolean("Harvestability_OnlyOres", "generic_tiles", true, "When enabled, only ore blocks will show harvestability info.");
        showHarvestable = config.getBoolean("Harvestability_Harvestable", "generic_tiles", true, "When enabled, shows if the current tile can be harvested or not.");
        showCorrectTier = config.getBoolean("Harvestability_Tier", "generic_tiles", true, "When enabled, shows the required tool tier, if the correct tool is used, but it is not good enough.");
        showCorrectTool = config.getBoolean("Harvestability_Tool", "generic_tiles", true, "When enabled, shows the required tool, if the correct one is not being held.");
    }
    
    /**
     * Checks if an ItemStack contains an ore item. This is done by checking the item extends
     * BlockOre, or if the ore dictionary for entries that start with 'ore'. It will also check
     * the display name of the stack to see if it has the word Ore in it.
     * 
     * @param stack The ItemStack to check.
     * @return boolean Whether or not the ItemStack is an ore.
     */
    private boolean isOre (ItemStack stack) {
        
        if (stack == null || stack.getItem() == null)
            return false;
        
        if (Block.getBlockFromItem(stack.getItem()) instanceof BlockOre)
            return true;
        
        for (final int oreID : OreDictionary.getOreIDs(stack))
            if (OreDictionary.getOreName(oreID).startsWith("ore"))
                return true;
            
        if (stack.getDisplayName().matches(".*(^|\\s)([oO]re)($|\\s)."))
            return true;
        
        return false;
    }
    
    /**
     * Tries to get the effective tool for breaking a block.
     * 
     * @param world Instance of the world.
     * @param state The state of the block.
     * @param pos The position of the block.
     * @return String An identifier which can be used to identify the effective tool for
     *         breaking a block.
     */
    private String getEffectiveTool (World world, IBlockState state, BlockPos pos) {
        
        String tool = state.getBlock().getHarvestTool(state);
        
        if (tool == null || tool.isEmpty()) {
            
            final float blockHardness = state.getBlock().getBlockHardness(state, world, pos);
            
            if (blockHardness > 0f)
                for (final Map.Entry<String, ItemStack> entry : overrides.entrySet()) {
                    
                    final ItemStack stack = entry.getValue();
                    
                    if (stack.getItem() instanceof ItemTool && stack.getItem().getStrVsBlock(stack, state) >= ((ItemTool) stack.getItem()).getToolMaterial().getEfficiencyOnProperMaterial()) {
                        
                        tool = entry.getKey();
                        break;
                    }
                }
        }
        
        // TODO add sword hook
        return tool;
    }
    
    static {
        
        overrides.put("pickaxe", new ItemStack(Items.WOODEN_PICKAXE));
        overrides.put("axe", new ItemStack(Items.WOODEN_AXE));
        overrides.put("shovel", new ItemStack(Items.WOODEN_SHOVEL));
    }
}