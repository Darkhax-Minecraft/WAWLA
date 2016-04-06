package net.darkhax.wawla.lib;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public interface InfoProvider {

	default public InfoAccess overrideEntity(InfoAccess data) {
		
		return data;
	}
	
	default public InfoAccess overrideTile(InfoAccess data) {
		
		return data;
	}
	
	default public boolean requireEntityOverride(InfoAccess data) {
		
		return false;
	}
	
	default public boolean requireTileOverride(InfoAccess data) {
		
		return false;
	}
	
	default public void addEntityInfo(List<String> info, InfoAccess data) {
		
	}
	
	default public void addTileInfo(List<String> info, InfoAccess data) {
		
	}
	
	default public void writeEntityNBT(World world, Entity entity, NBTTagCompound tag) {
		
	}
	
	default public void writeTileNBT(World world, TileEntity tile, NBTTagCompound tag) {
		
	}
	
	default public boolean requireEntitySync(World world, Entity entity) {
		
		return false;
	}
	
	default public boolean requireTileSync(World world, TileEntity tile) {
		
		return false;
	}
}
