package rafoudiablol.api;

import java.lang.annotation.Inherited;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
<code>
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(proxy);
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event) {
		super.load(proxy);
	}
</code>
**/

public abstract class BaseForgeMod
{
	protected boolean doRegisterItem = true;
	protected boolean doRegisterEntity = true;
	protected boolean doRegisterEvent = true;
	protected boolean doRegisterRender = true;
	
	protected void preInit(BaseForgeProxy proxy)
	{
		// Entities, Event handlers
		
		if(proxy != null)
		{
			if(doRegisterEntity) proxy.registerEntity();
			if(doRegisterEvent) proxy.registerEvent();
		}
	}
	
	public void load(BaseForgeProxy proxy)
	{
		// Names, Renderers
		
		if(proxy != null)
		{
			if(doRegisterItem) proxy.registerName();
			if(doRegisterRender) proxy.registerRender();
		}
	}
	
	public void postInit(FMLPostInitializationEvent event) {}
}
