package hr.fer.zemris.java.webserver;

/**
 * An interface that represents an web worker that can process a request.
 * @author Patrik
 *
 */
public interface IWebWorker {
	
	/**
	 * Processes the request.
	 * @param context request context
	 * @throws Exception in case of an exception
	 */
	public void processRequest(RequestContext context) throws Exception;
	
}