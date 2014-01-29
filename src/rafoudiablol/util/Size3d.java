package rafoudiablol.util;

public class Size3d
{
	public static final Size3d zero = new Size3d();
	
	public double x;
	public double y;
	public double z;
	
	public Size3d()
	{
		x = 0;
		y = 0;
		z = 0;
	}
	
	public Size3d(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
