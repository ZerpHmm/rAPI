package rafoudiablol.api;

import java.io.File;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Logger;

import rafoudiablol.api.network.IPacketManager;
import rafoudiablol.api.network.PacketPipeline;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
	pre, init & post should be override with the @EventHandler annotation

<code>
	@EventHandler @Override
	public void pre(FMLPreInitializationEvent event) {
		super.pre(event);
	}
	
	@EventHandler @Override
	public void post(FMLPostInitializationEvent event) {
		super.post(event);
	}
	
	@EventHandler @Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}
</code>
**/

public abstract class BaseForgeMod
{
	protected boolean shouldRegisterItems = true;
	protected boolean shouldRegisterBlocks = true;
	protected boolean shouldRegisterEntities = true;
	protected boolean shouldRegisterRenderers = true;
	protected boolean shouldRegisterRecipes = true;
	protected boolean shouldRegisterKeyBindings = true;
	private final boolean isProxyFMLEventHandler;
	private final boolean isProxyForgeEventHandler;
	protected final BaseForgeProxy proxyObj;
	private final PacketPipeline packetPipeline = new PacketPipeline(this);
	private Configuration configuration = null;
	private Logger loggerObj = null;
	
	public BaseForgeMod(BaseForgeProxy proxy)
	{
		proxyObj = proxy;
		isProxyFMLEventHandler = (proxyObj instanceof IFMLEventHandler);
		isProxyForgeEventHandler = (proxyObj instanceof IForgeEventHandler);
	}
	
	public BaseForgeMod()
	{
		this(null);
	}
	
	public final Mod getModAnnotation()
	{
		return this.getClass().getAnnotation(Mod.class);
	}
	
	public final Logger getLogger()
	{
		return loggerObj;
	}
	
	public final Configuration getConfiguration()
	{
		return configuration;
	}

	public final IPacketManager getPacketManager()
	{
		return packetPipeline;
	}
    
	/**
	 *	Items, Blocks
	 * 	@param event
	**/
	protected void pre(FMLPreInitializationEvent event)
	{
		loggerObj = event.getModLog();
		
		configuration = new Configuration(event.getSuggestedConfigurationFile());
		configuration.load();

		packetPipeline.initalize();
		
		if(proxyObj != null)
		{
			getLogger().debug("Pre initialization...");
			proxyObj.pre(this);
			
			if(shouldRegisterBlocks)
			{
				getLogger().debug("Registering blocks...");
				proxyObj.registerBlocks(this);
			}
			if(shouldRegisterItems)
			{
				getLogger().debug("Registering items...");
				proxyObj.registerItems(this);
			}
			if(shouldRegisterEntities)
			{
				getLogger().debug("Registering entities...");
				proxyObj.registerEntities(this);
			}
		}
	}

	/**
	 *	Renderers
	 * 	@param event
	**/
	
	protected void init(FMLInitializationEvent event)
	{		
		if(proxyObj != null)
		{
			if(shouldRegisterRenderers)
			{
				getLogger().debug("Registering renderers...");
				proxyObj.registerRenderers(this);
			}
			if(shouldRegisterRecipes)
			{
				getLogger().debug("Registering recipes...");
				proxyObj.registerRecipes(this);
			}
		}
	}

	/**
	 *	Packets
	 * 	@param event
	**/
	
	protected void post(FMLPostInitializationEvent event)
	{
		packetPipeline.postInitialize();
		
		if(proxyObj != null)
		{
			if(shouldRegisterKeyBindings)
			{
				getLogger().debug("Registering key bindings...");
				proxyObj.registerKeyBindings(this);
			}
		}
		
		if(isProxyFMLEventHandler)
		{
			FMLCommonHandler.instance().bus().register(proxyObj);
		}
		if(isProxyForgeEventHandler)
		{
			MinecraftForge.EVENT_BUS.register(proxyObj);
		}
	}
}
