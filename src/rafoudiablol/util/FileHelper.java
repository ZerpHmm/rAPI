package rafoudiablol.util;

public class FileHelper
{
	public static String removeExtension(String filename)
	{
		if(filename == null || !filename.contains("."))
		{
			return filename;
		}
		
		return filename.substring(0, filename.lastIndexOf("."));
	}
}
