package rafoudiablol.api.enchantment.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public abstract class CharmEvent
{
	public final EntityPlayer playerSource;
	public final World world;
	public final boolean isRemote;
	
	public CharmEvent(EntityPlayer player, World world)
	{
		this.playerSource = player;
		this.world = world;
		this.isRemote = world.isRemote;
	}
	
	public CharmEvent(EntityPlayer player)
	{
		this(player, player.worldObj);
	}
}
