package net.darkhax.icse.plugins;

import java.util.List;

import net.darkhax.icse.lib.DataAccess;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public interface InfoPlugin {
    
    default public DataAccess overrideEntity (DataAccess data) {
        
        return data;
    }
    
    default public DataAccess overrideTile (DataAccess data) {
        
        return data;
    }
    
    default public boolean requireEntityOverride (DataAccess data) {
        
        return false;
    }
    
    default public boolean requireTileOverride (DataAccess data) {
        
        return false;
    }
    
    default public void addEntityInfo (List<String> info, DataAccess data) {
    
    }
    
    default public void addTileInfo (List<String> info, DataAccess dats) {
    
    }
    
    default public void writeEntityNBT (World world, Entity entity, NBTTagCompound tag) {
    
    }
    
    default public void writeTileNBT (World world, TileEntity entity, NBTTagCompound tag) {
    
    }
    
    default public boolean requireEntitySync (World world, Entity entity) {
        
        return false;
    }
    
    default public boolean requireTileSync (World world, TileEntity tile) {
        
        return false;
    }
}
