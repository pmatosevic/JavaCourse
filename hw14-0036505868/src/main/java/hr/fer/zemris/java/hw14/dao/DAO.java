package hr.fer.zemris.java.hw14.dao;

import java.util.List;

import hr.fer.zemris.java.hw14.models.Poll;
import hr.fer.zemris.java.hw14.models.PollOption;

/**
 * An interface to access the data layer.
 * 
 * @author Patrik
 *
 */
public interface DAO {

	/**
	 * Loads all available polls.
	 * @return a list of polls
	 * @throws DAOException in case of an error
	 */
	List<Poll> loadAllPolls() throws DAOException;
	
	/**
	 * Returns the poll with given ID
	 * @param pollID poll ID
	 * @return the poll with given ID
	 * @throws DAOException in case of an error
	 */
	Poll loadPoll(long pollID) throws DAOException;
	
	/**
	 * Loads all poll option for the requested poll.
	 * @param pollID poll ID
	 * @return list of available options
	 * @throws DAOException in case of an error
	 */
	List<PollOption> loadPollOptions(long pollID) throws DAOException;
	
	/**
	 * Increments votes for the given option ID
	 * @param optionID option ID
	 * @throws DAOException in case of an error
	 */
	void incrementVotes(long optionID) throws DAOException;

}