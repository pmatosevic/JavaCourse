package hr.fer.zemris.java.webserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * A fully functional multi-threaded webserver capable of serving simple static pages and files,
 * but also capable of executing complex scripts.
 * 
 * @author Patrik
 *
 */
public class SmartHttpServer {

	/**
	 * Server name
	 */
	private static String SERVER_NAME = "simple java server";
	
	/**
	 * Package of available workers
	 */
	private static String WORKERS_PACKAGE = "hr.fer.zemris.java.webserver.workers.";
	
	/**
	 * Path for access to workers
	 */
	private static String EXT_PATH = "/ext/";
	
	/**
	 * Length of the session id
	 */
	private static int SID_LENGTH = 20;
	
	/**
	 * Flag to stop server thread
	 */
	private volatile boolean stopServer = false;
	
	/**
	 * Server address
	 */
	private String address;
	
	/**
	 * Domain name
	 */
	private String domainName;
	
	/**
	 * Port
	 */
	private int port;
	
	/**
	 * Number of worker threads
	 */
	private int workerThreads;
	
	/**
	 * Session timeout
	 */
	private int sessionTimeout;
	
	/**
	 * Map of all mime types
	 */
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	
	/**
	 * The main server thread
	 */
	private ServerThread serverThread;
	
	/**
	 * Pool of executors
	 */
	private ExecutorService threadPool;
	
	/**
	 * Document root path
	 */
	private Path documentRoot;
	
	/**
	 * Map of registered workers
	 */
	private Map<String,IWebWorker> workersMap = new HashMap<>();
	
	/**
	 * Map of all session data
	 */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();
	
	/**
	 * Random object to generate session identifiers
	 */
	private Random sessionRandom = new Random();
	
	/**
	 * Thread that removes old sessions
	 */
	private SessionGCThread sessionGC;
	
	/**
	 * Program entry point
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Expected path to the config file.");
			return;
		}
		try {
			new SmartHttpServer(args[0]).start();
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Creates a new webserver
	 * @param configFileName path to the config file
	 */
	public SmartHttpServer(String configFileName) {
		Path configPath = Paths.get(configFileName);
		Properties config = new Properties();
		try (InputStream is = Files.newInputStream(configPath)) {
			config.load(is);
		} catch (IOException e) {
			throw new IllegalArgumentException("Could not read config file: " + e.getMessage());
		}
		
		address = config.getProperty("server.address");
		domainName = config.getProperty("server.domainName");
		port = Integer.parseInt(config.getProperty("server.port"));
		workerThreads = Integer.parseInt(config.getProperty("server.workerThreads"));
		documentRoot = Paths.get(config.getProperty("server.documentRoot")).toAbsolutePath().normalize();
		sessionTimeout = Integer.parseInt(config.getProperty("session.timeout"));
		
		Path mimePath = Paths.get(config.getProperty("server.mimeConfig"));
		Path workersPath = Paths.get(config.getProperty("server.workers"));
		
		Properties mimes = new Properties();
		try (InputStream is = Files.newInputStream(mimePath)) {
			mimes.load(is);
		} catch (IOException e) {
			throw new IllegalArgumentException("Could not read mime file: " + e.getMessage());
		}
		for (String name : mimes.stringPropertyNames()) {
			mimeTypes.put(name, mimes.getProperty(name));
		}
		
		try {
			loadWorkers(workersPath);
		} catch (IOException e) {
			throw new IllegalArgumentException("Could not read workers file: " + e.getMessage());
		}
	}

	/**
	 * Loads the registered workers
	 * @param workersPath path to the workers config
	 * @throws IOException in case of an IO error
	 */
	private void loadWorkers(Path workersPath) throws IOException {
		List<String> lines = Files.readAllLines(workersPath);
		for (String line : lines) {
			line = line.trim();
			if (line.isEmpty() || line.startsWith("#")) continue;
			String[] parts = line.split("=");
			if (parts.length != 2) {
				throw new IllegalArgumentException("Illegal format of workers file");
			}
			
			String path = parts[0].trim();
			String fqcn = parts[1].trim();
			if (workersMap.containsKey(path)) {
				throw new IllegalArgumentException("Illegal format of workers file");
			}
			
			workersMap.put(path, loadWorkerClass(fqcn));
		}
	}
	
