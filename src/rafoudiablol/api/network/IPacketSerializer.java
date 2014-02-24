package rafoudiablol.api.network;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.lang.reflect.Field;

public interface IPacketSerializer
{
	public void writeObject(Field field, ByteBuf buf, Object obj) throws IOException;
	public Object readObject(Field field, ByteBuf buf) throws IOException;
}
