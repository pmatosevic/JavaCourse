package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.hw15.dao.DAOException;

/**
 * Class that stores and provides {@link EntityManager} to and from the thread local map.
 * 
 * @author Patrik
 *
 */
public class JPAEMProvider {

	/**
	 * A thread local map of managers
	 */
	private static ThreadLocal<EntityManager> locals = new ThreadLocal<>();

	/**
	 * Returns the manager that the current thread can use
	 * 
	 * @return entity manager
	 */
	public static EntityManager getEntityManager() {
		EntityManager em = locals.get();
		if(em==null) {
			em = JPAEMFProvider.getEmf().createEntityManager();
			em.getTransaction().begin();
			locals.set(em);
		}
		return em;
	}

	/**
	 * Commits all the changes and closes the entity manager.
	 * 
	 * @throws DAOException in case of an error
	 */
	public static void close() throws DAOException {
		EntityManager em = locals.get();
		if(em==null) {
			return;
		}
		DAOException dex = null;
		try {
			em.getTransaction().commit();
		} catch(Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			em.close();
		} catch(Exception ex) {
			if(dex!=null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if(dex!=null) throw dex;
	}
	
}