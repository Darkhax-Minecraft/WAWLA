package net.darkhax.icse.plugins.entity;

import java.util.List;

import net.darkhax.icse.lib.DataAccess;
import net.darkhax.icse.plugins.InfoPlugin;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLiving;

public class PluginHealth extends InfoPlugin {
    
    @Override
    public void addEntityInfo (List<String> info, DataAccess data) {
        
        if (data.entity instanceof EntityLiving) {
            
            final EntityLiving living = (EntityLiving) data.entity;
            info.add(I18n.format("tooltip.icse.health") + ": " + (int) living.getHealth() + "/" + (int) living.getMaxHealth());
        }
    }
}
