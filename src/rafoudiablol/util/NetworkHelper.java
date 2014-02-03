package rafoudiablol.util;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class NetworkHelper
{
	public static URL getUrlFromString(String address)
	{
		try
		{
			return new URL(address);
		}
		catch (MalformedURLException e)
		{
			return null;
		}
		
	}
}
