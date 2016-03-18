package net.darkhax.wawla.addons.generic;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockVine;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class AddonGenericTiles implements IWailaDataProvider {
    
    @Override
    public ItemStack getWailaStack (IWailaDataAccessor data, IWailaConfigHandler cfg) {
        
        return new ItemStack(data.getStack().getItem(), 1, 0);
    }
    
    @Override
    public List<String> getWailaHead (ItemStack stack, List<String> tip, IWailaDataAccessor data, IWailaConfigHandler cfg) {
        
        return tip;
    }
    
    @Override
    public List<String> getWailaBody (ItemStack stack, List<String> tip, IWailaDataAccessor data, IWailaConfigHandler cfg) {
        
        BlockPos pos = data.getPosition();
        Block block = data.getBlock();
        ItemStack item = data.getPlayer().getHeldItem();
        String tool = (block != null) ? block.getHarvestTool(data.getBlockState()) : "";
        int blockLevel = block.getHarvestLevel(data.getBlockState());
        int itemLevel = (item != null) ? item.getItem().getHarvestLevel(item, tool) : 0;
        
        // Corrective check to prevent chisel from breaking the module.
        if (tool != null && tool.equalsIgnoreCase("chisel")) {
            
            if (block == Blocks.stone)
                tool = "pickaxe";
                
            if (block == Blocks.planks)
                tool = "axe";
        }
        
        // Shows if the tool is the right tier.
        if (item != null && (item.getItem().getToolClasses(item).contains(tool))) {
            
            // When the block is harvestable.
            if (cfg.getConfig(CONFIG_SHOW_HARVESTABILITY) && (blockLevel <= itemLevel || blockLevel == 0))
                tip.add(StatCollector.translateToLocal("tooltip.wawla.canHarvest") + ": " + ((EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocal("tooltip.wawla.yes"))));
                
            // When it's not harvestable.
            else {
                
                if (cfg.getConfig(CONFIG_SHOW_HARVESTABILITY))
                    tip.add(StatCollector.translateToLocal("tooltip.wawla.canHarvest") + ": " + EnumChatFormatting.RED + StatCollector.translateToLocal("tooltip.wawla.no"));
                    
                if (cfg.getConfig(CONFIG_CORRECT_TIER))
                    tip.add(StatCollector.translateToLocal("tooltip.wawla.blockLevel") + ": " + blockLevel);
            }
        }
        
        // Shows correct tool type.
        else if (tool != null && cfg.getConfig(CONFIG_CORRECT_TOOL)) {
            
            String translation = StatCollector.translateToLocal("tooltip.wawla.tooltype." + tool);
            
            if (translation.startsWith("tooltip.wawla.tooltype."))
                tip.add(StatCollector.translateToLocal("tooltip.wawla.toolType") + ": " + tool);
                
            else
                tip.add(StatCollector.translateToLocal("tooltip.wawla.toolType") + ": " + translation);
        }
        
        if (cfg.getConfig(CONFIG_HARDNESS))
            tip.add(StatCollector.translateToLocal("tooltip.wawla.hardness") + ": " + data.getBlock().getBlockHardness(data.getWorld(), data.getPosition()));
            
        if (cfg.getConfig(CONFIG_RESISTANCE))
            tip.add(StatCollector.translateToLocal("tooltip.wawla.resistance") + ": " + data.getBlock().getExplosionResistance(data.getPlayer()));
            
        // Block Progression
        if (cfg.getConfig(CONFIG_BREAK_PROGRESSION) && data.getPlayer().worldObj.isRemote) {
            
            double progress = Utilities.round(Utilities.getBlockDamage(), 2);
            
            if (progress > 0)
                tip.add(StatCollector.translateToLocal("tooltip.wawla.progress") + ": " + (int) (progress * 100) + "%");
        }
        
        if (cfg.getConfig(CONFIG_ENCHANTING)) {
            
            float enchPower = block.getEnchantPowerBonus(data.getWorld(), data.getPosition());
            
            if (enchPower > 0)
                tip.add(StatCollector.translateToLocal("tooltip.wawla.enchPower") + ": " + enchPower);
        }
        return tip;
    }
    
    @Override
    public List<String> getWailaTail (ItemStack stack, List<String> tip, IWailaDataAccessor data, IWailaConfigHandler cfg) {
        
        return tip;
    }
    
    @Override
    public NBTTagCompound getNBTData (EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
        
        return tag;
    }
    
    public static void registerAddon (IWailaRegistrar register) {
        
        AddonGenericTiles dataProvider = new AddonGenericTiles();
        
        String catagory = "Wawla-General";
        register.addConfig(catagory, CONFIG_CORRECT_TOOL);
        register.addConfig(catagory, CONFIG_SHOW_HARVESTABILITY);
        register.addConfig(catagory, CONFIG_CORRECT_TIER);
        register.addConfig(catagory, CONFIG_BREAK_PROGRESSION);
        register.addConfig(catagory, CONFIG_LIGHTLEVEL);
        register.addConfig(catagory, CONFIG_MONSTERLIGHT);
        register.addConfig(catagory, CONFIG_DAYLIGHT);
        register.addConfig(catagory, CONFIG_SLEEPY);
        register.addConfig(catagory, CONFIG_ENCHANTING);
        register.addConfig(catagory, CONFIG_HARDNESS, false);
        register.addConfig(catagory, CONFIG_RESISTANCE, false);
        
        register.registerBodyProvider(dataProvider, Block.class);
        
        // Patch
        register.registerStackProvider(dataProvider, BlockDirectional.class);
        register.registerStackProvider(dataProvider, BlockTorch.class);
        // register.registerStackProvider(dataProvider, BlockLeaves.class);
        register.registerStackProvider(dataProvider, BlockLever.class);
        register.registerStackProvider(dataProvider, BlockButton.class);
        register.registerStackProvider(dataProvider, BlockRailBase.class);
        register.registerStackProvider(dataProvider, BlockPistonBase.class);
        register.registerStackProvider(dataProvider, BlockEndPortalFrame.class);
        register.registerStackProvider(dataProvider, BlockVine.class);
        register.registerStackProvider(dataProvider, BlockAnvil.class);
    }
    
    private static final String CONFIG_CORRECT_TOOL = "wawla.harvest.showTool";
    private static final String CONFIG_SHOW_HARVESTABILITY = "wawla.harvest.showHarvest";
    private static final String CONFIG_CORRECT_TIER = "wawla.harvest.showTier";
    private static final String CONFIG_BREAK_PROGRESSION = "wawla.harvest.showProgress";
    private static final String CONFIG_DAYLIGHT = "wawla.light.showDay";
    private static final String CONFIG_MONSTERLIGHT = "wawla.light.monsterSpawn";
    private static final String CONFIG_LIGHTLEVEL = "wawla.light.lightLevel";
    private static final String CONFIG_SLEEPY = "wawla.bed.sleepable";
    private static final String CONFIG_HARDNESS = "wawla.info.showHardness";
    private static final String CONFIG_RESISTANCE = "wawla.info.showResistance";
    private static final String CONFIG_ENCHANTING = "wawla.info.enchantFactor";
}
