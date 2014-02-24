package rafoudiablol.api;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import test.PacketTest;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Mod(
	modid = RApi.MODID,
	name = RApi.NAME,
	version = RApi.VERSION
)
public class ModCore extends BaseForgeMod
{
	@Instance(
		value = RApi.MODID
	)
	public static ModCore instance;

	@Override @EventHandler
	protected void pre(FMLPreInitializationEvent event)
	{
		super.pre(event);
		getPacketManager().registerPacket(PacketTest.class);
	}
	
	@Override @EventHandler
	public void post(FMLPostInitializationEvent event)
	{
		super.post(event);
		getLogger().info("Rafoudiablol's API done");
	}

	@Override @EventHandler
	protected void init(FMLInitializationEvent event)
	{
		super.init(event);
	}
}
