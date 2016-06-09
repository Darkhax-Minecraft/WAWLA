package net.darkhax.wawla.plugins.generic.entities;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraftforge.common.config.Configuration;

public class PluginTameable extends InfoProvider {
    
    private static boolean enabled = true;
    private static boolean showTamed = true;
    private static boolean showSitting = true;
    
    @Override
    public void addEntityInfo (List<String> info, InfoAccess data) {
        
        if (enabled && data.entity instanceof EntityTameable) {
            
            final EntityTameable entity = (EntityTameable) data.entity;
            
            if (showTamed && !entity.isOwner(data.player) && entity.isTamed())
                info.add(ChatFormatting.YELLOW + I18n.format("tooltip.wawla.generic.tamed"));
                
            if (showSitting && entity.isSitting())
                info.add(I18n.format("tooltip.wawla.generic.sitting"));
        }
    }
    
    @Override
    public void syncConfig (Configuration config) {
        
        enabled = config.getBoolean("Tameable", "generic_entities", true, "When enabled, tameable entities will have extra info on the hud");
        showTamed = config.getBoolean("Tameable_Tamed", "generic_entities", true, "When enabled, will tell players that are not the owner if the entity is tamed.");
        showSitting = config.getBoolean("Tameable_Sitting", "generic_entities", true, "When enabled, shows when an entity is sitting.");
    }
}
