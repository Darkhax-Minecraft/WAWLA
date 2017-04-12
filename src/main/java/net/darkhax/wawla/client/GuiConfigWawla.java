package net.darkhax.wawla.client;

import java.util.ArrayList;
import java.util.List;

import net.darkhax.wawla.Wawla;
import net.darkhax.wawla.lib.Constants;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiConfigWawla extends GuiConfig {

    private static final Configuration cfg = Wawla.config.getConfig();

    public GuiConfigWawla(GuiScreen parent) {
        
        super(parent, generateConfigList(), Constants.MODID, false, false, GuiConfig.getAbridgedConfigPath(cfg.toString()));
    }

    private static List<IConfigElement> generateConfigList() {
        
        final ArrayList<IConfigElement> elements = new ArrayList<>();
        
        for (final String name : cfg.getCategoryNames()) {
            
            elements.add(new ConfigElement(cfg.getCategory(name)));
        }
        
        return elements;
    }
}