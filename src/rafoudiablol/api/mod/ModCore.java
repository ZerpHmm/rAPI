package rafoudiablol.api.mod;

import rafoudiablol.api.BaseForgeMod;
import rafoudiablol.api.RApi;
import rafoudiablol.api.enchantment.EnchantmentAPI;
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
	private final EnchantmentAPI enchantmentCore = new EnchantmentAPI();
	
	@SidedProxy(
		clientSide = "rafoudiablol.api.mod.client.ClientProxy",
		serverSide = "rafoudiablol.api.mod.Proxy"
	) public static Proxy proxy;
	
	@Instance(
		value = RApi.MODID
	) public static ModCore instance;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(proxy);
		proxy.registerPackChangedEvent();
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event) {
		super.load(proxy);
	}
}
