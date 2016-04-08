package net.darkhax.icse.common.packet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class AbstractPacket<M extends AbstractPacket> implements IMessage, IMessageHandler<M, IMessage> {
    
    @Override
    public IMessage onMessage (M message, MessageContext ctx) {
        
        if (ctx.side.isClient())
            handleClientMessage(message);
            
        else
            handleServerMessage(message, ctx.getServerHandler().playerEntity);
            
        return null;
    }
    
    /**
     * Called when the message is received on the client side. Only use fields from the
     * provided message instance. Fields from the current class may not be reliable.
     * 
     * @param message An instance of the message that has been sent to the client.
     */
    @SideOnly(Side.CLIENT)
    public abstract void handleClientMessage (M message);
    
    /**
     * Called when the message is received on the server side. Only use fields from the
     * provided message instance. Fields from the current class may not be reliable.
     * 
     * @param message An instance of the message that has been sent to the server. This can be
     *            a dedicated server, or an integrated server.
     * @param player An instance of the player who sent the message to the server. Taken from
     *            ctx.getServerHandler().playerEntity.
     */
    public abstract void handleServerMessage (M message, EntityPlayer player);
}