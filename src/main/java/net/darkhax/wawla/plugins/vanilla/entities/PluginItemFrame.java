package net.darkhax.wawla.plugins.vanilla.entities;

import java.util.List;

import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;

public class PluginItemFrame extends InfoProvider {
    
    private static boolean enabled;
    
    @Override
    public void addEntityInfo (List<String> info, InfoAccess data) {
        
        if (enabled && data.entity instanceof EntityItemFrame) {
            
            final ItemStack stack = ((EntityItemFrame) data.entity).getDisplayedItem();
            
            if (stack != null && stack.getItem() != null)
                info.add(I18n.format("tooltip.wawla.item") + ": " + stack.getDisplayName());
        }
    }
    
    @Override
    public void syncConfig (Configuration config) {
        
        enabled = config.getBoolean("Item_Frame", "vanilla_entities", true, "When enabled, shows the name of the Item in an item frame.");
    }
}
