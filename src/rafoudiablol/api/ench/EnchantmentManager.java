package rafoudiablol.api.ench;

import java.util.HashMap;
import java.util.Map;

import rafoudiablol.api.ench.event.ItemEvent;
import rafoudiablol.api.ench.event.ItemEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class EnchantmentManager
{
	/**
	 *	@return true if something happened
	**/
	
	public boolean sendItemEventToEnchantments(ItemEvent event)
	{
		boolean somethingHappened = false;
    	final ItemStack stack = event.stack;
    	
		if(stack.isItemEnchanted())
		{
			final Map<?, ?> enchantments = EnchantmentHelper.getEnchantments(stack);
	    	
			for(Map.Entry<?, ?> entry : enchantments.entrySet())
			{
				final int id = (Integer)entry.getKey();
				final int lvl = (Integer)entry.getValue();
				final Enchantment enchantment = Enchantment.enchantmentsList[id];
		    	
				if(enchantment instanceof IEnchantHandler)
				{
					if(processEventToHandler(id, lvl, event, (IEnchantHandler)enchantment)) {
						somethingHappened = true;
					}
				}
			}
		}
		
		return somethingHappened;
	}
	
	protected boolean processEventToHandler(int id, int lvl, ItemEvent event, IEnchantHandler handler)
	{
		if(event instanceof ItemEvents.BlockInteraction)
		{
			return handler.handleBlockInteraction(id, lvl, (ItemEvents.BlockInteraction)event);
		}
		else if(event instanceof ItemEvents.EntityInteraction)
		{
			return handler.handleEntityInteraction(id, lvl, (ItemEvents.EntityInteraction)event);
		}
		else if(event instanceof ItemEvents.RightClick)
		{
			return handler.handleRightClick(id, lvl, (ItemEvents.RightClick)event);
		}
		
		// wtf ??
		return false;
	}
}
