package rafoudiablol.api.network;

import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.Player;

/**
 *	@see PacketManager
**/
public interface IPacketDataReceiver
{
	public void receive(Packet250CustomPayload packet, Player player);
}
