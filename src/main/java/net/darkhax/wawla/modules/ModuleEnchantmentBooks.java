package net.darkhax.wawla.modules;

import java.util.ArrayList;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class ModuleEnchantmentBooks {

    public static ArrayList<Enchantment> blacklist = new ArrayList<Enchantment>();
    
    public ModuleEnchantmentBooks(Boolean enabled) {
        
        if (enabled)
            MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        
        System.out.println("1");
        if (event.showAdvancedItemTooltips && event.itemStack.getItem() instanceof ItemEnchantedBook) {
            
            System.out.println("2");
            Enchantment ench = Utilities.getEnchantmentsFromStack(event.itemStack, true)[0];
            
            if (!blacklist.contains(ench)) {
                
                System.out.println("3");
                Utilities.wrapStringToList(StatCollector.translateToLocal("description." + ench.getName()), 38, false, event.toolTip);
            }
        }
    }
}