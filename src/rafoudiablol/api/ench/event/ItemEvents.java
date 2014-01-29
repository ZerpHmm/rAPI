package rafoudiablol.api.ench.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public final class ItemEvents
{
	public static class RightClick extends ItemEvent
	{
		public final boolean additionnalEvent;

		public RightClick(ItemStack stack, EntityPlayer player) {
			super(stack, player);
			this.additionnalEvent = false;
		}
		
		public RightClick(ItemStack stack, EntityPlayer player, boolean flag) {
			super(stack, player);
			this.additionnalEvent = flag;
		}
		
		public RightClick(ItemStack stack, EntityPlayer player, World world, boolean flag) {
			super(stack, player, world);
			this.additionnalEvent = flag;
		}
	}
	
	public static class BlockInteraction extends ItemEvent
	{
		public final int x;
		public final int y;
		public final int z;
		public final int side;
		
		public BlockInteraction(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side)
		{
			super(stack, player, world);
			this.x = x;
			this.y = y;
			this.z = z;
			this.side = side;
		}
	}
	
	public static class EntityInteraction extends ItemEvent
	{
		public final EntityLivingBase target;
		
		public EntityInteraction(ItemStack stack, EntityPlayer player, EntityLivingBase livingbase)
		{
			super(stack, player);
			this.target = livingbase;
		}
	}
}
