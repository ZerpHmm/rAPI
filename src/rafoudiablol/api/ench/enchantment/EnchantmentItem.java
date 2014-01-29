package rafoudiablol.api.ench.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;
import rafoudiablol.api.ench.IEnchantHandler;
import rafoudiablol.api.ench.event.ItemEvents;
import rafoudiablol.api.ench.event.ItemEvents.BlockInteraction;
import rafoudiablol.api.ench.event.ItemEvents.EntityInteraction;
import rafoudiablol.api.ench.event.ItemEvents.RightClick;
import rafoudiablol.api.ench.item.ItemEnchantable;

public class EnchantmentItem extends Enchantment
	implements IEnchantHandler
{
	/**
	 *	If not null, only derived classes can have this enchantment
	**/
	
	private final Class<? extends ItemEnchantable> enchantableClass;
	
	protected EnchantmentItem(int i, int j, EnumEnchantmentType type, Class<? extends ItemEnchantable> enchantableClass)
	{
		super(i, j, type);
		this.enchantableClass = enchantableClass;
	}
	
	@Override
    public boolean canApply(ItemStack stack)
    {
        return (enchantableClass == null) ? super.canApply(stack) : enchantableClass.isInstance(stack.getItem());
    }
	
	@Override
	public boolean handleEntityInteraction(int id, int lvl, ItemEvents.EntityInteraction event) {
		return false;
	}

	@Override
	public boolean handleBlockInteraction(int id, int lvl, ItemEvents.BlockInteraction event) {
		return false;
	}

	@Override
	public boolean handleRightClick(int id, int lvl, ItemEvents.RightClick event) {
		return false;
	}
}
