package net.darkhax.wawla.engine.waila;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.FeatureManager;
import net.darkhax.wawla.plugins.InfoProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityProvider implements IWailaEntityProvider {

    @Override
    public Entity getWailaOverride (IWailaEntityAccessor accessor, IWailaConfigHandler config) {

        InfoAccess info = new InfoAccess(accessor.getWorld(), accessor.getPlayer(), accessor.getEntity(), accessor.getNBTData());

        if (info.entity != null) {
            for (final InfoProvider provider : FeatureManager.entityProviders) {
                if (provider.requireEntityOverride(info)) {
                    info = provider.overrideEntity(info);
                }
            }
        }
        
        return info.entity;
    }

    @Override
    public List<String> getWailaHead (Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {

        return currenttip;
    }

    @Override
    public List<String> getWailaBody (Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {

        final InfoAccess info = new InfoAccess(accessor.getWorld(), accessor.getPlayer(), accessor.getEntity(), accessor.getNBTData());

        for (final InfoProvider provider : FeatureManager.entityProviders) {
            provider.addEntityInfo(currenttip, info);
        }

        return currenttip;
    }

    @Override
    public List<String> getWailaTail (Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler config) {

        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData (EntityPlayerMP player, Entity ent, NBTTagCompound tag, World world) {

        for (final InfoProvider provider : FeatureManager.entityProviders) {
            if (provider.requireEntitySync(world, ent)) {
                provider.writeEntityNBT(world, ent, tag);
            }
        }
        
        return tag;
    }

    public static void register (IWailaRegistrar register) {

        final EntityProvider provider = new EntityProvider();
        register.registerOverrideEntityProvider(provider, Entity.class);
        register.registerBodyProvider(provider, Entity.class);
        register.registerNBTProvider(provider, Entity.class);
    }
}