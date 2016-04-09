package net.darkhax.wawla.engine;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraftforge.fml.common.event.FMLInterModComms;

public class WailaEngine implements InfoEngine {
    
    public WailaEngine() {
        
        FMLInterModComms.sendMessage("Waila", "register", "net.darkhax.wawla.engine.waila.EntityProvider.register");
        FMLInterModComms.sendMessage("Waila", "register", "net.darkhax.wawla.engine.waila.TileProvider.register");
    }
    
    @Override
    public String getName () {
        
        return ChatFormatting.GOLD + "Waila";
    }
}
