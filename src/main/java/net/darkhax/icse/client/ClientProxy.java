package net.darkhax.icse.client;

import net.darkhax.icse.client.render.RenderingHandler;
import net.darkhax.icse.common.CommonProxy;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {
    
    @Override
    public void preInit () {
        
        MinecraftForge.EVENT_BUS.register(new RenderingHandler());
    }
}
