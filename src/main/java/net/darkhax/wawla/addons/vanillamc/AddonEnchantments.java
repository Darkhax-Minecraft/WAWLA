package net.darkhax.wawla.addons.vanillamc;

import java.util.ArrayList;
import java.util.List;

import net.darkhax.wawla.util.Utilities;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class AddonEnchantments {

    /**
     * A blacklist that enchantments can be added to. Enchantments can be added to this list through IMC.
     */
    public static ArrayList<Enchantment> blacklist = new ArrayList<Enchantment>();

    public void onTooltipDisplayed(ItemStack stack, EntityPlayer player, List<String> toolTip, boolean advanced) {

        if (player.worldObj.isRemote) {

            if (stack.getItem() instanceof ItemEnchantedBook) {

                if (Minecraft.getMinecraft().gameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak)) {

                    Enchantment[] enchArr = Utilities.getEnchantmentsFromStack(stack, true);
                    Enchantment ench = enchArr.length > 0 ? enchArr[0] : null;

                    if (ench != null && !blacklist.contains(ench))
                        Utilities.wrapStringToList(StatCollector.translateToLocal("description." + ench.getName()), 45, false, toolTip);
                }

                else
                    toolTip.add(StatCollector.translateToLocal("tooltip.wawla.shiftEnch"));
            }
        }
    }
}
