package net.darkhax.wawla.modules;

import java.util.ArrayList;

import net.darkhax.wawla.util.Utilities;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ModuleEnchantmentBooks {

    /**
     * A blacklist that enchantments can be added to. Enchantments can be added to this list through IMC.
     */
    public static ArrayList<Enchantment> blacklist = new ArrayList<Enchantment>();

    public ModuleEnchantmentBooks(boolean enabled) {

        if (enabled)
            MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {

        if (event.showAdvancedItemTooltips && event.itemStack.getItem() instanceof ItemEnchantedBook) {

            Enchantment ench = Utilities.getEnchantmentsFromStack(event.itemStack, true)[0];

            if (!blacklist.contains(ench)) {

                Utilities.wrapStringToList(StatCollector.translateToLocal("description." + ench.getName()), 45, false, event.toolTip);
            }
        }
    }
}