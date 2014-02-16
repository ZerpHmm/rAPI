package rafoudiablol.api;

import java.io.File;

import org.apache.logging.log4j.Logger;

import rafoudiablol.api.network.IPacketManager;
import rafoudiablol.api.network.PacketPipeline;
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
	protected final BaseForgeProxy proxyObj;
	private final PacketPipeline packetPipeline = new PacketPipeline();
	private Logger loggerObj = null;
	private File modDirObj = null;
	private File confFileObj = null;
	
	public BaseForgeMod(BaseForgeProxy proxy)
	{
		proxyObj = proxy;
	}
	
	public BaseForgeMod()
	{
		this(null);
	}
	
	public final Mod getModAnnotation()
	{
		return (Mod)this.getClass().getAnnotation(Mod.class);
	}
	
	public final Logger getLogger()
	{
		return loggerObj;
	}
	
	public final File getDirectory()
	{
		return modDirObj;
	}
	
	public final File getConfFile()
	{
		return confFileObj;
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
		modDirObj = event.getModConfigurationDirectory();
		confFileObj = event.getSuggestedConfigurationFile();
		
		
		if(proxyObj != null)
		{
			if(shouldRegisterBlocks) proxyObj.registerBlocks();
			if(shouldRegisterItems) proxyObj.registerItems();
			if(shouldRegisterEntities) proxyObj.registerEntities();
		}
	}

	/**
	 *	Renderers
	 * 	@param event
	**/
	
	protected void init(FMLInitializationEvent event)
	{
		packetPipeline.initalize();
		
		if(proxyObj != null)
		{
			if(shouldRegisterRenderers) proxyObj.registerRenderers();
		}
	}

	/**
	 *	Packets
	 * 	@param event
	**/
	
	protected void post(FMLPostInitializationEvent event)
	{
		packetPipeline.postInitialize();
	}
}
