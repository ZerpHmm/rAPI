package rafoudiablol.api;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils
{
	private static MessageDigest md5Digest;
	
	static
	{
		try
		{
			md5Digest = MessageDigest.getInstance("MD5");
		}
		catch(NoSuchAlgorithmException e)
		{
			ModCore.instance.getLogger().error("SEVERE!! Unable to find MD5 MessageDigest");
		}
	}
	
	/**
	 *	Use UTF-8
	 *
	 *	@param string
	 * 	@return md5 coressponding string
	**/
	
	public static String fromString(String string)
	{
		md5Digest.update(string.getBytes(), 0, string.length());
		return new BigInteger(1, md5Digest.digest()).toString(16);
	}
}
