package hr.fer.zemris.java.hw15.dao;

import java.util.List;

import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * An interface to access the data layer.
 * 
 * @author Patrik
 *
 */
public interface DAO {

	/**
	 * Returns the blog entry with given id or {@code null} if it does not exist.
	 * 
	 * @param id id
	 * @return the blog entry with given id or {@code null} if it does not exist
	 * @throws DAOException in case of an error during data access
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;
	
	/**
	 * Returns the user with given nick or {@code null} if it does not exist.
	 * 
	 * @param nick nickname
	 * @return the user with given nick or {@code null} if it does not exist
	 * @throws DAOException in case of an error during data access
	 */
	public BlogUser getBlogUser(String nick) throws DAOException;
	
	/**
	 * Returns the list of all blog users.
	 * 
	 * @return the list of all blog users
	 * @throws DAOException in case of an error during data access
	 */
	public List<BlogUser> getAllBlogUsers() throws DAOException;

	/**
	 * Saves the given blog entry.
	 * @param blogEntry blog entry
	 */
	public void saveBlogEntry(BlogEntry blogEntry);
	
	/**
	 * Saves the blog user.
	 * @param blogUser blog user
	 */
	public void saveBlogUser(BlogUser blogUser);

	/**
	 * Saves the blog comment.
	 * @param comment blog comment
	 */
	public void saveBlogComment(BlogComment comment);
	
}