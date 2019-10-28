package hr.fer.zemris.java.hw16.model;

/**
 * Provides access to a {@link ImageInfoDB} that knows information about images.
 * 
 * @author Patrik
 *
 */
public class ImageInfoProvider {

	/**
	 * The actual provider
	 */
	private static DefaultImageInfoDB provider;
	
	/**
	 * Sets the provider
	 * @param provider the provider
	 */
	public static void setProvider(DefaultImageInfoDB provider) {
		ImageInfoProvider.provider = provider;
	}
	
	/**
	 * Gets the ptovider
	 * @return the provider
	 */
	public static DefaultImageInfoDB getProvider() {
		return provider;
	}
	
}
