package net.darkhax.wawla.engine.waila;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.wawla.Wawla;
import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileProvider implements IWailaDataProvider {
    
    @Override
    public ItemStack getWailaStack (IWailaDataAccessor accessor, IWailaConfigHandler config) {
        
        InfoAccess info = new InfoAccess(accessor.getMOP(), accessor.getWorld(), accessor.getPlayer(), accessor.getStack(), accessor.getBlockState(), accessor.getPosition(), accessor.getSide(), accessor.getNBTData());
        
        if (info.isValidBlock())
            for (final InfoProvider provider : Wawla.tileProviders)
                if (provider.requireTileOverride(info))
                    info = provider.overrideTile(info);
                    
        return info.stack;
    }
    
    @Override
    public List<String> getWailaHead (ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        
        return currenttip;
    }
    
    @Override
    public List<String> getWailaBody (ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        
        final InfoAccess info = new InfoAccess(accessor.getMOP(), accessor.getWorld(), accessor.getPlayer(), accessor.getStack(), accessor.getBlockState(), accessor.getPosition(), accessor.getSide(), accessor.getNBTData());
        
        for (final InfoProvider provider : Wawla.tileProviders)
            provider.addTileInfo(currenttip, info);
            
        return currenttip;
    }
    
    @Override
    public List<String> getWailaTail (ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        
        return currenttip;
    }
    
    @Override
    public NBTTagCompound getNBTData (EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
        
        for (final InfoProvider provider : Wawla.tileProviders)
            if (provider.requireTileSync(world, te))
                provider.writeTileNBT(world, te, tag);
                
        return tag;
    }
    
    public static void register (IWailaRegistrar register) {
        
        final TileProvider provider = new TileProvider();
        // register.registerStackProvider(provider, Block.class);
        register.registerBodyProvider(provider, Block.class);
        register.registerNBTProvider(provider, Block.class);
    }
}