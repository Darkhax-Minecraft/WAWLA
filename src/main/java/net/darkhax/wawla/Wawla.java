package net.darkhax.wawla;

import java.util.ArrayList;
import java.util.List;

import net.darkhax.icse.ICSE;
import net.darkhax.wawla.common.CommonProxy;
import net.darkhax.wawla.engine.ICSEEngine;
import net.darkhax.wawla.engine.InfoEngine;
import net.darkhax.wawla.lib.Constants;
import net.darkhax.wawla.lib.InfoProvider;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Constants.MODID, name = Constants.MOD_NAME, version = Constants.VERSION_NUMBER, acceptableRemoteVersions = "*", acceptedMinecraftVersions = "[1.9,1.9.2]", dependencies = "required-after:ICSE")
public class Wawla {

	@SidedProxy(serverSide = Constants.SERVER, clientSide = Constants.CLIENT)
	public static CommonProxy proxy;

	@Mod.Instance(Constants.MODID)
	public static Wawla instance;

	public static InfoEngine engine;
	public static final List<InfoProvider> tileProviders = new ArrayList<InfoProvider>();
	public static final List<InfoProvider> entityProviders = new ArrayList<InfoProvider>();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		if (Loader.isModLoaded("ICSE")) {

			engine = new ICSEEngine();
			ICSE.plugins.add((ICSEEngine) engine);
		}

		proxy.preInit();
	}
}