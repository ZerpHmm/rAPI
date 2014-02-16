package rafoudiablol.api.client;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.WorldServer;
import rafoudiablol.api.BaseForgeMod;
import rafoudiablol.api.RApi;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MinecraftClient
{
	private static final Minecraft mc = Minecraft.getMinecraft();
	
	/**
	 *	Get Minecraft directory (almost always "%appdata%/.minecraft")
	**/
	public static File getMcDirectory()
	{
		return mc.mcDataDir;
	}
	
	/**
	 *	Add client chat message
	**/
	public static void chat(String msg) {
		mc.thePlayer.addChatMessage(new ChatComponentText(msg));
	}
	
	public static boolean singleplayer()
	{
		return mc.isSingleplayer();
	}
	
	public static boolean multiplayer()
	{
		return !mc.isSingleplayer();
	}
	
	public static File getDataModDirectory(BaseForgeMod mod)
	{
		File dir = new File(Minecraft.getMinecraft().mcDataDir + File.separator + RApi.MODS_DATA_DIR, mod.getModAnnotation().name());
		
		if(!dir.exists())
		{
			dir.mkdirs();
		}
		
		return dir;
	}
	
	/**
	 *	@return null if singleplayer
	**/
	public static WorldServer getWorldServer(int dimension)
	{
		if(multiplayer())
		{
			return null;
		}
		
		return mc.getIntegratedServer().worldServerForDimension(dimension);
	}
}
