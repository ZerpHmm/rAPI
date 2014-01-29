package rafoudiablol.api.ench.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import rafoudiablol.api.ench.EnchantmentManager;
import rafoudiablol.api.ench.event.ItemEvents;

public class ItemEnchantable extends Item
{
	protected final EnchantmentManager manager;
	
	public ItemEnchantable(int i)
	{
		super(i);
		this.setMaxStackSize(1);
		this.manager = new EnchantmentManager();
	}
	
	public ItemEnchantable(int i, EnchantmentManager manager)
	{
		super(i);
		this.setMaxStackSize(1);
		this.manager = manager;
	}
    
	/**
	 *	You can override it
	**/
	
    @Override
    public int getItemEnchantability() {
        return 1;
    }
	
    @Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player,
		EntityLivingBase livingbase)
	{
		final boolean result1 = manager.sendItemEventToEnchantments(new ItemEvents.RightClick(stack, player, true));
		final boolean result2 = manager.sendItemEventToEnchantments(new ItemEvents.EntityInteraction(stack, player, livingbase));
		if(result1 || result2)
		{
			player.swingItem();
		}
		
		return result1 || result2;
	}
    
    @Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player,
    	World world, int x, int y, int z, int side, float f1, float f2, float f3)
    {
		final boolean result1 = manager.sendItemEventToEnchantments(new ItemEvents.RightClick(stack, player, true));
		final boolean result2 = manager.sendItemEventToEnchantments(new ItemEvents.BlockInteraction(stack, player, world, x, y, z, side));
		return result1 || result2;
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
    	final boolean result = manager.sendItemEventToEnchantments(new ItemEvents.RightClick(stack, player));
    	if(result)
    	{
    		player.swingItem();
    	}
    	
		return stack;
    }
}
