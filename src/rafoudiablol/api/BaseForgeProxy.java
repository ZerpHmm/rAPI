package rafoudiablol.api;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BaseForgeProxy
{
	public void registerName() {}
	public void registerEntity() {}
	public void registerEvent() {}
	public void registerRender() {}
	
	/**
	 *	Par défaut, un proxy est serveur.
	 *	C'est un client uniquement s'il hérite de l'interface IClientProxy
	**/
	
	public final Side getSide()
	{
		if(this instanceof IClientProxy) {
			return Side.CLIENT;
		}
		else {
			return Side.SERVER;
		}
	}
	
	public final boolean serverSide() {
		return getSide() == Side.SERVER;
	}
	
	public final boolean clientSide() {
		return getSide() == Side.CLIENT;
	}
}
