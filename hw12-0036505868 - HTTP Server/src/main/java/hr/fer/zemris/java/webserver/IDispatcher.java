package hr.fer.zemris.java.webserver;

/**
 * An interface that represents a dispatcher that can further process the given path.
 * 
 * @author Patrik
 *
 */
public interface IDispatcher {
	
	/**
	 * Dispatches the request.
	 * @param urlPath path
	 * @throws Exception in case of an exception
	 */
	void dispatchRequest(String urlPath) throws Exception;
	
}