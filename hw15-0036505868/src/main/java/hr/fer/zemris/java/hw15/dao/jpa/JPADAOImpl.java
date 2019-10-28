package hr.fer.zemris.java.hw15.dao.jpa;

import java.util.List;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Inplementation of the {@link DAO} interface using SQL database access.
 * @author Patrik
 *
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}
	
	@Override
	public BlogUser getBlogUser(String nick) throws DAOException {
		List<BlogUser> blogUser = JPAEMProvider.getEntityManager()
				.createNamedQuery("BlogUser.queryNick", BlogUser.class)
				.setParameter("nick", nick)
				.getResultList();
		return blogUser.size() == 0 ? null : blogUser.get(0);
	}

	@Override
	public List<BlogUser> getAllBlogUsers() throws DAOException {
		List<BlogUser> blogUsers = JPAEMProvider.getEntityManager()
				.createNamedQuery("BlogUser.queryAll", BlogUser.class)
				.getResultList();
		return blogUsers;
	}

	@Override
	public void saveBlogEntry(BlogEntry blogEntry) {
		JPAEMProvider.getEntityManager().persist(blogEntry);
	}

	@Override
	public void saveBlogUser(BlogUser blogUser) {
		JPAEMProvider.getEntityManager().persist(blogUser);
	}

	@Override
	public void saveBlogComment(BlogComment comment) {
		JPAEMProvider.getEntityManager().persist(comment);
	}
	

}