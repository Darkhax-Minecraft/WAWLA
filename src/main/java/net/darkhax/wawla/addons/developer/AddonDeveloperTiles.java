package net.darkhax.wawla.addons.developer;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class AddonDeveloperTiles implements IWailaDataProvider {
    
    public AddonDeveloperTiles() {
    
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
    
        boolean isKeySprinting = Minecraft.getMinecraft().gameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSprint);
        
        if (data.getPlayer().isSneaking()) {
            
            tip.add("Block: " + data.getBlock().getClass().toString());
            
            if (data.getTileEntity() != null)
                tip.add("Class: " + data.getTileEntity().getClass().toString());
        }
        
        if (isKeySprinting)
            Utilities.wrapStringToList(data.getNBTData().toString(), 45, true, tip);
        
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
    
        AddonDeveloperTiles dataProvider = new AddonDeveloperTiles();
        register.registerBodyProvider(dataProvider, Block.class);
        register.registerNBTProvider(dataProvider, Block.class);
    }
}
