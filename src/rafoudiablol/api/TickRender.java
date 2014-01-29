package rafoudiablol.api;

import java.util.EnumSet;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public abstract class TickRender implements ITickHandler
{
	protected abstract void renderTick(float renderPartialTicks);
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		// Ne pas utiliser ici
		// Car tout ce qui aurait été affiché serait écrasé par l'affichage standard de minecraft 
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		renderTick((Float)tickData[0]);
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.RENDER);
	}
}
