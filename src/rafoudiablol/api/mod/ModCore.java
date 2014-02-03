package rafoudiablol.api.mod;

import rafoudiablol.api.BaseForgeMod;
import rafoudiablol.api.RApi;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(
	modid = RApi.MODID,
	name = RApi.NAME,
	version = RApi.VERSION
)
public class ModCore extends BaseForgeMod
{
	@SidedProxy(
		clientSide = "rafoudiablol.api.mod.client.ClientProxy",
		serverSide = "rafoudiablol.api.mod.ModProxy"
	) public static ModProxy proxy;
	
	@Instance(
		value = RApi.MODID
	) public static ModCore instance;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event, proxy);
		proxy.registerPackChangedEvent();
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event) {
		super.load(proxy);
	}
}