	/**
	 * Loads a worker class to the JVM
	 * @param fqcn fully qualified class name
	 * @return class casted to a web worker
	 */
	private IWebWorker loadWorkerClass(String fqcn) {
		try {
			Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
			Object newObject = referenceToClass.getConstructor().newInstance();
			IWebWorker iww = (IWebWorker) newObject;
			return iww;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | 
				IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Starts the webserver
	 */
	protected synchronized void start() {
		if (serverThread == null || !serverThread.isAlive()) {
			stopServer = false;
			serverThread = new ServerThread();
			threadPool = Executors.newFixedThreadPool(workerThreads);
			serverThread.start();
			
			sessionGC = new SessionGCThread();
			sessionGC.start();
		}
	}

	/**
	 * Stops the webserver
	 */
	protected synchronized void stop() {
		stopServer = true;
		threadPool.shutdown();
		sessionGC.interrupt();
	}

	/**
	 * A single session entry.
	 * 
	 * @author Patrik
	 *
	 */
	private static class SessionMapEntry {
		/** Session ID */
		String sid;
		/** Host */
		String host;
		/** Valid until */
		long validUntil;
		/** Map of data */
		Map<String,String> map;
	}
	
	/**
	 * A thread that removes sessions that are not valid anymore.
	 * @author Patrik
	 *
	 */
	protected class SessionGCThread extends Thread {
		
		/**
		 * Sleep time
		 */
		private static final int SLEEP = 5*60*1000;
		
		/**
		 * Creates a new thread
		 */
		public SessionGCThread() {
			setDaemon(true);
		}
		
		@Override
		public void run() {
			while (!stopServer) {
				long currTime = System.currentTimeMillis() / 1000L;
				Iterator<Map.Entry<String,SmartHttpServer.SessionMapEntry>> it = 
						sessions.entrySet().iterator();
				while (it.hasNext()) {
					if (it.next().getValue().validUntil < currTime) {
						it.remove();
					}
				}
				try {
					Thread.sleep(SLEEP);
				} catch (InterruptedException e) {}
			}
		}
	}
	
	/**
	 * The main server thread.
	 * 
	 * @author Patrik
	 *
	 */
	protected class ServerThread extends Thread {

		@Override
		public void run() {
			try (ServerSocket serverSocket = new ServerSocket()) {
				serverSocket.bind(new InetSocketAddress(InetAddress.getByName(address), port));
				while (!stopServer) {
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					if (!threadPool.isShutdown()) {
						threadPool.submit(cw);
					}
				}
			} catch (IOException e) {
				System.out.println("Error in server thread: " + e.getMessage());
				System.out.println("Server stopped.");
			}
		}

		/**
		 * A class that models a worker that processes a single client request.
		 * 
		 * @author Patrik
		 *
		 */
		private class ClientWorker implements Runnable, IDispatcher {
			
			/** The socket */
			private Socket csocket;
			
			/** Input stream */
			private PushbackInputStream istream;
			
			/** Output stream */
			private OutputStream ostream;
			
			/** HTTP version */
			private String version;
			
			/** HTTP method */
			private String method;
			
			/** The host */
			private String host;
			
			/** Map of session parameters */
			private Map<String, String> params = new HashMap<String, String>();
			
			/** Map of temporary parameters */
			private Map<String, String> tempParams = new HashMap<String, String>();
			
			/** Map of permanent parameters */
			private Map<String, String> permPrams = new HashMap<String, String>();
			
			/** Map of all cookies */
			private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
			
			/** Session ID */
			private String SID;
			
			/** Request context */
			private RequestContext context;
	
			/**
			 * Creates a new worker
			 * @param csocket client socket
			 */
			public ClientWorker(Socket csocket) {
				super();
				this.csocket = csocket;
			}
	
			@Override
			public void run() {
				try {
					System.out.println("Serving client: " + csocket.getRemoteSocketAddress());
					istream = new PushbackInputStream(new BufferedInputStream(csocket.getInputStream()));
					ostream = new BufferedOutputStream(csocket.getOutputStream());
					
					List<String> request = readRequest();
					if (request.size() == 0) {
						sendError(400, "Bad Header");
						return;
					}
					
					String firstLine = request.get(0);
					String[] firstLineParts = firstLine.split("\\s+");
					if (firstLineParts.length != 3) {
						sendError(400, "Bad Header");
						return;
					}
					method = firstLineParts[0].toUpperCase();
					if (!method.equals("GET")) {
						sendError(405, "Method Not Allowed");
						return;
					}
					
					String requestedPath = firstLineParts[1];
					
					version = firstLineParts[2].toUpperCase();
					if(!version.equals("HTTP/1.1") && !version.equals("HTTP/1.0")) {
						sendError(505, "HTTP Version Not Supported");
						return;
					}
					
					host = domainName;
					for (String headerLine : request) {
						if (headerLine.startsWith("Host:")) {
							host = headerLine.substring(5).trim().split(":")[0];
							break;
						}
					}
					
					checkSession(request);
					
					String[] requestedPathParts = requestedPath.split("\\?", 2);
					String path = requestedPathParts[0];
					String paramString = (requestedPathParts.length == 1) ? "" : requestedPathParts[1];
					parseParameters(paramString);
					
					System.out.println("\tRequested path: " + path);
					internalDispatchRequest(path, true);
					
					ostream.flush();
				} catch (Exception e) {
					System.out.println("Error while serving client: " + 
								String.valueOf(csocket.getRemoteSocketAddress()));
					System.out.println("Error: " + e);
				} finally {
					try {
						csocket.close();
						System.out.println("Connetion closed.");
						System.out.println();
					} catch (IOException e) {}
				}
			}

			/**
			 * Returns the extension
			 * @param filename file name
			 * @return the extension
			 */
			private String determineExtension(String filename) {
				int index = filename.lastIndexOf(".");
				return (index == -1) ? "" : filename.substring(index + 1);
			}

			/**
			 * Parses the parameters
			 * @param paramString the parameters string
			 */
			private void parseParameters(String paramString) {
				String[] paramsStr = paramString.split("&");
				for (String paramStr : paramsStr) {
					String[] parts = paramStr.split("=", 2);
					String name = parts[0];
					String value = (parts.length == 1) ? "" : parts[1];
					params.put(name, value);
				}
			}
			
			/**
			 * Checks and performs session initialization.
			 * @param request request header
			 */
			private void checkSession(List<String> request) {
				String sidCandidate = null;
				for (String line : request) {
					if (!line.startsWith("Cookie:")) continue;
					
					String[] cookiesParts = line.substring(7).trim().split(";");
					for (String cookieStr : cookiesParts) {
						String[] parts = cookieStr.trim().split("=", 2);
						if (parts.length != 2) continue;
						
						if (parts[0].equals("sid")) {
							sidCandidate = parts[1].substring(1, parts[1].length()-1);
							break;
						}
					}
					
					if (sidCandidate != null) break;
				}
				
				synchronized (SmartHttpServer.this) {
					SessionMapEntry entry = checkSid(sidCandidate);
					permPrams = entry.map;
				}
			}
			
			/**
			 * Checks and returns the session entry
			 * @param sidCandidate session id
			 * @return the session entry
			 */
			private SessionMapEntry checkSid(String sidCandidate) {
				long secondsNow = Instant.now().getEpochSecond();
				SessionMapEntry session = sidCandidate == null ? null : sessions.get(sidCandidate);
				
				if (session != null && !session.host.equals(host)) {
					session = null;
				}
				if (session != null && session.validUntil < secondsNow) {
					sessions.remove(sidCandidate);
					session = null;
				}
				
				if (session != null) {
					session.validUntil = secondsNow + sessionTimeout;
				} else {
					session = generateNewEntry();
					sessions.put(session.sid, session);
					outputCookies.add(new RCCookie("sid", session.sid, null, host, "/", true));
				}
				
				return session;
			}
			
			/**
			 * Generates a new session entry with random ID
			 * @return a new session entry with random ID
			 */
			private SessionMapEntry generateNewEntry() {
				SessionMapEntry entry = new SessionMapEntry();
				
				StringBuilder sb = new StringBuilder();
				for (int i=0; i<SID_LENGTH; i++) {
					sb.append((char) ('A' + sessionRandom.nextInt(26)));
				}
				
				entry.sid = sb.toString();
				entry.host = host;
				entry.validUntil = Instant.now().getEpochSecond() + sessionTimeout;
				entry.map = Collections.synchronizedMap(new HashMap<>());
				return entry;
			}
			
			/**
			 * Sends a HTTP error header
			 * @param statusCode status code
			 * @param statusText status text
			 * @throws IOException in case of an error
			 */
			private void sendError(int statusCode, String statusText) throws IOException {
				System.out.println("\tSending error: " + statusCode);
				ostream.write(
						("HTTP/1.1 "+statusCode+" "+statusText+"\r\n"+
						"Server: " + SERVER_NAME + "\r\n"+
						"Content-Type: text/plain;charset=UTF-8\r\n"+
						"Content-Length: 0\r\n"+
						"Connection: close\r\n"+
						"\r\n").getBytes(StandardCharsets.US_ASCII)
					);
				ostream.flush();
			}

			/**
			 * Reads the request from the socket
			 * @return header lines
			 * @throws IOException in case of an error
			 */
			private List<String> readRequest() throws IOException {
				byte[] request = readRequest(istream);
				if(request==null) request = new byte[0];
				String requestStr = new String(request, StandardCharsets.US_ASCII);
				return extractHeaders(requestStr);
			}
			
			/**
			 * Reads the bytes from the header
			 * @param is input stream
			 * @return bytes from the header
			 * @throws IOException in case of an error
			 */
			private byte[] readRequest(InputStream is) throws IOException {

				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				int state = 0;
l:				while(true) {
					int b = is.read();
					if(b==-1) return null;
					if(b!=13) {
						bos.write(b);
					}
					switch(state) {
					case 0: 
						if(b==13) { state=1; } else if(b==10) state=4;
						break;
					case 1: 
						if(b==10) { state=2; } else state=0;
						break;
					case 2: 
						if(b==13) { state=3; } else state=0;
						break;
					case 3: 
						if(b==10) { break l; } else state=0;
						break;
					case 4: 
						if(b==10) { break l; } else state=0;
						break;
					}
				}
				return bos.toByteArray();
			}
			
			/**
			 * Extracts the whole header by lines
			 * @param requestHeader whole header
			 * @return whole header by lines
			 */
			private List<String> extractHeaders(String requestHeader) {
				List<String> headers = new ArrayList<String>();
				String currentLine = null;
				for(String s : requestHeader.split("\n")) {
					if(s.isEmpty()) break;
					char c = s.charAt(0);
					if(c==9 || c==32) {
						currentLine += s;
					} else {
						if(currentLine != null) {
							headers.add(currentLine);
						}
						currentLine = s;
					}
				}
				if(!currentLine.isEmpty()) {
					headers.add(currentLine);
				}
				return headers;
			}

			/**
			 * Processes the request given the requested path
			 * @param urlPath requested path
			 * @param directCall whether the client called it
			 * @throws Exception in case of an error
			 */
			private void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
				if (context == null) {
					context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this, SID);
				}
				
				if (directCall && (urlPath.equals("/private") || urlPath.startsWith("/private/"))) {
					sendError(404, "Not Found");
					return;
				}
				
				if (workersMap.containsKey(urlPath)) {
					workersMap.get(urlPath).processRequest(context);
					return;
				}
				if (urlPath.startsWith(EXT_PATH)) {
					String fqcn = WORKERS_PACKAGE + urlPath.substring(EXT_PATH.length());
					IWebWorker worker;
					try {
						worker = loadWorkerClass(fqcn);
					} catch (IllegalArgumentException e) {
						sendError(404, "Not Found");
						return;
					}
					worker.processRequest(context);
					return;
				}
				
				Path requestedFile = documentRoot.resolve(urlPath.substring(1)).toAbsolutePath().normalize();
				if (!requestedFile.startsWith(documentRoot)) {
					sendError(403, "Forbidden");
					return;
				}
				if (!Files.isReadable(requestedFile) || !Files.isRegularFile(requestedFile)) {
					sendError(404, "Not Found");
					return;
				}
				
				String filename = requestedFile.getFileName().toString();
				String ext = determineExtension(filename);
				if (ext.equals("smscr")) {
					serveSmscr(requestedFile);
				} else {
					serveFile(requestedFile, ext);
				}
			}

			/**
			 * Serves the file to the client
			 * @param requestedFile requested file
			 * @param ext extension
			 * @throws IOException in case of an error
			 */
			private void serveFile(Path requestedFile, String ext) throws IOException {
				String mime = mimeTypes.get(ext);
				if (mime == null) mime = "application/octet-stream";
				
				context.setMimeType(mime);
				context.setStatusCode(200);
				context.setStatusText("OK");
				
				long size = Files.size(requestedFile);
				context.setContentLength(size);
				
				try (InputStream fis = new BufferedInputStream(Files.newInputStream(requestedFile))) {
					byte[] buf = new byte[1024];
					while(true) {
						int r = fis.read(buf);
						if (r < 1) break;
						context.write(buf, 0, r);
					}
				}
			}
			
			/**
			 * Seves the smscr file
			 * @param requestedFile requested path
			 * @throws IOException in case of an error
			 */
			private void serveSmscr(Path requestedFile) throws IOException {
				context.setMimeType("text/html");
				context.setStatusCode(200);
				context.setStatusText("OK");
				
				String documentBody = Files.readString(requestedFile);
				new SmartScriptEngine(
						new SmartScriptParser(documentBody).getDocumentNode(),
						context
				).execute();
			}

			@Override
			public void dispatchRequest(String urlPath) throws Exception {
				internalDispatchRequest(urlPath, false);
			}

				
		}
		
		
	}
	
	
}
