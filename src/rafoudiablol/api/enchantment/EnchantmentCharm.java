package rafoudiablol.api.enchantment;

import rafoudiablol.api.enchantment.event.CharmEvent;
import rafoudiablol.api.enchantment.event.CharmEventEntityInteract;
import rafoudiablol.api.enchantment.event.CharmEventRightClick;
import rafoudiablol.api.enchantment.event.CharmEventTileInteract;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class EnchantmentCharm extends Enchantment
{
	/**
	 *	If not null, only derived classes can have this enchantment
	**/
	
	private final Class<? extends Item> enchantableItem;
	
	protected EnchantmentCharm(int i, int j, EnumEnchantmentType type)
	{
		super(i, j, type);
		enchantableItem = null;
	}
	
	protected EnchantmentCharm(int i, int j, Class<? extends Item> enchantableItem)
	{
		super(i, j, EnumEnchantmentType.all);
		this.enchantableItem = enchantableItem;
	}
	
	protected EnchantmentCharm(int i, int j, EnumEnchantmentType type, Class<? extends Item> enchantableItem)
	{
		super(i, j, type);
		this.enchantableItem = enchantableItem;
	}

	@Override
    public boolean canApply(ItemStack stack)
    {
        return (enchantableItem == null) ? super.canApply(stack) : enchantableItem.isInstance(stack.getItem());
    }
	
	/**
	 *	@return true if something happened
	**/
	
	public final boolean handleCharmEvent(int id, int lvl, CharmEvent event)
	{
		if(event instanceof CharmEventRightClick) {
			return this.handleRightClick(id, lvl, (CharmEventRightClick)event);
		}
		else if(event instanceof CharmEventTileInteract) {
			return this.handleTileInteract(id, lvl, (CharmEventTileInteract)event);
		}
		else if(event instanceof CharmEventEntityInteract) {
			return this.handleEntityInteract(id, lvl, (CharmEventEntityInteract)event);
		}
		else {
			System.out.println("WTF");
		}
		
		return false;
	}
	
	/**
	 *	@return true if something happened
	**/
		
	protected boolean handleRightClick(int id, int lvl, CharmEventRightClick event) {
		return false;
	}
	
	/**
	 *	@return true if something happened
	**/
	
	protected boolean handleTileInteract(int id, int lvl, CharmEventTileInteract event) {
		return false;
	}
	
	/**
	 *	@return true if something happened
	**/
	
	protected boolean handleEntityInteract(int id, int lvl, CharmEventEntityInteract event) {
		return false;
	}
}
