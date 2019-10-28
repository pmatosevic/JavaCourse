package hr.fer.zemris.java.hw14.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOException;
import hr.fer.zemris.java.hw14.models.Poll;
import hr.fer.zemris.java.hw14.models.PollOption;


/**
 * Inplementation of the {@link DAO} interface using SQL database access.
 * @author Patrik
 *
 */
public class SQLDAO implements DAO {

	@Override
	public List<Poll> loadAllPolls() throws DAOException {
		List<Poll> polls = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		try {
			try (PreparedStatement pst = con.prepareStatement("SELECT id, title, message FROM Polls")) {
				try (ResultSet rs = pst.executeQuery()) {
					while (rs != null && rs.next()) {
						Poll poll = new Poll();
						poll.setId(rs.getLong(1));
						poll.setTitle(rs.getString(2));
						poll.setMessage(rs.getString(3));
						polls.add(poll);
					}
				}
			}
		} catch (Exception e) {
			throw new DAOException("Error during loading polls.", e);
		}
		return polls;
	}
	
	@Override
	public Poll loadPoll(long pollID) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		try {
			try (PreparedStatement pst = con.prepareStatement("SELECT id, title, message FROM Polls WHERE id = ?")) {
				pst.setLong(1, pollID);
				try (ResultSet rs = pst.executeQuery()) {
					if (rs != null && rs.next()) {
						Poll poll = new Poll();
						poll.setId(rs.getLong(1));
						poll.setTitle(rs.getString(2));
						poll.setMessage(rs.getString(3));
						return poll;
					} else {
						return null;
					}
				}
			}
		} catch (Exception e) {
			throw new DAOException("Error during loading polls.", e);
		}
	}



	@Override
	public List<PollOption> loadPollOptions(long pollID) throws DAOException {
		List<PollOption> options = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		try {
			try (PreparedStatement pst = con.prepareStatement(
					"SELECT id, optionTitle, optionLink, votesCount FROM PollOptions WHERE pollID = ?")) {
				pst.setLong(1, pollID);
				try (ResultSet rs = pst.executeQuery()) {
					while (rs != null && rs.next()) {
						PollOption option = new PollOption();
						option.setPollId(pollID);
						option.setId(rs.getLong(1));
						option.setOptionTitle(rs.getString(2));
						option.setOptionLink(rs.getString(3));
						option.setVotesCount(rs.getLong(4));
						options.add(option);
					}
				}
			}
		} catch (Exception e) {
			throw new DAOException("Error during loading options.", e);
		}
		return options;
	}

	@Override
	public void incrementVotes(long optionID) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		try {
			try (PreparedStatement pst = con.prepareStatement(
					"UPDATE PollOptions SET votesCount = votesCount+1 WHERE id = ?")) {
				pst.setLong(1, optionID);
				int affected = pst.executeUpdate();
				if (affected == 0) {
					throw new DAOException("The option was not found.");
				}
			}
		} catch (Exception e) {
			throw new DAOException("Error during updating votes.", e);
		}
	}


}