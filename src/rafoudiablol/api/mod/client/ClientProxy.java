package rafoudiablol.api.mod.client;

import net.minecraft.client.Minecraft;
import rafoudiablol.api.IClientSide;
import rafoudiablol.api.event.ResourcePackChangedEvent;
import rafoudiablol.api.mod.ModProxy;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends ModProxy
	implements IClientSide
{
	@Override
	public void registerPackChangedEvent() {
		TickRegistry.registerTickHandler(new ResourcePackChangedEvent.Ticker(), Side.CLIENT);
	}
}
