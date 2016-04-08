package net.darkhax.icse.plugins.entity;

import java.util.List;

import net.darkhax.icse.lib.DataAccess;
import net.darkhax.icse.plugins.InfoPlugin;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

/**
 * This plugin fixes an issue with EntityItem's display name showing untranslated text. This
 * overrides the name with the name of the held ItemStack.
 */
public class PluginEntityItem extends InfoPlugin {
    
    @Override
    public void addEntityInfo (List<String> info, DataAccess data) {
        
        if (data.entity instanceof EntityItem) {
            
            final ItemStack stack = ((EntityItem) data.entity).getEntityItem();
            
            if (stack != null && stack.getItem() != null) {
               
                info.set(0, stack.getDisplayName());
                stack.getItem().addInformation(stack, data.player, info, false);
            }
        }
    }
}
