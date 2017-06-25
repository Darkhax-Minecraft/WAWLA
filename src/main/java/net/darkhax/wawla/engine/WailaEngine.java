package net.darkhax.wawla.engine;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraftforge.fml.common.event.FMLInterModComms;

public class WailaEngine implements InfoEngine {

    private final boolean isHwyla;

    public WailaEngine (boolean isHwyla) {

        this.isHwyla = isHwyla;
        FMLInterModComms.sendMessage("waila", "register", "net.darkhax.wawla.engine.waila.EntityProvider.register");
        FMLInterModComms.sendMessage("waila", "register", "net.darkhax.wawla.engine.waila.TileProvider.register");
    }

    @Override
    public String getName () {

        return ChatFormatting.GOLD + (this.isHwyla ? "Hwyla" : "Waila");
    }
}
