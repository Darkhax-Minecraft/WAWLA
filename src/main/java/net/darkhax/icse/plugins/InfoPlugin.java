package net.darkhax.icse.plugins;

import java.util.List;

import net.darkhax.icse.lib.DataAccess;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class InfoPlugin {

	public DataAccess overrideEntity(DataAccess data) {

		return data;
	}

	public DataAccess overrideTile(DataAccess data) {

		return data;
	}

	public boolean requireEntityOverride(DataAccess data) {

		return false;
	}

	public boolean requireTileOverride(DataAccess data) {

		return false;
	}

	public void addEntityInfo(List<String> info, DataAccess data) {

	}

	public void addTileInfo(List<String> info, DataAccess dats) {

	}

	public void writeEntityNBT(World world, Entity entity, NBTTagCompound tag) {

	}

	public void writeTileNBT(World world, TileEntity entity, NBTTagCompound tag) {

	}

	public boolean requireEntitySync(World world, Entity entity) {

		return false;
	}

	public boolean requireTileSync(World world, TileEntity tile) {

		return false;
	}
}
