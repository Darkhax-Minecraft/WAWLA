package net.darkhax.wawla.plugins;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.lib.WawlaConfiguration;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;

public class InfoProvider {
    
    public InfoAccess overrideEntity (InfoAccess data) {
        
        return data;
    }
    
    public InfoAccess overrideTile (InfoAccess data) {
        
        return data;
    }
    
    public boolean requireEntityOverride (InfoAccess data) {
        
        return false;
    }
    
    public boolean requireTileOverride (InfoAccess data) {
        
        return false;
    }
    
    public void addEntityInfo (List<String> info, InfoAccess data) {
        
    }
    
    public void addTileInfo (List<String> info, InfoAccess data) {
        
    }
    
    public void addItemInfo (List<String> info, ItemStack stack, boolean advanced, EntityPlayer entityPlayer) {
        
    }
    
    public void writeEntityNBT (World world, Entity entity, NBTTagCompound tag) {
        
    }
    
    public void writeTileNBT (World world, TileEntity tile, NBTTagCompound tag) {
        
    }
    
    public boolean requireEntitySync (World world, Entity entity) {
        
        return false;
    }
    
    public boolean requireTileSync (World world, TileEntity tile) {
        
        return false;
    }
    
    public void syncConfig (Configuration config) {
        
    }
    
    public static String getBooleanForDisplay (boolean bool) {
        
        if (bool)
            return I18n.format("tooltip.wawla." + (WawlaConfiguration.useSymbols ? "yes" : "true"));
        
        return I18n.format("tooltip.wawla." + (WawlaConfiguration.useSymbols ? "no" : "false"));
    }
    
    public static void writeStackToTag (ItemStack stack, String tagName, NBTTagCompound tag) {
        
        final NBTTagCompound itemTag = new NBTTagCompound();
        stack.writeToNBT(itemTag);
        tag.setTag(tagName, itemTag);
    }
    
    public static double round (double value, int places) {
        
        if (value >= 0 && places > 0) {
            
            BigDecimal bd = new BigDecimal(value);
            bd = bd.setScale(places, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }
        
        return value;
    }
}
