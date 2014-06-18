package net.darkhax.wawla.modules;

import java.util.ArrayList;

import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.common.MinecraftForge;

public class ModuleEnchantmentBooks {

    public static ArrayList<Enchantment> blacklist = new ArrayList<Enchantment>();
    
    public ModuleEnchantmentBooks(Boolean enabled) {
        
        if (enabled)
            MinecraftForge.EVENT_BUS.register(this);
    }
    
}