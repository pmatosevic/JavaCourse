package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Models a request context with all parameters related to a single request
 * and with methods to write the output to the output stream.
 * 
 * @author Patrik
 *
 */
public class RequestContext {

	/**
	 * The output stream
	 */
	private OutputStream outputStream;
	
	/**
	 * The charset
	 */
	private Charset charset;
	
	/**
	 * The encoding
	 */
	private String encoding = "UTF-8";
	
	/**
	 * The status code
	 */
	private int statusCode = 200;
	
	/**
	 * Status text
	 */
	private String statusText = "OK";
	
	/**
	 * Mime type
	 */
	private String mimeType = "text/html";
	
	/**
	 * Content length
	 */
	private Long contentLength = null;
	
	/**
	 * Map of parameters of the request
	 */
	private Map<String,String> parameters;
	
	/**
	 * Map of temporary parameters
	 */
	private Map<String,String> temporaryParameters = new HashMap<>();
	
	/**
	 * Map of permanent session parameters
	 */
	private Map<String,String> persistentParameters;
	
	/**
	 * List of cookies
	 */
	private List<RCCookie> outputCookies;
	
	/**
	 * Flag whether the header was sent
	 */
	private boolean headerGenerated = false;
	
	/**
	 * The dispatcher
	 */
	private IDispatcher dispatcher;

	/**
	 * The session ID
	 */
	private String sid;
	
	/**
	 * Creates a new request context.
	 * @param outputStream output stream
	 * @param parameters parameters
	 * @param persistentParameters persistent parameters
	 * @param outputCookies output cookies
	 */
	public RequestContext(
			OutputStream outputStream,
			Map<String,String> parameters,
			Map<String,String> persistentParameters,
			List<RCCookie> outputCookies) {
		this.outputStream = Objects.requireNonNull(outputStream);
		this.parameters = parameters == null ? new HashMap<>() : parameters;
		this.parameters = Collections.unmodifiableMap(this.parameters);
		this.persistentParameters = persistentParameters == null ? new HashMap<>() : persistentParameters;
		this.outputCookies = outputCookies == null ? new ArrayList<>() : outputCookies;
	}
	
