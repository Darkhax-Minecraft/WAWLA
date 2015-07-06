package net.darkhax.wawla.addons.generic;

import net.darkhax.wawla.util.Utilities;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class AddonGenericTooltips {
    
    @SubscribeEvent
    public void onTooltip (ItemTooltipEvent event) {
    
        if (event.entityPlayer != null && event.entityPlayer.worldObj != null) {
            
            boolean isShifting = Minecraft.getMinecraft().gameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak);
            
            if (event.entityPlayer.worldObj.isRemote) {
                
                // Enchantments
                if (event.itemStack.getItem() instanceof ItemEnchantedBook) {
                    
                    if (isShifting) {
                        
                        Enchantment[] enchArr = Utilities.getEnchantmentsFromStack(event.itemStack, true);
                        Enchantment ench = enchArr.length > 0 ? enchArr[0] : null;
                        
                        if (ench != null) {
                            
                            String translation = StatCollector.translateToLocal("description." + ench.getName());
                            
                            if (translation.startsWith("description."))
                                event.toolTip.add(StatCollector.translateToLocal("tooltip.wawla.missingEnch"));
                            
                            else
                                Utilities.wrapStringToList(StatCollector.translateToLocal("description." + ench.getName()), 45, false, event.toolTip);
                        }
                    }
                    
                    else
                        event.toolTip.add(StatCollector.translateToLocal("tooltip.wawla.shiftEnch"));
                }
                
                // Armor Points
                else if (event.itemStack.getItem() instanceof ItemArmor) {
                    
                    ItemArmor armor = (ItemArmor) event.itemStack.getItem();
                    event.toolTip.add(StatCollector.translateToLocal("tooltip.wawla.armorprot") + ": " + armor.damageReduceAmount);
                }
            }
        }
    }
}
