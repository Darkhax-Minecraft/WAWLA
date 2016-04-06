package net.darkhax.wawla.lib;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.text.WordUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Utilities {
    
    /**
     * A simple boolean which will be true if the mod is launched in a developer environment.
     * This field is set when the mod is launched, see the preInit of the Wawla mod for more
     * info.
     */
    public static boolean isDevMode = false;
    
    /**
     * A simple method to make an ItemStack safe to work with in regards of nbt. If the stack
     * does not have a tag compound one will be added. If it already has one then nothing will
     * happen. This is useful when working with ItemStacks that may not have tag compounds yet.
     * 
     * @param stack  An instance of an ItemStack to be prepared for nbt work.
     * @return ItemStack The same instance of ItemStack provided, however with a tag compound
     *         added if one did not already exist.
     */
    public static ItemStack prepareStackCompound (ItemStack stack) {
        
        if (!stack.hasTagCompound())
            stack.setTagCompound(new NBTTagCompound());
            
        return stack;
    }
    
    /**
     * This method will take a string and break it down into multiple lines based on a provided
     * line length. The separate strings are then added to the list provided. This method is
     * useful for adding a long description to an item tool tip and having it wrap. This method
     * is similar to wrap in Apache WordUtils however it uses a List making it easier to use
     * when working with Minecraft.
     * 
     * @param string  The string being split into multiple lines. It's recommended to use
     *            StatCollector.translateToLocal() for this so multiple languages will be
     *            supported.
     * @param lnLength  The ideal size for each line of text.
     * @param wrapLongWords  If true the ideal size will be exact, potentially splitting words
     *            on the end of each line.
     * @param list  A list to add each line of text to. An good example of such list would be
     *            the list of tooltips on an item.
     * @return List The same List instance provided however the string provided will be
     *         wrapped to the ideal line length and then added.
     */
    public static List wrapStringToList (String string, int lnLength, boolean wrapLongWords, List list) {
        
        String strings[] = WordUtils.wrap(string, lnLength, null, wrapLongWords).split(SystemUtils.LINE_SEPARATOR);
        list.addAll(Arrays.asList(strings));
        return list;
    }
    
    /**
     * This method can be used to round a double to a certain amount of places.
     * 
     * @param value  The double being round.
     * @param places  The amount of places to round the double to.
     * @return double The double entered however being rounded to the amount of places
     *         specified.
     */
    public static double round (double value, int places) {
        
        if (value >= 0 && places > 0) {
            
            BigDecimal bd = new BigDecimal(value);
            bd = bd.setScale(places, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }
        
        return value;
    }
    
    /**
     * Generates an array of strings containing elements from an enum class.
     * 
     * @param enumClass  The enum class.
     * @return String[] An array of strings that represent the names of the elements in an
     *         enum.
     */
    public static String[] generateElementArray (Class enumClass) {
        
        if (enumClass != null) {
            
            Object[] constants = enumClass.getEnumConstants();
            String[] elements = new String[constants.length];
            
            for (int i = 0; i < constants.length; i++)
                elements[i] = constants[i].toString();
                
            return elements;
        }
        
        return null;
    }
    
    /**
     * Retrieves an inventory of items from an NBTTagCompound, useful for working with
     * furnaces.
     * 
     * @param tag  The NBTTagCompound that contains an "Items" compound.
     * @param invSize  The size of the new inventory.
     * @return ItemStack[] An array of all the items, based on the invSize.
     */
    public static ItemStack[] getInventoryStacks (NBTTagCompound tag, int invSize) {
        
        ItemStack[] inventory = null;
        
        if (tag.hasKey("Items")) {
            
            NBTTagList list = tag.getTagList("Items", 10);
            inventory = new ItemStack[invSize];
            
            for (int i = 0; i < list.tagCount(); i++) {
                
                if (!(i > list.tagCount())) {
                    
                    NBTTagCompound currentTag = list.getCompoundTagAt(i);
                    inventory[(int) currentTag.getByte("Slot")] = ItemStack.loadItemStackFromNBT(currentTag);
                }
            }
        }
        
        return inventory;
    }
    
    /**
     * A way to check if two classes are the same. Includes a null check.
     * 
     * @param class1  The first class.
     * @param class2  The second class.
     * @return boolean Are the classes the same?
     */
    public static boolean compareByClass (Class class1, Class class2) {
        
        return (class1 != null && class2 != null) ? class1.getName().equalsIgnoreCase(class2.getName()) : false;
    }
    
    /**
     * An alternative to compareByClass that uses a TileEntity and a class.
     * 
     * @param entity  The tile entity you wish to compare.
     * @param teClass  The class of another tile entity.
     * @return boolean True if they are the same.
     */
    public static boolean compareTileEntityByClass (TileEntity entity, Class teClass) {
        
        return compareByClass(entity.getClass(), teClass);
    }
    
    /**
     * Gets the progression percentage. Uses the current stage and the total maximum stage to
     * make a percentage.
     * 
     * @param curStage  The stage you are current at.
     * @param maxStage  The maximum possible stage for the event.
     * @return float The float as a percentage. This is not rounded.
     */
    public static float getProgression (float curStage, float maxStage) {
        
        return (curStage / maxStage) * 100;
    }
    
    /**
     * Converts the first char in a string to its upper case form.
     * 
     * @param string  The string to use.
     * @return string The input string, with the first character being upper cased.
     */
    public static String upperCase (String string) {
        
        return Character.toString(string.charAt(0)).toUpperCase() + string.substring(1);
    }
    
    /**
     * Retrieves a Class which has the same name as the one specified.
     * 
     * @param className The class name to look for.
     * @return Class A class that maches the provided name. Can be null, if no class is found.
     */
    public static Class getClass (String className) {
        
        try {
            
            return Class.forName(className);
        }
        
        catch (ClassNotFoundException e) {
            
            Constants.LOG.warn("A class could not be found! This will cause issues, please report to Darkhax!");
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Checks if a specific player can sleep. For this to be true, a player must not already be
     * in a bed, and the world time bust be greater than 12541, but less than 23458.
     * 
     * @param player The player to check the sleepability of.
     * @return boolean True if the player can sleep, false if they can not.
     */
    public static boolean canPlayerSleep (EntityPlayer player) {
        
        return (!player.isPlayerSleeping() && player.isEntityAlive() && player.worldObj.getWorldTime() > 12541 && player.worldObj.getWorldTime() < 23458);
    }
    
    /**
     * Provides a way to access an NBTTagCompound that is very deep within another
     * NBTTagCompound. This will allow you to use an array of strings which represent the
     * different steps to get to the deep NBTTagCompound.
     * 
     * @param tag An NBTTagCompound to search through.
     * @param tags An array containing the various steps to get to the desired deep
     *            NBTTagCompound.
     * @return NBTTagCompound This method will return the deepest possible NBTTagCompound. In
     *         some cases, this may be the tag you provide, or only a few steps deep, rather
     *         than all of the way.
     */
    public static NBTTagCompound getDeepTagCompound (NBTTagCompound tag, String[] tags) {
        
        NBTTagCompound deepTag = tag;
        
        if (tag != null)
            for (String tagName : tags)
                if (deepTag.hasKey(tagName))
                    deepTag = deepTag.getCompoundTag(tagName);
                    
        return deepTag;
    }
    
    /**
     * An accessible field which can be used to provide the client-side value of the current
     * block damage. Initialized through the client proxy during preInit.
     */
    public static Field currentBlockDamage;
    
    /**
     * A client sided method used to retrieve the progression of the block currently being
     * mined by the player. This method is client side only, and refers to only the one
     * instance of the player. Do not try to use this method to get data for multiple players,
     * or for server sided things.
     * 
     * @return float A float value representing how much time is left for the block being
     *         broken to break. 0 = no damage has been done. 1 = the block is broken.
     */
    @SideOnly(Side.CLIENT)
    public static float getBlockDamage () {
        
        if (currentBlockDamage == null)
            return 0;
            
        try {
            
            return currentBlockDamage.getFloat(Minecraft.getMinecraft().playerController);
        }
        
        catch (IllegalArgumentException e) {
        
        }
        
        catch (IllegalAccessException e) {
        
        }
        
        return 0;
    }
}
