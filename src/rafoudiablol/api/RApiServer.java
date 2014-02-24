package rafoudiablol.api;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class RApiServer
{
	/**
	 *	WORKS ON CLIENT SIDE
	**/
	public static MinecraftServer getMinecraftS()
	{
		return MinecraftServer.getServer();
	}

	/**
	 *	return an instance of EntityPlayer of the given name
	 *	or null if the player is not found
	 *	WORKS ON CLIENT SIDE
	**/
	public static EntityPlayerMP getPlayerByName(String name)
	{
		return getMinecraftS().getConfigurationManager().getPlayerForUsername(name);
	}
}
