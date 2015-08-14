package net.darkhax.wawla.addons.jewelrycraft;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.common.Loader;

public class AddonJewelrycraftTiles implements IWailaDataProvider {
    
    public AddonJewelrycraftTiles() {
    
        if (Loader.isModLoaded("Jewelrycraft2")) {
            
            try {
                
                classTileEntitySmelter = Class.forName("darkknight.jewelrycraft.tileentity.TileEntitySmelter");
                classTileEntityMolder = Class.forName("darkknight.jewelrycraft.tileentity.TileEntityMolder");
            }
            
            catch (ClassNotFoundException e) {
                
                e.printStackTrace();
            }
            
            catch (SecurityException e) {
                
                e.printStackTrace();
            }
        }
    }
    
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
    
        if (data.getBlock() != null && data.getTileEntity() != null) {
            
            if (Utilities.compareTileEntityByClass(data.getTileEntity(), classTileEntitySmelter)) {
                
                if (data.getNBTData().getBoolean("hasMoltenMetal")) {
                    
                    NBTTagCompound moltenTag = data.getNBTData().getCompoundTag("moltenMetal");
                    ItemStack moltenStack = getMetalStack(moltenTag);
                    
                    if (cfg.getConfig(CONFIG_MOLTEN_AMOUNT))
                        tip.add(StatCollector.translateToLocal("tooltip.wawla.jewelry.molten") + ": " + moltenStack.getDisplayName() + " X " + Utilities.round(data.getNBTData().getFloat("quantity") * 10, 2));
                }
                
                if (data.getNBTData().getBoolean("hasMetal")) {
                    
                    NBTTagCompound moltenTag = data.getNBTData().getCompoundTag("metal");
                    ItemStack metalStack = getMetalStack(moltenTag);
                    
                    if (cfg.getConfig(CONFIG_METAL_AMOUNT))
                        tip.add(StatCollector.translateToLocal("tooltip.wawla.jewelry.unmelted") + ": " + metalStack.getDisplayName() + " X " + metalStack.stackSize);
                    
                    if (cfg.getConfig(CONFIG_MELTING_TIME))
                        tip.add(StatCollector.translateToLocal("tooltip.wawla.jewelry.melttime") + ": " + data.getNBTData().getInteger("melting") / 20 + " " + StatCollector.translateToLocal("tooltip.wawla.seconds"));
                }
            }
            
            if (Utilities.compareTileEntityByClass(data.getTileEntity(), classTileEntityMolder))
                if (cfg.getConfig(CONFIG_COOLING_TIME) && data.getNBTData().getInteger("cooling") > 0)
                    tip.add(StatCollector.translateToLocal("tooltip.wawla.jewelry.cooling") + ": " + data.getNBTData().getInteger("cooling") / 20 + " " + StatCollector.translateToLocal("tooltip.wawla.seconds"));
        }
        
        return tip;
    }
    
    public ItemStack getMetalStack (NBTTagCompound metalTag) {
    
        return new ItemStack(Item.getItemById(metalTag.getShort("id")), metalTag.getByte("Count"), metalTag.getShort("Damage"));
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
    
        AddonJewelrycraftTiles dataProvider = new AddonJewelrycraftTiles();
        
        register.addConfig("Jewelrycraft", CONFIG_MOLTEN_AMOUNT);
        register.addConfig("Jewelrycraft", CONFIG_METAL_AMOUNT);
        register.addConfig("Jewelrycraft", CONFIG_MELTING_TIME);
        register.addConfig("Jewelrycraft", CONFIG_COOLING_TIME);
        register.registerBodyProvider(dataProvider, classTileEntitySmelter);
        register.registerNBTProvider(dataProvider, classTileEntitySmelter);
        register.registerBodyProvider(dataProvider, classTileEntityMolder);
        register.registerNBTProvider(dataProvider, classTileEntityMolder);
    }
    
    private static final String CONFIG_MOLTEN_AMOUNT = "wawla.jewelrycraft.showMoltenAmount";
    private static final String CONFIG_METAL_AMOUNT = "wawla.jewelrycraft.showMetalAmount";
    private static final String CONFIG_MELTING_TIME = "wawla.jewelrycraft.showMeltTime";
    private static final String CONFIG_COOLING_TIME = "wawla.jewelrycraft.showCooling";
    
    public static Class classTileEntitySmelter = null;
    public static Class classTileEntityMolder = null;
}
