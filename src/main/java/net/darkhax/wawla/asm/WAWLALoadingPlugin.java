package net.darkhax.wawla.asm;

import java.util.Map;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

public class WAWLALoadingPlugin implements IFMLLoadingPlugin 
{
    @Override
    public String[] getASMTransformerClass()
    {
    	FMLLog.severe("Loading CoreMod");
        return new String[]{"net.darkhax.wawla.asm.WAWLAClassTransformer"};
    }

    @Override
    public String getModContainerClass()
    {
        return null;
    }

    @Override
    public String getSetupClass()
    {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data)
    {
    }

    @Override
    public String getAccessTransformerClass()
    {
        return null;
    }
}
