package net.darkhax.wawla.handler;

import net.darkhax.wawla.modules.ModuleEnchantmentBooks;
import net.darkhax.wawla.modules.ModulePets;
import net.darkhax.wawla.util.Reference;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;

public class IMCHandler {

    /**
     * Basic method for breaking down an IMCMessage. Once broken down the information is passed along to
     * other helper methods.
     * 
     * @param message: An IMCMessage to read.
     */
    public static void readMeassage(IMCMessage message) {

        if (message.key.equalsIgnoreCase("wawla-addon") && message.isNBTMessage()) {

            NBTTagList ownerKeys = message.getNBTValue().getTagList("ownerTags", 8);
            for (int i = 0; i < ownerKeys.tagCount(); i++)
                registerOwnerTag(ownerKeys.getStringTagAt(i));

            int[] blacklist = message.getNBTValue().getIntArray("enchBlacklist");
            for (int i = 0; i < blacklist.length; i++)
                registerEnchantToBlacklist(blacklist[i], message.getSender());
        }

        if (message.key.equalsIgnoreCase("wawla-pet") && message.isStringMessage())
            registerOwnerTag(message.getStringValue());

        if (message.key.equalsIgnoreCase("wawla-echantment-blacklist") && message.isStringMessage())
            registerEnchantToBlacklist(Integer.parseInt(message.getStringValue()), message.getSender());
    }

    /**
     * Adds a nbtKey to the list of keys to look for when attempting to determine who owns a pet entity.
     * 
     * @param nbtKey: The new nbtKey to add.
     */
    public static void registerOwnerTag(String nbtKey) {

        ModulePets.nbtNames.add(nbtKey);
    }

    /**
     * Registers and enchantment with the blacklist. If an enchantment is on the blacklist its
     * enchantment book will not receive additional information on the tooltip.
     * 
     * @param id: ID of the enchantment being added. Can't wait to change this to strings in 1.8
     * @param modid: The id of the mod. Used in case something goes wrong.
     */
    public static void registerEnchantToBlacklist(int id, String modid) {

        Enchantment ench = Enchantment.enchantmentsList[id];

        if (ench != null)
            ModuleEnchantmentBooks.blacklist.add(ench);

        else
            Reference.LOG.info(modid + " has attempted to register an enchantment with the enchantment blacklist however " + id + " is not a valid enchantment id.");
    }

    /**
     * This method should never be called unless testing the API. The purpose of this method is to allow
     * for testing of all imc messages and to demonstrate how other mod authors can register their
     * information with this mod.
     */
    public static void exampleMessages() {

        // Single nbtKey to add to the pet key lookup list. Specifically adds "yourEntitiesOwnerKey" to
        // the list.
        FMLInterModComms.sendMessage("wawla", "wawla-pet", "yourEntitiesOwnerKey");

        // Single enchantment id to the enchantment book blacklist. Specifically blacklists the
        // Efficiency enchantment.
        FMLInterModComms.sendMessage("wawla", "wawla-enchantment-blacklist", String.valueOf(Enchantment.efficiency.effectId));

        // Using a NBTTagCompound to send many entries. Adds "tag1" and "tag2" to the pet key lookup
        // list. Adds 4, 3, 2, 1 to the list of blacklist enchantments.
        NBTTagCompound tag = new NBTTagCompound();
        NBTTagList list = new NBTTagList();
        list.appendTag(new NBTTagString("tag1"));
        list.appendTag(new NBTTagString("tag2"));
        tag.setTag("ownerTags", list);
        int[] enchIDs = { 4, 3, 2, 1 };
        tag.setIntArray("enchBlacklist", enchIDs);
        FMLInterModComms.sendMessage("wawla", "wawla-addon", tag);
    }
}
