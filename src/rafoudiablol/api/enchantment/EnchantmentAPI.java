package rafoudiablol.api.enchantment;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import rafoudiablol.api.enchantment.event.CharmEvent;

public class EnchantmentAPI
{
	/**
	 * @return true if something happened
	**/
	
	public static boolean sendCharmEvent(ItemStack stack, CharmEvent event)
	{
		boolean somethingHappened = false;
    	
		if(stack.isItemEnchanted())
		{
			final Map<?, ?> enchantments = EnchantmentHelper.getEnchantments(stack);
	    	
			for(Map.Entry<?, ?> entry : enchantments.entrySet())
			{
				final int id = (Integer)entry.getKey();
				final int lvl = (Integer)entry.getValue();
				final Enchantment enchantment = Enchantment.enchantmentsList[id];
		    	
				if(enchantment instanceof EnchantmentCharm)
				{
					final EnchantmentCharm charm = (EnchantmentCharm)enchantment;

					if(charm.handleCharmEvent(id, lvl, event) && !somethingHappened)
					{
						somethingHappened = true;
					}
				}
			}
		}
		
		return somethingHappened;
	}
}
