package rafoudiablol.api.network;

import net.minecraft.entity.player.EntityPlayer;

/**
 *	@see PacketManager
**/
public interface IPacketDataSender
{
	public void toServer(PacketData data);
	public void toPlayer(PacketData data, EntityPlayer player);
	public void toAll(PacketData data);
}
