package net.darkhax.wawla.addons.generic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.util.StatCollector;

import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.darkhax.wawla.util.Utilities;

public class AddonGenericTooltips {
    
    @SubscribeEvent
    public void onTooltip (ItemTooltipEvent event) {
        
        if (event.entityPlayer != null && event.entityPlayer.worldObj != null) {
            
            boolean isShifting = Minecraft.getMinecraft().gameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak);
            
            if (event.entityPlayer.worldObj.isRemote) {
                
                Item item = event.itemStack.getItem();
                Block block = Block.getBlockFromItem(item);
                
                // Enchantments
                if (item instanceof ItemEnchantedBook) {
                    
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
                else if (item instanceof ItemArmor) {
                    
                    ItemArmor armor = (ItemArmor) item;
                    event.toolTip.add(StatCollector.translateToLocal("tooltip.wawla.armorprot") + ": " + armor.damageReduceAmount);
                }
                
                if (!(block instanceof BlockAir) && block != null) {
                    
                    float enchPower = block.getEnchantPowerBonus(event.entityPlayer.worldObj, 0, 0, 0);
                    
                    if (enchPower > 0)
                        event.toolTip.add(StatCollector.translateToLocal("tooltip.wawla.enchPower") + ": " + enchPower);
                }
                
                // Dev Tips
                if (Utilities.isDevMode)
                    event.toolTip.add(Item.itemRegistry.getNameForObject(event.itemStack.getItem()));
            }
        }
    }
}
