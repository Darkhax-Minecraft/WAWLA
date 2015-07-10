package net.darkhax.wawla.addons.developer;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class AddonDeveloperEntities implements IWailaEntityProvider {
    
    public AddonDeveloperEntities() {
    
    }
    
    @Override
    public Entity getWailaOverride (IWailaEntityAccessor data, IWailaConfigHandler cfg) {
    
        return data.getEntity();
    }
    
    @Override
    public List<String> getWailaHead (Entity entity, List<String> tip, IWailaEntityAccessor data, IWailaConfigHandler cfg) {
    
        return tip;
    }
    
    @Override
    public List<String> getWailaBody (Entity entity, List<String> tip, IWailaEntityAccessor data, IWailaConfigHandler cfg) {
    
        boolean isKeySprinting = Minecraft.getMinecraft().gameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSprint);
        
        if (data.getPlayer().isSneaking())
            tip.add("Entity: " + data.getEntity().getClass().toString());
        
        if (isKeySprinting)
            Utilities.wrapStringToList(data.getNBTData().toString(), 45, true, tip);
        
        return tip;
    }
    
    @Override
    public List<String> getWailaTail (Entity entity, List<String> tip, IWailaEntityAccessor data, IWailaConfigHandler cfg) {
    
        return tip;
    }
    
    @Override
    public NBTTagCompound getNBTData (EntityPlayerMP player, Entity entity, NBTTagCompound tag, World world) {
    
        if (entity != null)
            entity.writeToNBT(tag);
        
        return tag;
    }
    
    public static void registerAddon (IWailaRegistrar register) {
    
        AddonDeveloperEntities dataProvider = new AddonDeveloperEntities();
        register.registerBodyProvider(dataProvider, Entity.class);
        register.registerNBTProvider(dataProvider, Entity.class);
    }
}