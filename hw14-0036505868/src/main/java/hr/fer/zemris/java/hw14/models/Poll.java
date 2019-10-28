package hr.fer.zemris.java.hw14.models;

/**
 * Represents a single poll.
 * 
 * @author Patrik
 *
 */
public class Poll {

	/** Poll ID */
	private long id;
	
	/** Pool title */
	private String title;
	
	/** Pool detailed message */
	private String message;
	
	/**
	 * Creates a new poll model.
	 * 
	 * @param id ID
	 * @param title title
	 * @param message detailed message
	 */
	public Poll(long id, String title, String message) {
		this.id = id;
		this.title = title;
		this.message = message;
	}
	
	/**
	 * Creates a new poll model.
	 */
	public Poll() {
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
