package net.darkhax.wawla.client;

import java.util.Set;

import net.darkhax.icse.lib.Utilities;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TooltipHandler {
    
    private final static String CATAGORY = "item_tooltips";
    
    private static boolean showModName = true;
    private static boolean showToolTier = true;
    private static boolean showEnchantmentPower = true;
    private static boolean enchantmentDescription = true;
    private static boolean enchantmentMod = true;
    private static boolean showModID = false;
    
    @SubscribeEvent
    public void onItemTooltip (ItemTooltipEvent event) {
        
        if (event.getEntityPlayer() != null && event.getEntityPlayer().worldObj != null && event.getItemStack() != null) {
            
            final KeyBinding keyBindSneak = Minecraft.getMinecraft().gameSettings.keyBindSneak;
            final boolean isShifting = GameSettings.isKeyDown(keyBindSneak);
            final Item item = event.getItemStack().getItem();
            Block.getBlockFromItem(item);
            
            if (item instanceof ItemEnchantedBook && enchantmentDescription) {
                
                final Set<Enchantment> enchants = EnchantmentHelper.getEnchantments(event.getItemStack()).keySet();
                
                if (!enchants.isEmpty()) {
                    
                    final Enchantment enchant = enchants.iterator().next();
                    
                    if (enchant != null)
                        if (isShifting) {
                            
                            final String description = I18n.translateToLocal("description." + enchant.getName());
                            
                            if (description.startsWith("description."))
                                Utilities.wrapStringToList(String.format(I18n.translateToLocal("tooltip.wawla.missingench"), Utilities.getModName(enchant), description), 45, false, event.getToolTip());
                                
                            else
                                Utilities.wrapStringToList(description, 45, false, event.getToolTip());
                        }
                        
                        else
                            Utilities.wrapStringToList(String.format(I18n.translateToLocal("tooltip.wawla.shiftEnch"), keyBindSneak.getDisplayName()), 45, false, event.getToolTip());
                }
            }
        }
    }
    
    public static void handleConfigs (Configuration config) {
        
        showModName = Loader.isModLoaded("Waila") ? false : config.getBoolean("modNames", CATAGORY, true, "When enabled, item tooltips will have the mod name displayed in blue. This will not enable if the player has Waila installed.");
        showToolTier = config.getBoolean("toolTier", CATAGORY, true, "When enabled, tools will have their tier value on the tooltip.");
        showEnchantmentPower = config.getBoolean("enchantmentPower", CATAGORY, true, "When enabled, blocks that contribute to the total bookshelves at an enchantment table will be shown.");
        showModID = config.getBoolean("modID", CATAGORY, false, "When enabled, items will show their full ID");
    }
}