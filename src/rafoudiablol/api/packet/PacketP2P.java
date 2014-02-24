package rafoudiablol.api.packet;

import java.io.IOException;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;

import org.apache.logging.log4j.Logger;

import rafoudiablol.api.BaseForgeMod;
import rafoudiablol.api.RApiServer;
import rafoudiablol.api.network.IPacketManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class PacketP2P extends AbstractPacket
{
	/**
	 *	we must have a IPacketManager to re-send the packet from server to the client
	**/
	private final IPacketManager packetManagerObj;
	
	/**
	 *	mod logger
	 *	to log as default a message if the packet is refused
	**/
	private final Logger loggerObj;
	
	/**
	 *	player who send the PacketP2P
	 *	wrote on server side
	 *	read on client side
	**/
	@Serialize
	public String playerNameSource;
	
	/**
	 *	player who must receive the Packet2P2
	 *	wrote on client side
	 *	read on server side
	**/
	@Serialize
	public String playerNameDestination;
	
	/**
	 *	if the server won't send the PacketP2P to playerDestination
	 *	the packet is re send to the playerSource
	 *	if refuseReturnValue != 0 on the clientSide, the packet is refused
	**/
	@Serialize
	public int refuseReturnValue;
	
	/**
	 *	generic value if the packet is refused
	**/
	public static final int REFUSED = 1;
	public static final int PLAYER_NOT_FOUND = 2;
	
	/**
	 *	generic value if the packet is accepted to be send
	**/
	public static final int ADMITTED = 0;

	public PacketP2P(BaseForgeMod mod)
	{
		packetManagerObj = mod.getPacketManager();
		loggerObj = mod.getLogger();
	}
	
	@SideOnly(Side.CLIENT)
	public PacketP2P(BaseForgeMod mod, String destination)
	{
		this(mod);
		playerNameSource = "";
		playerNameDestination = destination;
	}
	
	@Override
	public final void handleServerSide(EntityPlayerMP playerSource) throws IOException
	{
		this.playerNameSource = playerSource.getDisplayName();
		final EntityPlayerMP playerDestination = RApiServer.getPlayerByName(playerNameDestination);
		
		if(playerDestination == null)
		{
			p2pResolve(playerSource, playerDestination, PLAYER_NOT_FOUND);
		}
		else
		{
			final int returnValue = p2pCheck(playerSource, playerDestination);
			p2pResolve(playerSource, playerDestination, returnValue);
		}
	}

	/**
	 *	resolve the request of playerSource, that is send finally this packet to playerDestination
	 *	or re-send the packet to playerSource if there is the error flag
	**/
	private final void p2pResolve(EntityPlayerMP src, EntityPlayerMP dest, int msg)
	{
		this.refuseReturnValue = msg;
		
		if(refuseReturnValue == ADMITTED)
		{
			packetManagerObj.sendTo(this, dest);
		}
		else
		{
			packetManagerObj.sendTo(this, src);
		}
	}

	
	/**
	 *	can be override
	 *	to check on server side if the player source can send a PacketP2P to the destinated player
	**/
	protected int p2pCheck(EntityPlayerMP source, EntityPlayerMP destination)
	{
		return ADMITTED;
	}
	
	@Override @SideOnly(Side.CLIENT)
	public final void handleClientSide(EntityPlayerSP destination) throws IOException
	{
		if(refuseReturnValue != ADMITTED)
		{
			handleRefuse(destination, refuseReturnValue);
		}
		else
		{
			handleRequest(destination);
		}
	}
	
	@SideOnly(Side.CLIENT)
	protected void handleRefuse(EntityPlayerSP player, int msg)
	{
		loggerObj.info("PacketP2P refused with the message " + msg + " (" + this + ")");
	}
	
	/**
	 *	the request is submitted!
	 *	ouf
	**/
	@SideOnly(Side.CLIENT)
	protected abstract void handleRequest(EntityPlayerSP player);
}
