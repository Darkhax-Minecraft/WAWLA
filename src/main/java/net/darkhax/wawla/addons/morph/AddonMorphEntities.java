package net.darkhax.wawla.addons.morph;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class AddonMorphEntities implements IWailaEntityProvider {
    
    @Override
    public Entity getWailaOverride (IWailaEntityAccessor data, IWailaConfigHandler cfg) {
        
        String morphID = "";
        Entity fakeEntity = data.getEntity();
        
        if (data.getNBTData().hasKey(KEY_MORPH_ID))
            morphID = data.getNBTData().getString(KEY_MORPH_ID);
            
        if (!morphID.isEmpty())
            fakeEntity = EntityList.createEntityByName(data.getNBTData().getString(KEY_MORPH_ID), data.getWorld());
            
        return fakeEntity != null ? fakeEntity : data.getEntity();
    }
    
    @Override
    public List<String> getWailaHead (Entity entity, List<String> tip, IWailaEntityAccessor data, IWailaConfigHandler cfg) {
        
        return tip;
    }
    
    @Override
    public List<String> getWailaBody (Entity entity, List<String> tip, IWailaEntityAccessor data, IWailaConfigHandler cfg) {
        
        return tip;
    }
    
    @Override
    public List<String> getWailaTail (Entity entity, List<String> tip, IWailaEntityAccessor data, IWailaConfigHandler cfg) {
        
        return tip;
    }
    
    @Override
    public NBTTagCompound getNBTData (EntityPlayerMP player, Entity entity, NBTTagCompound tag, World world) {
        
        if (entity != null && entity.getEntityData() != null) {
            
            NBTTagCompound morphTag = Utilities.getDeepTagCompound(entity.getEntityData(), new String[] { "PlayerPersisted", "MorphSave", "morphData", "nextState", "entInstanceTag" });
            
            if (morphTag.hasKey("id"))
                tag.setString(KEY_MORPH_ID, morphTag.getString("id"));
        }
        
        return tag;
    }
    
    public static void registerAddon (IWailaRegistrar register) {
        
        AddonMorphEntities dataProvider = new AddonMorphEntities();
        register.registerOverrideEntityProvider(dataProvider, EntityOtherPlayerMP.class);
        register.registerNBTProvider(dataProvider, EntityPlayerMP.class);
    }
    
    public static final String KEY_MORPH_ID = "WawlaMorphID";
}
