package rafoudiablol.api.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public final class PacketManager
{
	private final HashMap<Class<? extends PacketData>, Integer> registeredData = new HashMap();
	private Class<? extends PacketData>[] registeredID = new Class[255];
	public final String channel;
	
	public PacketManager(String channel) {
		this.channel = channel;
	}
	
	/**
	 * @param clazz
	 */
	
	public void registerData(Class<? extends PacketData> clazz)
	{
		try
		{
			final PacketData data = clazz.newInstance();
			final int id = data.getDataID();
			
			if(registeredID[id] != null)
			{
				throw new IllegalArgumentException(
					String.format("[rafoudiablol] PacketData %d from channel %s already exists", id, this.channel)
				);
			}
			
			registeredID[id] = clazz;
			registeredData.put(clazz, id);
		}
		catch(InstantiationException e)
		{
			// No trivial constructor
			throw new RuntimeException(e);
		}
		catch(IllegalAccessException e)
		{
			// Class innaccessible
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @param data
	 */
	
	public void sendToServer(PacketData data)
	{
		final Packet250CustomPayload packet250 = new Packet250CustomPayload();
		final ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		final DataOutputStream out = new DataOutputStream(byteOut);
		
		try
		{
			// THROWS NULLPOINTEREXCEPTION IF PACKET ID DON'T EXIST
			final int packetID = registeredData.get(data.getClass());
			out.writeByte(packetID); // write packetID
			
			data.writeData(out); // throws IOException
			packet250.channel = this.channel;
			packet250.data = byteOut.toByteArray();
			packet250.length = byteOut.size(); // byteOut/out are the same here
			
			PacketDispatcher.sendPacketToServer(packet250);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				out.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 	@param data
	 * 	@param player
	**/
	
	public void sendToPlayer(PacketData data, Player player)
	{
		final Packet250CustomPayload packet250 = new Packet250CustomPayload();
		final ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		final DataOutputStream out = new DataOutputStream(byteOut);
		
		try
		{
			final int packetID = registeredData.get(data.getClass());
			out.writeByte(packetID); // write packetID
			
			data.writeData(out); // throws IOException
			packet250.channel = this.channel;
			packet250.data = byteOut.toByteArray();
			packet250.length = byteOut.size();
			
			PacketDispatcher.sendPacketToPlayer(packet250, player);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				out.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 	Je ne sais pas s'il est possible d'envoyer un packet à tous les joueurs côté client
	 * 	Mais même si c'est possible, ce n'est pas une bonne solution
	 * 
	 * 	@param data
	 * 	@param playerSource
	**/
	
	public void sendToAll(PacketData data)
	{
		final Packet250CustomPayload packet250 = new Packet250CustomPayload();
		final ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		final DataOutputStream out = new DataOutputStream(byteOut);
		
		try
		{
			final int packetID = registeredData.get(data.getClass());
			out.writeByte(packetID); // write packetID
			
			data.writeData(out); // throws IOException
			packet250.channel = this.channel;
			packet250.data = byteOut.toByteArray();
			packet250.length = byteOut.size();
			
			PacketDispatcher.sendPacketToAllPlayers(packet250);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				out.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 	@param packet
	 * 	@param player
	 */
	
	public PacketData receive(Packet250CustomPayload packet, Player player)
	{
		PacketData handler = null;
		
		if(!packet.channel.equals(this.channel))
		{
			System.out.println(
				String.format("[rafoudiablol] Bad packet channel: %s (it must be %s)", packet.channel, this.channel)
			);
		}
		else
		{
			final DataInputStream in = 
				new DataInputStream(
					new ByteArrayInputStream(packet.data)
			);
			
			try
			{
				final int packetID = in.readByte(); // packetID
				final Class<? extends PacketData> clazz = registeredID[packetID];
				
				handler = clazz.newInstance();
				handler.readData(in);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			catch (InstantiationException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			finally
			{
				try {
					in.close();
				}
				catch(IOException e) {}
			}
		}
		
		return handler;
	}
}
