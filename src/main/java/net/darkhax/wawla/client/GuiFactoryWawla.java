package net.darkhax.wawla.client;

import java.util.Collections;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiFactoryWawla implements IModGuiFactory {
    
    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        
        return null;
    }

    @Override
    public void initialize(Minecraft minecraftInstance) {
        
    }

    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        
        return null;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        
        return Collections.emptySet();
    }

    @Override
    public boolean hasConfigGui () {
        
        return true;
    }

    @Override
    public GuiScreen createConfigGui (GuiScreen parentScreen) {
        
        return new GuiConfigWawla(parentScreen);
    }
}