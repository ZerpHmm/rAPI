package rafoudiablol.api.enchantment;

import rafoudiablol.api.enchantment.event.CharmEventEntityInteract;
import rafoudiablol.api.enchantment.event.CharmEventRightClick;
import rafoudiablol.api.enchantment.event.CharmEventTileInteract;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemEnchantable extends Item
{
	public ItemEnchantable(int i) {
		super(i);
		this.setMaxStackSize(1);
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
    	final boolean happened1 = EnchantmentAPI.sendCharmEvent(stack, new CharmEventRightClick(player));
    	final boolean happened2 = EnchantmentAPI.sendCharmEvent(stack, new CharmEventEntityInteract(player, livingbase));
    	if(happened1 || happened2)
    	{
    		player.swingItem();
    	}
    	
		return happened1 || happened2;
	}
    
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player,
    	World world, int x, int y, int z, int side, float f1, float f2, float f3)
    {
    	final boolean happened1 = EnchantmentAPI.sendCharmEvent(stack, new CharmEventRightClick(player));
    	final boolean happened2 = EnchantmentAPI.sendCharmEvent(stack, new CharmEventTileInteract(player, world, x, y, z, side));
        return happened1 || happened2;
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if(EnchantmentAPI.sendCharmEvent(stack, new CharmEventRightClick(player)))
        {
        	player.swingItem();
        }
        
        return stack;
    }
}
