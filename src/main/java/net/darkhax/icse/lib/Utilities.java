package net.darkhax.icse.lib;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.text.WordUtils;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry.EntityRegistration;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

public class Utilities {
    
    /**
     * This method will take a string and break it down into multiple lines based on a provided
     * line length. The separate strings are then added to the list provided. This method is
     * useful for adding a long description to an item tool tip and having it wrap. This method
     * is similar to wrap in Apache WordUtils however it uses a List making it easier to use
     * when working with Minecraft.
     *
     * @param string: The string being split into multiple lines. It's recommended to use
     *        StatCollector.translateToLocal() for this so multiple languages will be
     *        supported.
     * @param lnLength: The ideal size for each line of text.
     * @param wrapLongWords: If true the ideal size will be exact, potentially splitting words
     *        on the end of each line.
     * @param list: A list to add each line of text to. An good example of such list would be
     *        the list of tooltips on an item.
     * @return List: The same List instance provided however the string provided will be
     *         wrapped to the ideal line length and then added.
     */
    public static List<String> wrapStringToList (String string, int lnLength, boolean wrapLongWords, List<String> list) {
        
        final String lines[] = WordUtils.wrap(string, lnLength, null, wrapLongWords).split(SystemUtils.LINE_SEPARATOR);
        list.addAll(Arrays.asList(lines));
        return list;
    }
    
    public static String getModName (IForgeRegistryEntry.Impl<?> registerable) {
        
        final String modID = registerable.getRegistryName().getResourceDomain();
        final ModContainer mod = Loader.instance().getIndexedModList().get(modID);
        return mod != null ? mod.getName() : modID.equalsIgnoreCase("minecraft") ? "Minecraft" : "Unknown";
    }
    
    public static String getModName (Entity entity) {
        
        if (entity == null)
            return "Unknown";
        
        final EntityRegistration reg = EntityRegistry.instance().lookupModSpawn(entity.getClass(), false);
        
        if (reg != null) {
            
            final ModContainer mod = reg.getContainer();
            
            if (mod != null)
                return mod.getName();
            
            return "Unknown";
        }
        
        return "Minecraft";
    }
}