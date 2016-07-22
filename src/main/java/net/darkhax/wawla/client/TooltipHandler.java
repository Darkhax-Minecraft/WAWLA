package net.darkhax.wawla.client;

import java.util.Set;

import net.darkhax.icse.lib.Utilities;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TooltipHandler {
    
    private final static String CATAGORY = "item_tooltips";
    
    private static boolean showEnchantmentPower = true;
    private static boolean enchantmentDescription = true;
    private static boolean enchantmentMod = true;
    
    @SubscribeEvent
    public void onItemTooltip (ItemTooltipEvent event) {
        
        if (event.getEntityPlayer() != null && event.getEntityPlayer().worldObj != null && event.getItemStack() != null) {
            
            final KeyBinding keyBindSneak = Minecraft.getMinecraft().gameSettings.keyBindSneak;
            final boolean isShifting = GameSettings.isKeyDown(keyBindSneak);
            final Item item = event.getItemStack().getItem();
            final Block block = Block.getBlockFromItem(item);
            
            if (item instanceof ItemEnchantedBook && enchantmentDescription) {
                
                final Set<Enchantment> enchants = EnchantmentHelper.getEnchantments(event.getItemStack()).keySet();
                
                if (!enchants.isEmpty()) {
                    
                    final Enchantment enchant = enchants.iterator().next();
                    
                    if (enchant != null)
                        if (isShifting) {
                            
                            final String description = I18n.format("description." + enchant.getName());
                            
                            if (description.startsWith("description."))
                                Utilities.wrapStringToList(I18n.format("tooltip.wawla.missingench", Utilities.getModName(enchant), description), 45, false, event.getToolTip());
                                
                            else {
                                
                                Utilities.wrapStringToList(description, 45, false, event.getToolTip());
                                
                                if (enchantmentMod)
                                    event.getToolTip().add(I18n.format("tooltip.wawla.addedby", Utilities.getModName(enchant)));
                            }
                        }
                        
                        else
                            Utilities.wrapStringToList(I18n.format("tooltip.wawla.shiftEnch", keyBindSneak.getDisplayName()), 45, false, event.getToolTip());
                }
            }
            
            if (block != null && showEnchantmentPower)
                try {
                    
                    final float enchPower = block.getEnchantPowerBonus(event.getEntityPlayer().worldObj, BlockPos.ORIGIN);
                    
                    if (enchPower > 0)
                        event.getToolTip().add(I18n.format("tooltip.wawla.enchPower") + ": " + enchPower);
                }
                
                catch (final IllegalArgumentException exception) {
                
                }
        }
    }
    
    public static void handleConfigs (Configuration config) {
        
        showEnchantmentPower = config.getBoolean("enchantmentPower", CATAGORY, true, "When enabled, blocks that contribute to the total bookshelves at an enchantment table will be shown.");
        enchantmentDescription = config.getBoolean("enchantmentDescription", CATAGORY, true, "When enabled, enchantment books can display descriptions about what they do.");
        enchantmentMod = config.getBoolean("enchantmentOwner", CATAGORY, true, "When enabled, shows the name of the mod that added the enchantment.");
    }
}