package rafoudiablol.api.enchantment.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class CharmEventRightClick extends CharmEvent
{
	public CharmEventRightClick(EntityPlayer player, World world) {
		super(player, world);
	}
	
	public CharmEventRightClick(EntityPlayer player) {
		super(player);
	}
}
