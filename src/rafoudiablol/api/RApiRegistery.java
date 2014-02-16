package rafoudiablol.api;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class RApiRegistery
{
	/**
	 *	Works only from a FMLPreInitializationEvent
	**/
	
	public static void registerBlock(Block block)
	{
		GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));
	}
}
