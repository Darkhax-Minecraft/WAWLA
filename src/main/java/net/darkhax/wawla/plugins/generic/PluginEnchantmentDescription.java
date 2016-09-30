package net.darkhax.wawla.plugins.generic;

import java.util.ArrayList;
import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.darkhax.wawla.plugins.InfoProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

public class PluginEnchantmentDescription extends InfoProvider {
    
    private static final KeyBinding keyBindSneak = Minecraft.getMinecraft().gameSettings.keyBindSneak;
    private static boolean enabled = true;
    private static boolean showOwner = true;
    
    @Override
    public void addItemInfo (List<String> info, ItemStack stack, boolean advanced, EntityPlayer entityPlayer) {
        
        if (enabled && stack.getItem() instanceof ItemEnchantedBook)
            if (GameSettings.isKeyDown(keyBindSneak)) {
                
                final ItemEnchantedBook item = (ItemEnchantedBook) stack.getItem();
                final List<Enchantment> enchants = this.getEnchantments(item, stack);
                
                for (final Enchantment enchant : enchants) {
                    
                    info.add(I18n.format("tooltip.wawla.enchdesc.name") + ": " + I18n.format(enchant.getName()));
                    info.add(this.getDescription(enchant));
                    
                    if (showOwner)
                        info.add(I18n.format("tooltip.wawla.enchdesc.addedby") + ": " + ChatFormatting.BLUE + getModName(enchant));
                }
            }
            
            else
                info.add(I18n.format("tooltip.wawla.enchdesc.activate", ChatFormatting.LIGHT_PURPLE, keyBindSneak.getDisplayName(), ChatFormatting.GRAY));
    }
    
    @Override
    public void syncConfig (Configuration config) {
        
        enabled = config.getBoolean("EnchantDescriptions", "generic_items", true, "Should the tooltip for enchanted books give descriptions of the enchantment?");
        showOwner = config.getBoolean("ShowEnchantmentSource", "generic_items", true, "Should the tooltip for enchanted books show the name of the mod that added them?");
    }
    
    /**
     * Gets the description of the enchantment. Or the missing text, if no description exists.
     * 
     * @param enchantment The enchantment to get a description for.
     * @return The enchantment description.
     */
    private String getDescription (Enchantment enchantment) {
        
        final String key = getTranslationKey(enchantment);
        String description = I18n.format(key);
        
        if (description.startsWith("enchantment."))
            description = I18n.format("tooltip.wawla.enchdesc.missing", getModName(enchantment), key);
        
        return description;
    }
    
    /**
     * Reads a List of enchantments from an ItemEnchantedBook stack.
     * 
     * @param book Instance of ItemEnchantedBook, as it uses non-static methods for some
     *        reason.
     * @param stack The stack to read the data from.
     * @return The list of enchantments stored on the stack.
     */
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
    
    /**
     * Gets the name of the mod that registered the passed object. Works for anthing which uses
     * Forge's registry.
     * 
     * @param registerable The object to get the mod name of.
     * @return The name of the mod which registered the object.
     */
    public static String getModName (IForgeRegistryEntry.Impl<?> registerable) {
        
        final String modID = registerable.getRegistryName().getResourceDomain();
        final ModContainer mod = Loader.instance().getIndexedModList().get(modID);
        return mod != null ? mod.getName() : modID;
    }
    
    public static String getTranslationKey (Enchantment enchant) {
        
        return String.format("enchantment.%s.%s.desc", enchant.getRegistryName().getResourceDomain(), enchant.getRegistryName().getResourcePath());
    }
}
