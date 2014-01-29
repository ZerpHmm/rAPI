package rafoudiablol.api.ench.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class ItemEvent
{
	public final ItemStack stack;
	public final EntityPlayer playerSource;
	public final World world;
	public final boolean isRemote;
	
	public ItemEvent(ItemStack stack, EntityPlayer player, World world)
	{
		this.playerSource = player;
		this.world = world;
		this.stack = stack;
		this.isRemote = world.isRemote;
	}
	
	public ItemEvent(ItemStack stack, EntityPlayer player)
	{
		this(stack, player, player.worldObj);
	}
}
