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
	 * @return Gets the item in the cache.
	 */
	T getItem();

	/**
	 * Invalidates the cache.
	 * Force a reload next time it is called.
	 */
	void invalidate();
}