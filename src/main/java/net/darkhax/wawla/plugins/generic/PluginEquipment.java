package net.darkhax.wawla.plugins.generic;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.darkhax.wawla.config.Configurable;
import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.darkhax.wawla.plugins.ProviderType;
import net.darkhax.wawla.plugins.WawlaFeature;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

@WawlaFeature(description = "Shows the equipment a mob is wearing", name = "equipment", type = ProviderType.ENTITY)
public class PluginEquipment extends InfoProvider {

    @Configurable(category = "equipment", description = "Show a purple star by name of enchanted item")
    public static boolean enchantmentStar = true;

    @Configurable(category = "equipment", description = "Require sneaking to see entity equipment")
    public static boolean requireSneak = true;

    @Override
    public void addEntityInfo (List<String> info, InfoAccess data) {

        if (data.entity instanceof EntityLivingBase && (data.player.isSneaking() || !requireSneak)) {

            final EntityLivingBase entity = (EntityLivingBase) data.entity;

            for (final EntityEquipmentSlot slot : EntityEquipmentSlot.values()) {

                final ItemStack stack = entity.getItemStackFromSlot(slot);

                if (!stack.isEmpty()) {
                    info.add(I18n.format("tooltip.wawla." + slot.getName()) + ": " + stack.getDisplayName() + (enchantmentStar && stack.isItemEnchanted() ? ChatFormatting.LIGHT_PURPLE + I18n.format("tooltip.wawla.star") : ""));
                }
            }
        }
    }
}
