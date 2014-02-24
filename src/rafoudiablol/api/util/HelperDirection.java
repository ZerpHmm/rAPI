package rafoudiablol.api.util;

public interface HelperDirection
{
	/**
	 *	Directions (2D)
	**/

	public static final String GET_DIRECTION[] =
		{
			"SOUTH", "WEST", "NORTH", "EAST"
		};
	
	public static final int SOUTH_DIRECTION = 0;
	public static final int WEST_DIRECTION = 1;
	public static final int NORTH_DIRECTION = 2;
	public static final int EAST_DIRECTION = 3;

	/**
	 *	Axe X: East(+) / West(-)
	 *	Axe Z: South(+) / North(-)
	**/
	
	public static final int OFFSET_X[] =
		{
			0, -1, 0, 1
		};
	
	public static final int OFFSET_Z[] = 
		{
			1, 0, -1, 0
		};
	
	/**
	 *	Faces (3D)
	**/

	public static final String GET_FACE[] =
		{
			"DOWN", "UP", "NORTH", "SOUTH", "WEST", "EAST"
		};
	
	public static final int NORTH_FACE = 2;
	public static final int SOUTH_FACE = 3;
	public static final int WEST_FACE = 4;
	public static final int EAST_FACE = 5;
}
