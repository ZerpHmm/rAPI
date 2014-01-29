package rafoudiablol.api.mod.client;

import net.minecraft.client.Minecraft;
import rafoudiablol.api.IClientProxy;
import rafoudiablol.api.event.ResourcePackChangedEvent;
import rafoudiablol.api.mod.Proxy;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends Proxy
	implements IClientProxy
{
	@Override
	public void registerPackChangedEvent() {
		TickRegistry.registerTickHandler(new ResourcePackChangedEvent.Ticker(), Side.CLIENT);
	}
}
