package hr.fer.zemris.java.hw14.models;

/**
 * Models a single poll option.
 * 
 * @author Patrik
 *
 */
public class PollOption {

	/** Option ID */
	private long id;
	
	/** Option title */
	private String optionTitle;
	
	/** Optional URL link for the option */
	private String optionLink;
	
	/** Current votes count */
	private long votesCount;
	
	/** ID of the poll of this option */
	private long pollId;
	
	/**
	 * Creates a new poll option.
	 * @param id ID
	 * @param optionTitle option title
	 * @param optionLink optional URL link for the option
	 * @param votesCount current votes count
	 * @param pollId ID of the poll of this option
	 */
	public PollOption(long id, String optionTitle, String optionLink, long votesCount, long pollId) {
		this.id = id;
		this.optionTitle = optionTitle;
		this.optionLink = optionLink;
		this.votesCount = votesCount;
		this.pollId = pollId;
	}
	
	/**
	 * Creates a new poll option.
	 */
	public PollOption() {
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
	 * @return the optionTitle
	 */
	public String getOptionTitle() {
		return optionTitle;
	}
	
	/**
	 * @param optionTitle the optionTitle to set
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}
	
	/**
	 * @return the optionLink
	 */
	public String getOptionLink() {
		return optionLink;
	}
	
	/**
	 * @param optionLink the optionLink to set
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}
	
	/**
	 * @return the votesCount
	 */
	public long getVotesCount() {
		return votesCount;
	}
	
	/**
	 * @param votesCount the votesCount to set
	 */
	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}
	
	/**
	 * @return the pollId
	 */
	public long getPollId() {
		return pollId;
	}
	
	/**
	 * @param pollId the pollId to set
	 */
	public void setPollId(long pollId) {
		this.pollId = pollId;
	}
	
	
	
}
