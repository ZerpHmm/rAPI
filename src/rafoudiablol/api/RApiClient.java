package rafoudiablol.api;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RApiClient
{
	private static final Minecraft mc = Minecraft.getMinecraft();
	
	public static File getMcDirectory() {
		return mc.mcDataDir;
	}
	
	public static void displayGuiScreen(GuiScreen screen) {
		Minecraft.getMinecraft().displayGuiScreen(screen);
	}

	public static void addChatMessage(String msg)
	{
		mc.thePlayer.addChatMessage(new ChatComponentText(msg));
	}
	
	public static String getPlayerName()
	{
		return mc.thePlayer.getDisplayName();
	}
}
