package rafoudiablol.api.mod.server;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import rafoudiablol.api.BaseForgeProxy;
import rafoudiablol.api.IServerProxy;
import rafoudiablol.api.mod.Proxy;

@SideOnly(Side.SERVER)
public class ServerProxy extends Proxy
	implements IServerProxy
{
	@Override
	public void registerPackChangedEvent() {
		// This event is only for client
	}
}
