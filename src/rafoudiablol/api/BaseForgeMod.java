package rafoudiablol.api;

import java.lang.annotation.Inherited;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
<code>
	@EventHandler @Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(proxy);
	}
	
	@EventHandler @Override
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
	
	protected final void preInit(FMLPreInitializationEvent event, BaseForgeProxy proxy)
	{
		// Entities, Event handlers
		
		if(proxy != null)
		{
			proxy.preInit(event);
			if(doRegisterEntity) proxy.registerEntity();
			if(doRegisterEvent) proxy.registerEvent();
		}
	}
	
	protected final void load(BaseForgeProxy proxy)
	{
		// Names, Renderers
		
		if(proxy != null)
		{
			if(doRegisterItem) proxy.registerName();
			if(doRegisterRender) proxy.registerRender();
		}
	}
	
	public final Mod getModAnnotation()
	{
		return (Mod)this.getClass().getAnnotation(Mod.class);
	}
	
	// You can override these methods with the annotation @EventHandler
	public void preInit(FMLPreInitializationEvent event) {}
	public void load(FMLInitializationEvent event) {}
	public void postInit(FMLPostInitializationEvent event) {}
}
