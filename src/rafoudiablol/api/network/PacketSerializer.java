package rafoudiablol.api.network;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import rafoudiablol.api.packet.Serialize;

public class PacketSerializer
{
	/**
	 *	THE FIELDS WITH THE SERIALIZE ANNOTATION CAN'T BE NULL
	**/
	public static void serializeObject(PacketBuffer buf, Object obj)
			throws IOException
	{
		try
		{
			for(final Field field : obj.getClass().getFields())
			{
				if(field.isAnnotationPresent(Serialize.class))
				{
					PacketSerializer.serializeField(buf, field.get(obj));
				}
			}
		}
		catch(IllegalAccessException e)
		{
			throw new IllegalArgumentException(e);
		}
	}
	
	public static void deserializeObject(PacketBuffer buf, Object obj)
			throws IOException
	{
		try
		{
			for(final Field field : obj.getClass().getFields())
			{
				if(field.isAnnotationPresent(Serialize.class))
				{
					field.set(obj, PacketSerializer.deserializeField(buf, field.getType()));
				}
			}
		}
		catch(IllegalAccessException e)
		{
			throw new IllegalArgumentException(e);
		}
	}

	private static Object deserializeField(PacketBuffer buf, Class<?> clazz) throws IllegalAccessException, IOException
	{
		if(clazz.isArray())
		{
			final int length = buf.readInt(); // serialized array length
			final Class<?> subclazz = clazz.getComponentType();
			final Object result = Array.newInstance(subclazz, length);
			
			for(int i = 0 ; i < length ; ++i)
			{
				Array.set(result, i, PacketSerializer.deserializeField(buf, subclazz));
			}
			
			return result;
		}
		else if(clazz == byte.class)
			return buf.readByte();
		else if(clazz == short.class)
			return buf.readShort();
		else if(clazz == int.class)
			return buf.readInt();
		else if(clazz == long.class)
			return buf.readLong();
		else if(clazz == double.class)
			return buf.readDouble();
		else if(clazz == float.class)
			return buf.readFloat();
		else if(clazz == String.class)
			return buf.readStringFromBuffer(32767);
		else if(clazz == ItemStack.class)
			return buf.readItemStackFromBuffer();
		
		throw new IllegalArgumentException("Can't deserialize field of type " + clazz.getName());
	}
	
	/**
	 *	fieldObj can't be null
	**/
	private static void serializeField(PacketBuffer buf, Object fieldObj) throws IOException
	{
		final Class<?> clazz = fieldObj.getClass();

		if(clazz.isArray())
		{
			final int length = Array.getLength(fieldObj);
			buf.writeInt(length);
			
			for(int i = 0 ; i < length ; ++i)
			{
				PacketSerializer.serializeField(buf, Array.get(fieldObj, i));
			}
		}
		else if(clazz == Byte.class)
			buf.writeByte((Byte)fieldObj);
		else if(clazz == Short.class)
			buf.writeShort((Short)fieldObj);
		else if(clazz == Integer.class)
			buf.writeInt((Integer)fieldObj);
		else if(clazz == Long.class)
			buf.writeLong((Long)fieldObj);
		else if(clazz == Double.class)
			buf.writeDouble((Double)fieldObj);
		else if(clazz == Float.class)
			buf.writeFloat((Float)fieldObj);
		else if(clazz == String.class)
			buf.writeStringToBuffer((String)fieldObj);
		else if(clazz == ItemStack.class)
			buf.writeItemStackToBuffer((ItemStack)fieldObj);
		else
			throw new IllegalArgumentException("Can't serialize field of type " + clazz.getName());
	}
}