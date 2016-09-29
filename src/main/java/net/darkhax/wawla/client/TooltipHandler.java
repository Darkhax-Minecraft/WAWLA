package net.darkhax.wawla.client;

import java.util.ArrayList;
import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TooltipHandler {
    
    private final static String CATAGORY = "item_tooltips";
    private final KeyBinding keyBindSneak = Minecraft.getMinecraft().gameSettings.keyBindSneak;
    
    private static boolean showEnchantmentPower = true;
    private static boolean enchantmentDescription = true;
    private static boolean isEnchDescLoaded = false;
    
    @SubscribeEvent
    public void onItemTooltip (ItemTooltipEvent event) {
        
        if (event.getEntityPlayer() != null && event.getEntityPlayer().worldObj != null && event.getItemStack() != null) {
            
            final Item item = event.getItemStack().getItem();
            final Block block = Block.getBlockFromItem(item);
            
            if (item instanceof ItemEnchantedBook && enchantmentDescription && GameSettings.isKeyDown(this.keyBindSneak) && !isEnchDescLoaded) {
                
                final List<String> tooltip = event.getToolTip();
                
                if (GameSettings.isKeyDown(this.keyBindSneak)) {
                    
                    final List<Enchantment> enchants = this.getEnchantments((ItemEnchantedBook) item, event.getItemStack());
                    
                    for (final Enchantment enchant : enchants) {
                        
                        tooltip.add(I18n.format("tooltip.enchdesc.name") + ": " + I18n.format(enchant.getName()));
                        tooltip.add(this.getDescription(enchant));
                        tooltip.add(I18n.format("tooltip.enchdesc.addedby") + ": " + ChatFormatting.BLUE + getModName(enchant));
                    }
                }
                
                else
                    tooltip.add(I18n.format("tooltip.enchdesc.activate", ChatFormatting.LIGHT_PURPLE, this.keyBindSneak.getDisplayName(), ChatFormatting.GRAY));
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
    
    private String getDescription (Enchantment enchantment) {
        
        final String key = getTranslationKey(enchantment);
        String description = I18n.format(key);
        
        if (description.startsWith("enchantment."))
            description = I18n.format("tooltip.enchdesc.missing", getModName(enchantment), key);
        
        return description;
    }
    
    private List<Enchantment> getEnchantments (ItemEnchantedBook book, ItemStack stack) {
        
        final NBTTagList enchTags = book.getEnchantments(stack);
        final List<Enchantment> enchantments = new ArrayList<>();
        
        if (enchTags != null)
            for (int index = 0; index < enchTags.tagCount(); ++index) {
                
                final int id = enchTags.getCompoundTagAt(index).getShort("id");
                final Enchantment enchant = Enchantment.getEnchantmentByID(id);
                
                if (enchant != null)
                    enchantments.add(enchant);
            }
        
        return enchantments;
    }
    
    public static String getModName (IForgeRegistryEntry.Impl<?> registerable) {
        
        final String modID = registerable.getRegistryName().getResourceDomain();
        final ModContainer mod = Loader.instance().getIndexedModList().get(modID);
        return mod != null ? mod.getName() : modID;
    }
    
    public static String getTranslationKey (Enchantment enchant) {
        
        return String.format("enchantment.%s.%s.desc", enchant.getRegistryName().getResourceDomain(), enchant.getRegistryName().getResourcePath());
    }
    
    public static void handleConfigs (Configuration config) {
        
        showEnchantmentPower = config.getBoolean("enchantmentPower", CATAGORY, true, "When enabled, blocks that contribute to the total bookshelves at an enchantment table will be shown.");
        enchantmentDescription = config.getBoolean("enchantmentDescription", CATAGORY, true, "When enabled, enchantment books can display descriptions about what they do.");
        isEnchDescLoaded = Loader.isModLoaded("enchdesc");
    }
}