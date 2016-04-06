package net.darkhax.icse.plugins.entity;

import java.util.List;

import net.darkhax.icse.lib.DataAccess;
import net.darkhax.icse.plugins.InfoPlugin;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.text.translation.I18n;

public class PluginHealth implements InfoPlugin {

    public void addEntityInfo (List<String> info, DataAccess data) {
        
        if (data.entity instanceof EntityLiving) {
            
            EntityLiving living = (EntityLiving) data.entity;
            info.add(I18n.translateToLocal("tooltip.icse.health") + ": " + living.getHealth() + "/" + living.getMaxHealth());
        }
    }
}
