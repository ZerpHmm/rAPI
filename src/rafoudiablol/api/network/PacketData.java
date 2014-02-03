package rafoudiablol.api.network;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class PacketData
{
	protected PacketData()
	{
	}
	
	public abstract String getDataName();
	public abstract int getDataID();
	
	public abstract void readData(DataInput in) throws IOException;
	public abstract void writeData(DataOutput out) throws IOException;
}
