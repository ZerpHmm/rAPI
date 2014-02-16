package rafoudiablol.api;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import rafoudiablol.api.network.AbstractPacket;
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
		MinecraftForge.EVENT_BUS.register(this);
		super.pre(event);
	}
	
	@Override @EventHandler
	public void post(FMLPostInitializationEvent event)
	{
		super.post(event);
		getLogger().info("Rafoudiablol's API done.");
	}

	@Override @EventHandler
	protected void init(FMLInitializationEvent event)
	{
		super.init(event);
		
		getPacketManager().registerPacket(PacketTest.class);
	}
	
	@SubscribeEvent
	public void handleEvent(LivingAttackEvent event)
	{
		if(!event.entity.worldObj.isRemote)
		{
			getPacketManager().sendToAll(new PacketTest());
		}
	}
	
	static class PacketTest extends AbstractPacket
	{

		@Override
		public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
			// TODO Auto-generated method stub
			instance.getLogger().debug("encodeInto");
		}

		@Override
		public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
			// TODO Auto-generated method stub
			instance.getLogger().debug("decodeInto");
			
		}

		@Override
		public void handleClientSide(EntityPlayer player) {
			// TODO Auto-generated method stub
			instance.getLogger().debug("handleClientSide");
			
		}

		@Override
		public void handleServerSide(EntityPlayer player) {
			// TODO Auto-generated method stub
			instance.getLogger().debug("handleServerSide");
			
		}
	}
}