	/**
	 * Creates a new request context.
	 * @param outputStream output stream
	 * @param parameters parameters
	 * @param persistentParameters persistent parameters
	 * @param outputCookies output cookies
	 * @param temporaryParameters temporary parameters
	 * @param dispatcher dispatcher
	 * @param sid session ID
	 */
	public RequestContext(
			OutputStream outputStream,
			Map<String,String> parameters,
			Map<String,String> persistentParameters,
			List<RCCookie> outputCookies, 
			Map<String, String> temporaryParameters,
			IDispatcher dispatcher,
			String sid) {
		this(outputStream, parameters, persistentParameters, outputCookies);
		this.temporaryParameters = temporaryParameters;
		this.dispatcher = dispatcher;
		this.sid = sid;
	}
	
	
	/**
	 * Returns the dispatcher
	 * @return the dispatcher
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}
	
	
	/**
	 * @param encoding the encoding to set
	 */
	public void setEncoding(String encoding) {
		checkState();
		this.encoding = encoding;
	}



	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		checkState();
		this.statusCode = statusCode;
	}



	/**
	 * @param statusText the statusText to set
	 */
	public void setStatusText(String statusText) {
		checkState();
		this.statusText = statusText;
	}



	/**
	 * @param mimeType the mimeType to set
	 */
	public void setMimeType(String mimeType) {
		checkState();
		this.mimeType = mimeType;
	}

	/**
	 * @param contentLength the contentLength to set
	 */
	public void setContentLength(Long contentLength) {
		checkState();
		this.contentLength = contentLength;
	}

	/**
	 * Checks if the header was generated and throws exception in that case.
	 */
	private void checkState() {
		if (headerGenerated) {
			throw new IllegalStateException("The header was already written.");
		}
	}
	

	/**
	 * Method that retrieves value from parameters map (or null if no association exists).
	 * @param name name
	 * @return value
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}
	
	/**
	 * Method that retrieves names of all parameters in parameters map (note, this set must be read-only).
	 * @return names of all parameters in parameters map
	 */
	public Set<String> getParameterNames() {
		return parameters.keySet();
	}
	
	/**
	 * Method that retrieves value from persistentParameters map (or null if no association exists):
	 * @param name name
	 * @return value
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}
	
	/**
	 * Method that retrieves names of all parameters in persistent parameters map.
	 * @return names of all parameters in persistent parameters map
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}
	
	/**
	 * Method that stores a value to persistent parameters map:
	 * @param name name
	 * @param value value
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}
	
	
	/**
	 * Method that removes a value from persistent parameters map:
	 * @param name name
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}
	
	/**
	 * Method that retrieves value from temporary parameters map (or null if no association exists):
	 * @param name name
	 * @return value from temporary parameters map
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}
	
	/**
	 * Method that retrieves names of all parameters in temporary parameters map.
	 * @return names of all parameters in temporary parameters map
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}
	
	/**
	 * Method that retrieves an identifier which is unique for current user session.
	 * @return an identifier which is unique for current user session
	 */
	public String getSessionID() {
		return sid;
	}
	
	/**
	 * Method that stores a value to temporary parameters map:
	 * @param name name
	 * @param value value
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}
	
	/**
	 * Method that removes a value from temporary parameters map:
	 * @param name name
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}
	
	
	/**
	 * Writes the data to the output stream
	 * @param data data
	 * @return this request context
	 * @throws IOException in case of an IO error
	 */
	public RequestContext write(byte[] data) throws IOException {
		return write(data, 0, data.length);
	}
	
	
	/**
	 * Writes the data to the output stream
	 * @param data data
	 * @param offset offset
	 * @param len length
	 * @return this request context
	 * @throws IOException in case of an IO error
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		if (!headerGenerated) {
			writeHeader();
		}
		outputStream.write(data, offset, len);
		return this;
	}
	
	
	/**
	 * Writes the text to the output stream.
	 * @param text the text
	 * @return this request context
	 * @throws IOException in case of an IO error
	 */
	public RequestContext write(String text) throws IOException {
		if (!headerGenerated) {
			writeHeader();
		}
		return write(text.getBytes(charset));
	}
	
	/**
	 * Writes the header to the output stream
	 * @throws IOException in case of an IO error
	 */
	private void writeHeader() throws IOException {
		charset = Charset.forName(encoding);
		Charset headerCharset = StandardCharsets.ISO_8859_1;
		
		StringBuilder sb = new StringBuilder();
		sb.append("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");
		sb.append("Content-Type: " + mimeType);
		if (mimeType.startsWith("text/")) {
			sb.append("; charset=" + encoding);
		}
		sb.append("\r\n");
		if (contentLength != null) {
			sb.append("Content-Length: " + contentLength + "\r\n");
		}
		for (RCCookie cookie : outputCookies) {
			sb.append("Set-Cookie: " + cookie.asSetCookieText() + "\r\n");
		}
		sb.append("\r\n");
		
		byte[] header = sb.toString().getBytes(headerCharset);
		outputStream.write(header);
		outputStream.flush();
		headerGenerated = true;
	}
	
	/**
	 * Represents a cookie.
	 * 
	 * @author Patrik
	 *
	 */
	public static class RCCookie {
		
		/**
		 * Name of the cookie
		 */
		private String name;
		
		/**
		 * Value of the cookie
		 */
		private String value;
		
		/**
		 * Domain
		 */
		private String domain;
		
		/**
		 * Path
		 */
		private String path;
		
		/**
		 * Max age
		 */
		private Integer maxAge;

		/**
		 * Whether the cookie is http only
		 */
		private boolean httpOnly;
		
		/**
		 * Creates a new cookie
		 * @param name name
		 * @param value value
		 * @param domain domain
		 * @param path path
		 * @param maxAge max age
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			this.name = Objects.requireNonNull(name);
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}
		
		/**
		 * Creates a new cookie
		 * @param name name
		 * @param value value
		 * @param domain domain
		 * @param path path
		 * @param maxAge max age
		 * @param httpOnly whether the cookie is http only
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path, boolean httpOnly) {
			this(name, value, maxAge, domain, path);
			this.httpOnly = httpOnly;
		}


		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}


		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}


		/**
		 * @return the domain
		 */
		public String getDomain() {
			return domain;
		}


		/**
		 * @return the path
		 */
		public String getPath() {
			return path;
		}


		/**
		 * @return the maxAge
		 */
		public Integer getMaxAge() {
			return maxAge;
		}


		/**
		 * Returns the cookies as a text for "Set-Cookie:" header param.
		 * @return the cookies as a text
		 */
		public String asSetCookieText() {
			StringBuilder sb = new StringBuilder();
			sb.append(name + "=\"" + value + "\"");
			if (domain != null) {
				sb.append("; Domain=" + domain);
			}
			if (path != null) {
				sb.append("; Path=" + path);
			}
			if (maxAge != null) {
				sb.append("; Max-Age=" + maxAge);
			}
			if (httpOnly) {
				sb.append("; HttpOnly");
			}
			return sb.toString();
		}
	}

	/**
	 * Adds a cookie to the context
	 * @param rcCookie
	 */
	public void addRCCookie(RCCookie rcCookie) {
		checkState();
		outputCookies.add(rcCookie);
	}
	
}
