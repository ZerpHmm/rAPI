package rafoudiablol.api.network;

public class BadHandleException extends Exception
{
	public BadHandleException() {
		super();
	}
	
	public BadHandleException(String s) {
		super(s);
	}
	
	public BadHandleException(Throwable t) {
		super(t);
	}
	
	public BadHandleException(String s, Throwable t) {
		super(s, t);
	}
}
