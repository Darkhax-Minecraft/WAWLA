package net.darkhax.icse.common.packet;

import io.netty.buffer.ByteBuf;
import net.darkhax.icse.client.render.RenderingHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSendInfo implements IMessage {
    
    public NBTTagCompound tag;
    
    public PacketSendInfo() {
    
    }
    
    public PacketSendInfo(NBTTagCompound tag) {
        
        this.tag = tag;
    }
    
    @Override
    public void fromBytes (ByteBuf buf) {
        
        this.tag = ByteBufUtils.readTag(buf);
    }
    
    @Override
    public void toBytes (ByteBuf buf) {
        
        ByteBufUtils.writeTag(buf, tag);
    }
    
    public static class PacketHandler implements IMessageHandler<PacketSendInfo, IMessage> {
        
        @Override
        public IMessage onMessage (PacketSendInfo packet, MessageContext ctx) {
            
            RenderingHandler.dataTag = packet.tag;
            return null;
        }
    }
}