package rafoudiablol.api.ench;

import rafoudiablol.api.ench.event.ItemEvents;

public interface IEnchantHandler
{
	public boolean handleEntityInteraction(int id, int lvl, ItemEvents.EntityInteraction event);
	public boolean handleBlockInteraction(int id, int lvl, ItemEvents.BlockInteraction event);
	public boolean handleRightClick(int id, int lvl, ItemEvents.RightClick event);
}
