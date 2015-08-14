package net.darkhax.wawla.addons.vanillamc;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.block.BlockBeacon;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockSkull;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class AddonVanillaTiles implements IWailaDataProvider {
    
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
    
        // Beacon
        if (data.getTileEntity() instanceof TileEntityBeacon) {
            
            NBTTagCompound tag = data.getNBTData();
            int level = tag.getInteger("Levels");
            int primary = tag.getInteger("Primary");
            int secondary = tag.getInteger("Secondary");
            
            if (cfg.getConfig(CONFIG_BEACON_LEVEL))
                tip.add(StatCollector.translateToLocal("tooltip.wawla.levels") + ": " + level);
            
            if (cfg.getConfig(CONFIG_BEACON_PRIMARY) && primary > 0)
                tip.add(StatCollector.translateToLocal("tooltip.wawla.primary") + ": " + StatCollector.translateToLocal(Potion.potionTypes[primary].getName()));
            
            if (cfg.getConfig(CONFIG_BEACON_SECONDARY) && secondary > 0)
                tip.add(StatCollector.translateToLocal("tooltip.wawla.secondary") + ": " + StatCollector.translateToLocal(Potion.potionTypes[secondary].getName()));
        }
        
        // Furnace
        else if (data.getTileEntity() instanceof TileEntityFurnace) {
            
            TileEntityFurnace furnace = (TileEntityFurnace) data.getTileEntity();
            int burnTime = data.getNBTData().getInteger("BurnTime") / 20;
            
            if (burnTime > 0 && cfg.getConfig(CONFIG_FURNACE_BURNTIME))
                tip.add(StatCollector.translateToLocal("tooltip.wawla.burnTime") + ": " + burnTime + " " + StatCollector.translateToLocal("tooltip.wawla.seconds"));
            
            if (data.getPlayer().isSneaking()) {
                
                ItemStack[] furnaceStacks = Utilities.getInventoryStacks(data.getNBTData(), 3);
                
                if (furnaceStacks[0] != null && cfg.getConfig(CONFIG_FURNACE_INPUT))
                    tip.add(StatCollector.translateToLocal("tooltip.wawla.input") + ": " + furnaceStacks[0].getDisplayName() + " X " + furnaceStacks[0].stackSize);
                
                if (furnaceStacks[1] != null && cfg.getConfig(CONFIG_FURNACE_FUEL))
                    tip.add(StatCollector.translateToLocal("tooltip.wawla.fuel") + ": " + furnaceStacks[1].getDisplayName() + " X " + furnaceStacks[1].stackSize);
                
                if (furnaceStacks[2] != null && cfg.getConfig(CONFIG_FURNACE_OUTPUT))
                    tip.add(StatCollector.translateToLocal("tooltip.wawla.output") + ": " + furnaceStacks[2].getDisplayName() + " X " + furnaceStacks[2].stackSize);
            }
        }
        
        // Player Skull
        if (data.getTileEntity() instanceof TileEntitySkull && cfg.getConfig(CONFIG_PLAYER_SKULL) && data.getNBTData().hasKey("Owner"))
            tip.add(StatCollector.translateToLocal("tooltip.wawla.owner") + ": " + NBTUtil.func_152459_a(data.getNBTData().getCompoundTag("Owner")).getName());
        
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
    
        AddonVanillaTiles dataProvider = new AddonVanillaTiles();
        
        register.addConfig("Wawla-Blocks", CONFIG_BEACON_LEVEL);
        register.addConfig("Wawla-Blocks", CONFIG_BEACON_PRIMARY);
        register.addConfig("Wawla-Blocks", CONFIG_BEACON_SECONDARY);
        register.registerBodyProvider(dataProvider, BlockBeacon.class);
        register.registerNBTProvider(dataProvider, BlockBeacon.class);
        
        register.addConfig("Wawla-Blocks", CONFIG_FURNACE_INPUT);
        register.addConfig("Wawla-Blocks", CONFIG_FURNACE_OUTPUT);
        register.addConfig("Wawla-Blocks", CONFIG_FURNACE_FUEL);
        register.addConfig("Wawla-Blocks", CONFIG_FURNACE_BURNTIME);
        register.registerBodyProvider(dataProvider, BlockFurnace.class);
        register.registerNBTProvider(dataProvider, BlockFurnace.class);
        
        register.addConfig("Wawla-Blocks", CONFIG_PLAYER_SKULL);
        register.registerBodyProvider(dataProvider, BlockSkull.class);
        register.registerNBTProvider(dataProvider, BlockSkull.class);
    }
    
    private static final String CONFIG_BEACON_LEVEL = "wawla.beacon.showLevels";
    private static final String CONFIG_BEACON_PRIMARY = "wawla.beacon.showPrimary";
    private static final String CONFIG_BEACON_SECONDARY = "wawla.beacon.showSecondary";
    private static final String CONFIG_FURNACE_INPUT = "wawla.furnace.input";
    private static final String CONFIG_FURNACE_OUTPUT = "wawla.furnace.output";
    private static final String CONFIG_FURNACE_FUEL = "wawla.furnace.fuel";
    private static final String CONFIG_FURNACE_BURNTIME = "wawla.furnace.burntime";
    private static final String CONFIG_PLAYER_SKULL = "wawla.showHead";
}
