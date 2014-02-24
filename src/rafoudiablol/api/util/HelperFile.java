package rafoudiablol.api.util;

import java.io.File;

public class HelperFile
{
	public static String removeExtension(String filename)
	{
		if(filename == null || !filename.contains("."))
		{
			return filename;
		}
		
		return filename.substring(0, filename.lastIndexOf("."));
	}
	
	public static File getDirectory(File dir)
	{
		if(dir.isFile())
		{
			dir.delete();
		}
		if(!dir.exists())
		{
			dir.mkdirs();
		}
		
		return dir;
	}
}
