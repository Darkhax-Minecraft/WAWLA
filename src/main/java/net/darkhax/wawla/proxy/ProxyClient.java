package net.darkhax.wawla.proxy;

import net.darkhax.wawla.addons.generic.AddonGenericTooltips;
import net.darkhax.wawla.util.Utilities;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class ProxyClient extends ProxyCommon {
    
    @Override
    public void preInit () {
        
        MinecraftForge.EVENT_BUS.register(new AddonGenericTooltips());
        Utilities.currentBlockDamage = ReflectionHelper.findField(PlayerControllerMP.class, "g", "field_78770_f", "curBlockDamageMP");
        FMLInterModComms.sendMessage("llibrary", "update-checker", "https://github.com/Darkhax-Minecraft/WAWLA/master/versions.json");
    }
    
    @Override
    public void init () {
    
    }
    
    @Override
    public void postInit () {
    
    }
}
