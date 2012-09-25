package se.labs.itemcache;

/**
 * 
 * @author ne
 *
 * @param <T> The item to cache
 */
public interface ItemCache<T> {

	/**
	 * 
	 * @return The cached item
	 */
	T getItem();

	/**
	 * 
	 * @return {@code true} if reload is needed
	 */
	boolean isReloadNeeded();

	/**
	 * Forces the cache to reload next time it is called
	 */
	void invalidate();
}