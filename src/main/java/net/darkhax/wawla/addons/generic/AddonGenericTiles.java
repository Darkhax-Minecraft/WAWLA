package net.darkhax.wawla.addons.generic;

import java.lang.reflect.Field;
import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.wawla.addons.tinkersconstruct.AddonTinkersTiles;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AddonGenericTiles implements IWailaDataProvider {
    
    @Override
    public ItemStack getWailaStack (IWailaDataAccessor data, IWailaConfigHandler cfg) {
    
        return data.getStack();
    }
    
    @Override
    public List<String> getWailaHead (ItemStack stack, List<String> tip, IWailaDataAccessor data, IWailaConfigHandler cfg) {
    
        return tip;
    }
    
    @Override
    public List<String> getWailaBody (ItemStack stack, List<String> tip, IWailaDataAccessor data, IWailaConfigHandler cfg) {
    
        // tip.add(data.getTileEntity().getClass().toString());
        // Utilities.wrapStringToList(data.getNBTData().toString(), 50, true, tip);
        
        MovingObjectPosition pos = data.getPosition();
        Block block = data.getBlock();
        ItemStack item = data.getPlayer().getHeldItem();
        String tool = (block != null) ? block.getHarvestTool(data.getMetadata()) : "";
        int blockLevel = block.getHarvestLevel(data.getMetadata());
        int itemLevel = (item != null) ? item.getItem().getHarvestLevel(item, tool) : 0;
        
        // Corrective check to prevent chisel from breaking the module.
        if (tool != null && tool.equalsIgnoreCase("chisel")) {
            
            if (block == Blocks.stone)
                tool = "pickaxe";
            
            if (block == Blocks.planks)
                tool = "axe";
        }
        
        // Shows if the tool is the right tier.
        if (item != null && (item.getItem().getToolClasses(item).contains(tool) || AddonTinkersTiles.canHarvest(item, tool))) {
            
            // When the block is harvestable.
            if (cfg.getConfig(showHarvestable) && (blockLevel <= itemLevel || blockLevel == 0))
                tip.add(StatCollector.translateToLocal("tooltip.wawla.canHarvest") + ": " + ((EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocal("tooltip.wawla.yes"))));
            
            // When it's not harvestable.
            else {
                
                if (cfg.getConfig(showHarvestable))
                    tip.add(StatCollector.translateToLocal("tooltip.wawla.canHarvest") + ": " + EnumChatFormatting.RED + StatCollector.translateToLocal("tooltip.wawla.no"));
                
                if (cfg.getConfig(showTier))
                    tip.add(StatCollector.translateToLocal("tooltip.wawla.blockLevel") + ": " + blockLevel);
                
                // Shows correct tool type.
                if (tool != null && cfg.getConfig(showTool)) {
                    
                    String translation = StatCollector.translateToLocal("tooltip.wawla.tooltype." + tool);
                    
                    if (translation.startsWith("tooltip.wawla.tooltype."))
                        tip.add(StatCollector.translateToLocal("tooltip.wawla.toolType") + ": " + tool);
                    
                    else
                        tip.add(StatCollector.translateToLocal("tooltip.wawla.toolType") + ": " + translation);
                }
            }
        }
        
        // Light level
        if (cfg.getConfig(showLightLevel) && (!data.getWorld().isBlockNormalCubeDefault(data.getPosition().blockX, data.getPosition().blockY + 1, data.getPosition().blockZ, false) || data.getWorld().isAirBlock(data.getPosition().blockX, data.getPosition().blockY + 1, data.getPosition().blockZ))) {
            
            int dayLevel = Utilities.getBlockLightLevel(data.getWorld(), data.getPosition().blockX, data.getPosition().blockY, data.getPosition().blockZ, true);
            int nightLevel = Utilities.getBlockLightLevel(data.getWorld(), data.getPosition().blockX, data.getPosition().blockY, data.getPosition().blockZ, false);
            
            String display = StatCollector.translateToLocal("tooltip.wawla.lightLevel") + ": ";
            
            if (cfg.getConfig(showMonsterSpawn)) {
                
                if (nightLevel <= 7)
                    display = display + EnumChatFormatting.DARK_RED + "" + nightLevel + " ";
                
                else if (nightLevel > 7)
                    display = display + EnumChatFormatting.GREEN + "" + nightLevel + " ";
            }
            
            if (cfg.getConfig(showDay))
                display = display + EnumChatFormatting.YELLOW + "(" + dayLevel + ")";
            
            tip.add(display);
        }
        
        // Block Progression
        if (cfg.getConfig(showProgress) && data.getPlayer().worldObj.isRemote && currentBlockDamage != null) {
            
            double progress = Utilities.round(getBlockDamage(), 2);
            
            if (progress > 0)
                tip.add(StatCollector.translateToLocal("tooltip.wawla.progress") + ": " + (int) (progress * 100) + "%");
        }
        
        return tip;
    }
    
    @Override
    public List<String> getWailaTail (ItemStack stack, List<String> tip, IWailaDataAccessor data, IWailaConfigHandler cfg) {
    
        return tip;
    }
    
    @Override
    public NBTTagCompound getNBTData (EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) {
    
        if (te != null)
            te.writeToNBT(tag);
        
        return tag;
    }
    
    public static void registerAddon (IWailaRegistrar register) {
    
        AddonGenericTiles dataProvider = new AddonGenericTiles();
        
        register.addConfig("Wawla-General", showTool);
        register.addConfig("Wawla-General", showHarvestable);
        register.addConfig("Wawla-General", showTier);
        register.addConfig("Wawla-General", showProgress);
        
        register.addConfig("Wawla-General", showLightLevel);
        register.addConfig("Wawla-General", showMonsterSpawn);
        register.addConfig("Wawla-General", showDay);
        
        register.registerBodyProvider(dataProvider, Block.class);
        register.registerNBTProvider(dataProvider, Block.class);
        
        if (FMLCommonHandler.instance().getSide().equals(Side.CLIENT))
            currentBlockDamage = ReflectionHelper.findField(PlayerControllerMP.class, "g", "field_78770_f", "curBlockDamageMP");
    }
    
    private static Field currentBlockDamage;
    
    private static String showTool = "wawla.harvest.showTool";
    private static String showHarvestable = "wawla.harvest.showHarvest";
    private static String showTier = "wawla.harvest.showTier";
    private static String showProgress = "wawla.harvest.showProgress";
    
    private static String showDay = "wawla.light.showDay";
    private static String showMonsterSpawn = "wawla.light.monsterSpawn";
    private static String showLightLevel = "wawla.light.lightLevel";
    
    /**
     * A client sided method used to retrieve the progression of the block currently being
     * mined by the player. This method is client side only, and refers to only the one
     * instance of the player. Do not try to use this method to get data for multiple players,
     * or for server sided things.
     * 
     * @return float: A float value representing how much time is left for the block being
     *         broken to break. 0 = no damage has been done. 1 = the block is broken.
     */
    @SideOnly(Side.CLIENT)
    public float getBlockDamage () {
    
        try {
            
            return currentBlockDamage.getFloat(Minecraft.getMinecraft().playerController);
        }
        
        catch (IllegalArgumentException e) {
            
        }
        
        catch (IllegalAccessException e) {
            
        }
        
        return 0;
    }
}
