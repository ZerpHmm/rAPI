package rafoudiablol.api.event;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event;
import rafoudiablol.api.RApi;
import rafoudiablol.api.TickRender;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 *	Lorsque le ResourcePack change
**/

@SideOnly(Side.CLIENT)
public class ResourcePackChangedEvent extends Event
{
	public final String packName;
	private static String globalCurrentPackName = null;
	
	// ---------------------------------------------
	
	public static class Ticker extends TickRender
	{
		@Override
		public String getLabel() {
			return RApi.MODID;
		}
		
		@Override
		protected void renderTick(float renderPartialTicks)
		{
			final String currentName = Minecraft.getMinecraft()
				.getResourcePackRepository()
					.getResourcePackName();

			if(!currentName.equals(globalCurrentPackName))
			{
				if(globalCurrentPackName != null)
				{
					MinecraftForge.EVENT_BUS.post(new ResourcePackChangedEvent(currentName));
				}
				
				globalCurrentPackName = currentName;
			}
		}
	}
	
	// ---------------------------------------------
	
	public ResourcePackChangedEvent(String packName) {
		this.packName = packName;
	}
}
