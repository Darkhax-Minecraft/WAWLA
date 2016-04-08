package net.darkhax.wawla.engine;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.darkhax.icse.lib.DataAccess;
import net.darkhax.icse.plugins.InfoPlugin;
import net.darkhax.wawla.Wawla;
import net.darkhax.wawla.lib.InfoAccess;
import net.darkhax.wawla.plugins.InfoProvider;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ICSEEngine extends InfoPlugin implements InfoEngine {
    
    @Override
    public String getName () {
        
        return ChatFormatting.AQUA + "ICSE" + ChatFormatting.RESET;
    }
    
    @Override
    public DataAccess overrideEntity (DataAccess data) {
        
        InfoAccess info = new InfoAccess(data.world, data.player, data.entity, data.tag);
        
        if (info.isValidBlock())
            for (InfoProvider provider : Wawla.entityProviders)
                if (provider.requireEntityOverride(info))
                    info = provider.overrideTile(info);
                    
        return new DataAccess(info.world, info.player, info.entity, info.tag);
    }
    
    @Override
    public DataAccess overrideTile (DataAccess data) {
        
        InfoAccess info = new InfoAccess(data.world, data.player, data.state, data.pos, data.side, data.tag);
        
        if (info.isValidBlock())
            for (InfoProvider provider : Wawla.tileProviders)
                if (provider.requireTileOverride(info))
                    info = provider.overrideTile(info);
                    
        return new DataAccess(info.world, info.player, info.state, info.pos, info.side, info.tag);
    }
    
    @Override
    public void addEntityInfo (List<String> info, DataAccess data) {
        
        InfoAccess infoAccess = new InfoAccess(data.world, data.player, data.entity, data.tag);
        
        for (InfoProvider provider : Wawla.entityProviders)
            provider.addEntityInfo(info, infoAccess);
    }
    
    @Override
    public void addTileInfo (List<String> info, DataAccess data) {
        
        InfoAccess infoAccess = new InfoAccess(data.world, data.player, data.state, data.pos, data.side, data.tag);
        
        for (InfoProvider provider : Wawla.tileProviders)
            provider.addTileInfo(info, infoAccess);
    }
    
    @Override
    public void writeEntityNBT (World world, Entity entity, NBTTagCompound tag) {
        
        for (InfoProvider provider : Wawla.entityProviders)
            if (provider.requireEntitySync(world, entity))
                provider.writeEntityNBT(world, entity, tag);
    }
    
    @Override
    public void writeTileNBT (World world, TileEntity entity, NBTTagCompound tag) {
        
        for (InfoProvider provider : Wawla.tileProviders)
            if (provider.requireTileSync(world, entity))
                provider.writeTileNBT(world, entity, tag);
    }
    
    @Override
    public boolean requireEntityOverride (DataAccess data) {
        
        return false;
    }
    
    @Override
    public boolean requireTileOverride (DataAccess data) {
        
        return true;
    }
    
    @Override
    public boolean requireEntitySync (World world, Entity entity) {
        
        for (InfoProvider plugin : Wawla.entityProviders)
            if (plugin.requireEntitySync(world, entity))
                return true;
                
        return false;
    }
    
    @Override
    public boolean requireTileSync (World world, TileEntity tile) {
        
        for (InfoProvider plugin : Wawla.entityProviders)
            if (plugin.requireTileSync(world, tile))
                return true;
                
        return false;
    }
}
