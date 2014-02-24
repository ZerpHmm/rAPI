package rafoudiablol.api.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import rafoudiablol.api.network.PacketSerializer;

/**
 *	@see http://www.minecraftforge.net/wiki/Netty_Packet_Handling
**/
public abstract class AbstractPacket
{
	/**
	 *	ALL PACKETS MUST HAVE A DEFAULT CONSTRUCTOR
	**/
	public AbstractPacket()
	{
	}
	
    public void encodeInto(PacketBuffer buf)
    		throws IOException
	{
    	PacketSerializer.serializeObject(buf, this);
	}

    public void decodeInto(PacketBuffer buf)
    		throws IOException
	{
    	PacketSerializer.deserializeObject(buf, this);
	}

    /**
     *	handle packet on the client side
     */
    @SideOnly(Side.CLIENT)
    public abstract void handleClientSide(EntityPlayerSP player) throws IOException;

    /**
     *	handle packet on server side
    **/
    public abstract void handleServerSide(EntityPlayerMP player) throws IOException;
}