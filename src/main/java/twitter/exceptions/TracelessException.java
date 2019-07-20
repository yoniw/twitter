package twitter.exceptions;

public class TracelessException extends RuntimeException {

	public TracelessException(String message) {
		super(message);
	}
	
	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}