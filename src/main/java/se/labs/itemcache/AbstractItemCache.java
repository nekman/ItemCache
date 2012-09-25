package se.labs.itemcache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author ne
 *
 * @param <T> The item to cache
 */
public abstract class AbstractItemCache<T> implements ItemCache<T> {
	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(AbstractItemCache.class);

	/**
	 * The cache loader
	 */
	protected final ItemCacheLoader<T> cacheLoader;
	/**
	 * Time in ms between each reload
	 */
	protected final Long timeBetweenReload;

	/**
	 * When the cache was last updated 
	 */
	protected Long lastUpdated;

	protected T item;

	/**
	 * 
	 * @param cacheLoader - the cache loader 
	 * @param secondsBetweenReload - the seconds between each reload of the cache
	 * 
	 */
	public AbstractItemCache(final ItemCacheLoader<T> cacheLoader, final int secondsBetweenReload) {
		if (cacheLoader == null) {
			throw new IllegalArgumentException("cacheloader");
		}

		this.cacheLoader = cacheLoader;
		this.timeBetweenReload = secondsBetweenReload * 1000L;
	}

	/**
	 * @see ItemCache#getItem() 
	 */
	public T getItem() {
		// Check if a reload of the cache is needed, if so,
		// get the item from the cacheloader and update the time stamp.
		if (isReloadNeeded()) {
			synchronized (this) {
				if (isReloadNeeded()) {
					item = cacheLoader.reload();
					lastUpdated = System.currentTimeMillis();

					logger.info("Cache was reloaded.");
				}
			}
		}

		return item;
	}

	/**
	 * @see ItemCache#invalidate() 
	 */
	public void invalidate() {
		item = null;
	}

	/**
	 * 
	 * @return <code>true</code> if the cache needs to be reloaded next time {@link ItemCache#getItem()} is called.
	 */
	protected boolean isReloadNeeded() {
		if (item == null) {
			return true;
		}

		return System.currentTimeMillis() - lastUpdated > timeBetweenReload;
	}
}
