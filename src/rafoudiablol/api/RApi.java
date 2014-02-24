package rafoudiablol.api;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class RApi
{
	public static final String NAME = "Rafoudiablol's API";
	public static final String MODID = "rapi";
	public static final String DEPENDENCY = "required-after:" + MODID;
	
	public static final int VERSION_MAJOR = 2;
	public static final int VERSION_MINOR = 0;
	public static final String VERSION = VERSION_MAJOR + "." + VERSION_MINOR;
	
	public static Side getSide()
	{
		return FMLCommonHandler.instance().getSide();
	}
	
	/**
	 *	send a chat message to the given player
	**/
	public static void addChatMessage(EntityPlayer player, String msg)
	{
		player.addChatMessage(new ChatComponentText(msg));
	}
}
