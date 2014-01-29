package rafoudiablol.api.enchantment.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class CharmEventEntityInteract extends CharmEvent
{
	public final EntityLivingBase target;
	
	public CharmEventEntityInteract(EntityPlayer player, EntityLivingBase livingbase)
	{
		super(player);
		this.target = livingbase;
	}
}
