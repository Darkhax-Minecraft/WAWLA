package net.darkhax.icse.lib;

import java.util.HashMap;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry.EntityRegistration;
import net.minecraftforge.fml.common.registry.GameData;

public class Utilities {
    
    private static HashMap<String, ModContainer> mods;
    
    public static String getModName (ItemStack stack) {
        
        @SuppressWarnings("deprecation")
        String itemID = GameData.getItemRegistry().getNameForObject(stack.getItem()).toString();
        String modID = itemID.substring(0, itemID.indexOf(':'));
        ModContainer mod = mods.get(modID);
        return (mod != null) ? mod.getName() : (modID.equalsIgnoreCase("minecraft") ? "Minecraft" : "Unknown");
    }
    
    public static String getModName (Entity entity) {
        
        if (entity == null)
            return "Unknown";
            
        EntityRegistration reg = EntityRegistry.instance().lookupModSpawn(entity.getClass(), false);
        
        if (reg != null) {
            
            ModContainer mod = reg.getContainer();
            
            if (mod != null)
                return mod.getName();
                
            return "Unknown";
        }
        
        return "Minecraft";
    }
    
    static {
        
        mods = new HashMap<String, ModContainer>();
        
        Loader loader = Loader.instance();
        for (String key : loader.getIndexedModList().keySet())
            mods.put(key, loader.getIndexedModList().get(key));
    }
}
