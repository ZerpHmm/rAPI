package rafoudiablol.api.util;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class HelperNetwork
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
