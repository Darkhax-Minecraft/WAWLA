package net.darkhax.wawla.modules;

import java.util.ArrayList;
import java.util.List;

import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;

public class ModuleEnchantmentBooks extends Module {
    
    public ModuleEnchantmentBooks(boolean enabled) {
    
        super(enabled);
    }
    
    /**
     * A blacklist that enchantments can be added to. Enchantments can be added to this list
     * through IMC.
     */
    public static ArrayList<Enchantment> blacklist = new ArrayList<Enchantment>();
    
    @Override
    public void onTooltipDisplayed (ItemStack stack, EntityPlayer player, List<String> toolTip, boolean advanced) {
    
        if (stack.getItem() instanceof ItemEnchantedBook) {
            
            if (Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                
                Enchantment ench = Utilities.getEnchantmentsFromStack(stack, true)[0];
                
                if (!blacklist.contains(ench))
                    Utilities.wrapStringToList(StatCollector.translateToLocal("description." + ench.getName()), 45, false, toolTip);
            }
            
            else
                toolTip.add(StatCollector.translateToLocal("tooltip.wawla.shiftEnch"));
        }
    }
    
    @Override
    public void onWailaRegistrar (IWailaRegistrar register) {
    
        // TODO need way to get IWailaConfiguration
        register.addConfig("Wawla", "wawla.showEnchDesc");
    }
}