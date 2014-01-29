package rafoudiablol.api.enchantment.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class CharmEventTileInteract extends CharmEvent
{
	public final int x;
	public final int y;
	public final int z;
	public final int side;
	
	public CharmEventTileInteract(EntityPlayer player, World world, int x, int y, int z, int side)
	{
		super(player, world);
		this.x = x;
		this.y = y;
		this.z = z;
		this.side = side;
	}
}
