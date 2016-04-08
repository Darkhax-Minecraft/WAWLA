package net.darkhax.wawla.plugins.vanilla.entities;

import java.util.List;

import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.config.Configuration;

public class PluginArmorPoints extends InfoProvider {
    
    private static boolean enabled = true;
    
    @Override
    public void addEntityInfo (List<String> info, InfoAccess data) {
        
        if (enabled && data.entity instanceof EntityLivingBase) {
            
            final EntityLivingBase entity = (EntityLivingBase) data.entity;
            final int armorPoints = entity.getTotalArmorValue();
            
            if (armorPoints > 0)
                info.add(I18n.translateToLocal("tooltip.wawla.generic.armor") + ": " + armorPoints);
        }
    }
    
    @Override
    public void syncConfig (Configuration config) {
        
        enabled = config.getBoolean("Armor_Points", "generic_entities", true, "When enabled, the total amount of armor points will be added to the HUD, if it is greater than 0.");
    }
}
