package net.darkhax.wawla.proxy;

import net.minecraft.client.multiplayer.PlayerControllerMP;

import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.relauncher.ReflectionHelper;

import net.darkhax.wawla.addons.generic.AddonGenericTooltips;
import net.darkhax.wawla.util.Utilities;

public class ProxyClient extends ProxyCommon {
    
    @Override
    public void preInit () {
        
        MinecraftForge.EVENT_BUS.register(new AddonGenericTooltips());
        Utilities.currentBlockDamage = ReflectionHelper.findField(PlayerControllerMP.class, "g", "field_78770_f", "curBlockDamageMP");
    }
    
    @Override
    public void init () {
    
    }
    
    @Override
    public void postInit () {
    
    }
}
