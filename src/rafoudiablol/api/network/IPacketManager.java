package rafoudiablol.api.network;

import rafoudiablol.api.packet.AbstractPacket;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayerMP;

public interface IPacketManager
{
    public boolean registerPacket(Class<? extends AbstractPacket> clazz);
    public void sendToAll(AbstractPacket packet);
    public void sendTo(AbstractPacket packet, EntityPlayerMP player);
    public void sendToAllAround(AbstractPacket packet, NetworkRegistry.TargetPoint point);
    public void sendToDimension(AbstractPacket packet, int dimensionId);
    public void sendToServer(AbstractPacket packet);
}
