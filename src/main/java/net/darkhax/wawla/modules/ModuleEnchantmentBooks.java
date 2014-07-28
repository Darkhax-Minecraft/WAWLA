package net.darkhax.wawla.modules;

import java.util.ArrayList;
import java.util.List;

import net.darkhax.wawla.util.Utilities;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ModuleEnchantmentBooks extends Module {

    public ModuleEnchantmentBooks(boolean enabled) {

        super(enabled);
    }

    /**
     * A blacklist that enchantments can be added to. Enchantments can be added to this list through IMC.
     */
    public static ArrayList<Enchantment> blacklist = new ArrayList<Enchantment>();

    @Override
    public void onTooltipDisplayed(ItemStack stack, List<String> toolTip, boolean advanced) {

        if (advanced && stack.getItem() instanceof ItemEnchantedBook) {

            Enchantment ench = Utilities.getEnchantmentsFromStack(stack, true)[0];

            if (!blacklist.contains(ench))
                Utilities.wrapStringToList(StatCollector.translateToLocal("description." + ench.getName()), 45, false, toolTip);
        }
    }
}