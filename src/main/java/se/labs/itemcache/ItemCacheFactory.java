package se.labs.itemcache;

/**
 * 
 * @author ne
 *
 */
public class ItemCacheFactory {
	/**
	 * 
	 * @param cacheLoader
	 * @param secondsBetweenReload
	 * @return
	 */
	public static <T> ItemCache<T> create(final ItemCacheLoader<T> cacheLoader, final int secondsBetweenReload) {
		return new ItemCacheImpl<T>(cacheLoader, secondsBetweenReload);
	}

	/**
	 * 
	 * @param cacheLoader
	 * @return
	 */
	public static <T> ItemCache<T> create(final ItemCacheLoader<T> cacheLoader) {
		return create(cacheLoader, 600);
	}
}