package rafoudiablol.api.network;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class PacketData
{
	public PacketData() {
		// trivial constructor obligatory
	}
	
	public abstract void readData(DataInput in) throws IOException;
	public abstract void writeData(DataOutput out) throws IOException;
	
	public abstract String getDataName();
	public abstract int getDataID();
}
