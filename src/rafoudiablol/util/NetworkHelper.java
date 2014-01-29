package rafoudiablol.util;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class NetworkHelper
{
	/**
	 *	http://stackoverflow.com/questions/1600291/validating-url-in-java
	**/
	
	public static boolean isValidURL(String address)
	{  
	    URL url = null;

	    try {  
	    	url = new URL(address);  
	    } catch (MalformedURLException e) {  
	        return false;  
	    }

	    try {  
	    	url.toURI();  
	    } catch (URISyntaxException e) {  
	        return false;  
	    }  

	    return true;  
	} 
}
