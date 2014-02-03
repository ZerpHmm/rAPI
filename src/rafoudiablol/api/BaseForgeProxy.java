package rafoudiablol.api;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;

public class BaseForgeProxy
{
	public void preInit(FMLPreInitializationEvent event) {}
	
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
		if(this instanceof IClientSide) {
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
