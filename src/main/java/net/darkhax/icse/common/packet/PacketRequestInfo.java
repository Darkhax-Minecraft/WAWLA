package net.darkhax.icse.common.packet;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.darkhax.icse.ICSE;
import net.darkhax.icse.plugins.InfoPlugin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketRequestInfo implements IMessage {
    
    public BlockPos pos;
    public UUID entityID;
    
    public PacketRequestInfo() {
    
    }
    
    public PacketRequestInfo(BlockPos pos) {
        
        this.pos = pos;
    }
    
    public PacketRequestInfo(UUID entityID) {
        
        this.entityID = entityID;
    }
    
    @Override
    public void fromBytes (ByteBuf buf) {
        
        final boolean isTileRequest = buf.readBoolean();
        
        if (isTileRequest)
            pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
            
        else
            entityID = UUID.fromString(ByteBufUtils.readUTF8String(buf));
    }
    
    @Override
    public void toBytes (ByteBuf buf) {
        
        final boolean isTileRequest = pos != null;
        buf.writeBoolean(isTileRequest);
        
        if (isTileRequest) {
            
            buf.writeInt(pos.getX());
            buf.writeInt(pos.getY());
            buf.writeInt(pos.getZ());
        }
        
        else
            ByteBufUtils.writeUTF8String(buf, entityID.toString());
    }
    
    public static class PacketHandler implements IMessageHandler<PacketRequestInfo, IMessage> {
        
        @Override
        public IMessage onMessage (PacketRequestInfo packet, MessageContext ctx) {
            
            if (packet.pos != null)
                syncTile(ctx.getServerHandler().playerEntity, packet.pos);
                
            else if (packet.entityID != null)
                syncEntity(ctx.getServerHandler().playerEntity, packet.entityID);
                
            return null;
        }
        
        public void syncTile (EntityPlayerMP player, BlockPos pos) {
            
            final World world = player.getEntityWorld();
            final TileEntity tile = world.getTileEntity(pos);
            final NBTTagCompound tag = new NBTTagCompound();
            
            if (tile != null)
                for (InfoPlugin plugin : ICSE.plugins)
                    if (plugin.requireTileSync(world, tile))
                        plugin.writeTileNBT(world, tile, tag);
                        
            ICSE.network.sendTo(new PacketSendInfo(tag), player);
        }
        
        public void syncEntity (EntityPlayerMP player, UUID entityID) {
            
            final World world = player.getEntityWorld();
            
            Entity entity = null;
            for (Entity loadedEntity : world.loadedEntityList)
                if (loadedEntity.getUniqueID().equals(entityID))
                    entity = loadedEntity;
                    
            final NBTTagCompound tag = new NBTTagCompound();
            
            if (entity != null)
                for (InfoPlugin plugin : ICSE.plugins)
                    if (plugin.requireEntitySync(world, entity))
                        plugin.writeEntityNBT(world, entity, tag);
                        
            ICSE.network.sendTo(new PacketSendInfo(tag), player);
        }
    }
}