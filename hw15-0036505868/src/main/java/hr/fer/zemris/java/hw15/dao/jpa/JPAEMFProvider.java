package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * A class that provides access to the global {@link EntityManagerFactory}.
 * 
 * @author Patrik
 *
 */
public class JPAEMFProvider {

	/**
	 * The global factory
	 */
	public static EntityManagerFactory emf;
	
	/**
	 * Returns the entity factory manager.
	 * @return the entity factory manager
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	/**
	 * Sets the the entity factory manager.
	 * @param emf the entity factory manager
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}