package net.darkhax.icse;

import java.util.ArrayList;
import java.util.List;

import net.darkhax.icse.common.CommonProxy;
import net.darkhax.icse.common.packet.PacketRequestInfo;
import net.darkhax.icse.common.packet.PacketSendInfo;
import net.darkhax.icse.lib.Constants;
import net.darkhax.icse.plugins.InfoPlugin;
import net.darkhax.icse.plugins.entity.PluginEntityItem;
import net.darkhax.icse.plugins.entity.PluginHealth;
import net.darkhax.icse.plugins.tile.PluginMonsterEggFix;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Constants.MODID, name = Constants.MOD_NAME, version = Constants.VERSION_NUMBER, acceptableRemoteVersions = "*", dependencies = "after:Waila")
public class ICSE {
    
    @SidedProxy(serverSide = Constants.SERVER, clientSide = Constants.CLIENT)
    public static CommonProxy proxy;
    
    @Mod.Instance(Constants.MODID)
    public static ICSE instance;
    
    public static SimpleNetworkWrapper network;
    public static List<InfoPlugin> plugins = new ArrayList<InfoPlugin>();
    
    @EventHandler
    public void preInit (FMLPreInitializationEvent event) {
        
        if (Loader.isModLoaded("Waila"))
            return;
            
        network = NetworkRegistry.INSTANCE.newSimpleChannel("ICSE");
        network.registerMessage(PacketRequestInfo.PacketHandler.class, PacketRequestInfo.class, 0, Side.SERVER);
        network.registerMessage(PacketSendInfo.PacketHandler.class, PacketSendInfo.class, 1, Side.CLIENT);
        
        proxy.preInit();
        plugins.add(new PluginEntityItem());
        plugins.add(new PluginMonsterEggFix());
        plugins.add(new PluginHealth());
    }
}