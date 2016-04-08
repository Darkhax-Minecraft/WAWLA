package net.darkhax.wawla.plugins.vanilla.entities;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.config.Configuration;

public class PluginEquipment extends InfoProvider{

    private static boolean enabled = true;
    private static boolean enchantmentStar = true;
    
    @Override
    public void addEntityInfo (List<String> info, InfoAccess data) {
        
        if (enabled && data.entity instanceof EntityLivingBase && data.player.isSneaking()) {
            
            final EntityLivingBase entity = (EntityLivingBase) data.entity;
            
            for (EntityEquipmentSlot slot : EntityEquipmentSlot.values()) {
                
                final ItemStack stack = entity.getItemStackFromSlot(slot);
                
                if (stack != null && stack.getItem() != null)
                    info.add(I18n.translateToLocal("tooltip.wawla." + slot.getName()) + ": " + stack.getDisplayName() + (enchantmentStar && stack.isItemEnchanted() ? ChatFormatting.LIGHT_PURPLE + I18n.translateToLocal("tooltip.wawla.star"): ""));
            }
        }
    }
    
    @Override
    public void syncConfig (Configuration config) {
        
        enabled = config.getBoolean("Equipment", "generic_entities", true, "When enabled, a list of armor that the entity is wearing will be added to the HUD while the player is sneaking.");
        enchantmentStar = config.getBoolean("Equipment_Star", "generic_entities", true, "When enabled, names of equipment will have a purple star beside them when enchanted.");
    }
}
