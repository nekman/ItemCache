package se.labs.itemcache;

/**
 * 
 * @author ne
 *
 */
public class ItemCacheImpl<T> extends AbstractItemCache<T> {

	public ItemCacheImpl(final ItemCacheLoader<T> cacheLoader, final int secondsBetweenReload) {
		super(cacheLoader, secondsBetweenReload);
	}

	/**
	 * 
	 * @param cacheLoader - the cache loader
	 */
	public ItemCacheImpl(final ItemCacheLoader<T> cacheLoader) {
		this(cacheLoader, 600);
	}
}