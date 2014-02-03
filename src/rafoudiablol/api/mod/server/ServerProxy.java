package rafoudiablol.api.mod.server;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import rafoudiablol.api.BaseForgeProxy;
import rafoudiablol.api.IServerSide;
import rafoudiablol.api.mod.ModProxy;

@SideOnly(Side.SERVER)
public class ServerProxy extends ModProxy
	implements IServerSide
{
	@Override
	public void registerPackChangedEvent() {
		// This event is only for client
	}
}
