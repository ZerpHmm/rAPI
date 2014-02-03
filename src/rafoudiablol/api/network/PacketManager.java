package rafoudiablol.api.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

/**
 *	Packet I/O
 *	La partie abstraite est pour l'implémentation des méthodes processPacket(CustomPacketDataType, EntityPlayer)
**/
public abstract class PacketManager
	implements IPacketHandler, IPacketDataSender, IPacketDataReceiver
{
	private final HashMap<Class<? extends PacketData>, Method> processHandleMethods = new HashMap();
	private final HashMap<Class<? extends PacketData>, Integer> registeredData = new HashMap();
	private final Class<? extends PacketData>[] registeredID = new Class[255];
	public final String channel;
	
	protected PacketManager(String channel) {
		this.channel = channel;
		initHandleMethods();
	}

	private void initHandleMethods()
	{
		Class<?> clazz = getClass();
		
		while(clazz != PacketManager.class)
		{
			System.out.println(clazz);
			
			for(Method method : clazz.getDeclaredMethods())
			{
				final int mod = method.getModifiers();
				
				if((Modifier.isPublic(mod) || Modifier.isProtected(mod)) && !Modifier.isStatic(mod) && !Modifier.isAbstract(mod)
					&& method.getName().equals("processHandle"))
				{
					Class<?>[] parameters = method.getParameterTypes();
					
					if(parameters.length == 2 &&
							PacketData.class.isAssignableFrom(parameters[0]) &&
							EntityPlayer.class.isAssignableFrom(parameters[1]))
					{
						processHandleMethods.put((Class<? extends PacketData>)parameters[0], method);
					}
				}
			}
			
			clazz = clazz.getSuperclass();
		}
	}
	
	/**
	 *	IPacketHandler implementation
	**/
	@Override
	public final void onPacketData(INetworkManager networkManager, Packet250CustomPayload packet, Player player)
	{
		this.receive(packet, player);
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
	@Override
	public void toServer(PacketData data)
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
	@Override
	public void toPlayer(PacketData data, EntityPlayer player)
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
			
			PacketDispatcher.sendPacketToPlayer(packet250, (Player)player);
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
	**/
	@Override
	public void toAll(PacketData data)
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
	@Override
	public final void receive(Packet250CustomPayload packet, Player player)
	{
		PacketData data = null;
		
		if(!packet.channel.equals(this.channel))
		{
			// wtf ?
			
			System.out.println(
				String.format("[rAPI] Bad packet channel: %s (it must be %s)", packet.channel, this.channel)
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
				
				data = clazz.newInstance();
				data.readData(in);
				
				this.processHandle(data, (EntityPlayer)player);
			}
			catch(BadHandleException e)
			{
				System.out.println(
					String.format("[rAPI] Bad PacketData process (channel '%s')", this.channel)
				);
				
				e.printStackTrace();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			catch (InstantiationException e) {
				e.printStackTrace();
			}
			catch (IllegalAccessException e) {
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
	}

	/**
	 *	Handling process
	**/
	private void processHandle(PacketData data, EntityPlayer player)
		throws BadHandleException
	{
		final Method m = processHandleMethods.get(data.getClass());

		if(m == null)
		{
			throw new BadHandleException(
				String.format("[rAPI] Cannot find %s.processHandle(%s)", this.getClass().getSimpleName(), data.getClass().getSimpleName())
			);
		}
		
		try
		{
			m.invoke(this, data, player);
		}
		catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		}
		catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
}
