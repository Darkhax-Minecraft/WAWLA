package net.darkhax.wawla.client;

import net.darkhax.wawla.Wawla;
import net.darkhax.wawla.common.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientProxy extends CommonProxy {
    
    private static final String PREFIX = "[Wawla] ";
    
    public void preInit () {
        
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
    public void onOverlayRendered (RenderGameOverlayEvent.Text event) {
        
        Minecraft mc = Minecraft.getMinecraft();
        
        // Shows the current debug engine in the debug menu.
        if (mc.gameSettings.showDebugInfo)
            event.getLeft().add(PREFIX + "Info Engine: " + Wawla.engine.getName());
    }
    
}
