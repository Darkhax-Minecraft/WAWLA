package net.darkhax.wawla.engine.waila;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.wawla.Wawla;
import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityProvider implements IWailaEntityProvider {
    
    @Override
    public Entity getWailaOverride (IWailaEntityAccessor accessor, IWailaConfigHandler config) {
        
        InfoAccess info = new InfoAccess(accessor.getWorld(), accessor.getPlayer(), accessor.getEntity(), accessor.getNBTData());
        
        if (info.entity != null)
            for (InfoProvider provider : Wawla.entityProviders)
                if (provider.requireEntityOverride(info))
                    info = provider.overrideEntity(info);
                    
        return info.entity;
    }
    
    @Override
    public List<String> getWailaHead (Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
        
        return currenttip;
    }
    
    @Override
    public List<String> getWailaBody (Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
        
        InfoAccess info = new InfoAccess(accessor.getWorld(), accessor.getPlayer(), accessor.getEntity(), accessor.getNBTData());
        
        for (InfoProvider provider : Wawla.entityProviders)
            provider.addEntityInfo(currenttip, info);
            
        return currenttip;
    }
    
    @Override
    public List<String> getWailaTail (Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {
        
        return currenttip;
    }
    
    @Override
    public NBTTagCompound getNBTData (EntityPlayerMP player, Entity ent, NBTTagCompound tag, World world) {
        
        for (InfoProvider provider : Wawla.entityProviders)
            if (provider.requireEntitySync(world, ent))
                provider.writeEntityNBT(world, ent, tag);
                
        return tag;
    }
    
    public static void register (IWailaRegistrar register) {
        
        EntityProvider provider = new EntityProvider();
        register.registerOverrideEntityProvider(provider, Entity.class);
        register.registerBodyProvider(provider, Entity.class);
        register.registerNBTProvider(provider, EntitySkeleton.class);
    }
}