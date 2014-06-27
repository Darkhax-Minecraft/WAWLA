package net.darkhax.wawla.handler;

import net.darkhax.wawla.modules.Module;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ForgeEventHandler {

    public ForgeEventHandler () {
        
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {

        for (Module module : Module.modules)
            module.onTooltipDisplayed(event.itemStack, event.toolTip, event.showAdvancedItemTooltips);
    }
}
