package rafoudiablol.api.client;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MinecraftClient
{
	public static Minecraft mc() {
		return FMLClientHandler.instance().getClient();
	}

	public static void chat(String msg) {
		mc().thePlayer.addChatMessage(msg);
	}
}
