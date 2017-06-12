package net.darkhax.wawla.plugins.generic;

import java.util.ArrayList;
import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.darkhax.wawla.config.Configurable;
import net.darkhax.wawla.plugins.InfoProvider;
import net.darkhax.wawla.plugins.ProviderType;
import net.darkhax.wawla.plugins.WawlaFeature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@WawlaFeature(description = "Shows descriptions of enchantments on enchantment books", name = "enchdesc", type = ProviderType.ITEM)
public class PluginEnchantmentDescription extends InfoProvider {

    @Configurable(category = "enchdesc", description = "Should the mod which added the enchantment be shown?")
    public static boolean showOwner = true;

    @Override
    @SideOnly(Side.CLIENT)
    public void addItemInfo (List<String> info, ItemStack stack, ITooltipFlag flag, EntityPlayer entityPlayer) {

        final KeyBinding keyBindSneak = Minecraft.getMinecraft().gameSettings.keyBindSneak;

        if (stack.getItem() instanceof ItemEnchantedBook) {
            if (GameSettings.isKeyDown(keyBindSneak)) {

                final ItemEnchantedBook item = (ItemEnchantedBook) stack.getItem();
                final List<Enchantment> enchants = this.getEnchantments(item, stack);

                for (final Enchantment enchant : enchants) {

                    info.add(I18n.format("tooltip.wawla.enchdesc.name") + ": " + I18n.format(enchant.getName()));
                    info.add(this.getDescription(enchant));

                    if (showOwner) {
                        info.add(I18n.format("tooltip.wawla.enchdesc.addedby") + ": " + ChatFormatting.BLUE + getModName(enchant));
                    }
                }
            }
            else {
                info.add(I18n.format("tooltip.wawla.enchdesc.activate", ChatFormatting.LIGHT_PURPLE, keyBindSneak.getDisplayName(), ChatFormatting.GRAY));
            }
        }
    }

    @Override
    public boolean canEnable () {

        return !Loader.isModLoaded("enchdesc") && FMLCommonHandler.instance().getSide().equals(Side.CLIENT);
    }

    /**
     * Gets the description of the enchantment. Or the missing text, if no description exists.
     *
     * @param enchantment The enchantment to get a description for.
     * @return The enchantment description.
     */
    @SideOnly(Side.CLIENT)
    private String getDescription (Enchantment enchantment) {

        final String key = getTranslationKey(enchantment);
        String description = I18n.format(key);

        if (description.startsWith("enchantment.")) {
            description = I18n.format("tooltip.wawla.enchdesc.missing", getModName(enchantment), key);
        }

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
    @SideOnly(Side.CLIENT)
    private List<Enchantment> getEnchantments (ItemEnchantedBook book, ItemStack stack) {

        final NBTTagList enchTags = ItemEnchantedBook.getEnchantments(stack);
        final List<Enchantment> enchantments = new ArrayList<>();

        if (enchTags != null) {
            for (int index = 0; index < enchTags.tagCount(); ++index) {

                final int id = enchTags.getCompoundTagAt(index).getShort("id");
                final Enchantment enchant = Enchantment.getEnchantmentByID(id);

                if (enchant != null) {
                    enchantments.add(enchant);
                }
            }
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
    @SideOnly(Side.CLIENT)
    public static String getModName (IForgeRegistryEntry.Impl<?> registerable) {

        final String modID = registerable.getRegistryName().getResourceDomain();
        final ModContainer mod = Loader.instance().getIndexedModList().get(modID);
        return mod != null ? mod.getName() : modID;
    }

    @SideOnly(Side.CLIENT)
    public static String getTranslationKey (Enchantment enchant) {

        return String.format("enchantment.%s.%s.desc", enchant.getRegistryName().getResourceDomain(), enchant.getRegistryName().getResourcePath());
    }
}
