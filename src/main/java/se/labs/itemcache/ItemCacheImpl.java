package se.labs.itemcache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author ne
 *
 */
public class ItemCacheImpl<T> implements ItemCache<T> {

	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(ItemCacheImpl.class);

	private final ItemCacheLoader<T> cacheLoader;
	/**
	 * Time in ms between each reload
	 */
	private final Long timeBetweenReload;

	/**
	 * When the cache was last updated 
	 */
	private Long lastUpdated;

	private T item;

	/**
	 * 
	 * @param cacheLoader - the cache loader 
	 * @param secondsBetweenReload - the seconds between each reload of the cache
	 * 
	 */
	public ItemCacheImpl(final ItemCacheLoader<T> cacheLoader, final int secondsBetweenReload) {
		if (cacheLoader == null) {
			throw new IllegalArgumentException("cacheloader");
		}

		this.cacheLoader = cacheLoader;
		this.timeBetweenReload = secondsBetweenReload * 1000L;
	}

	/**
	 * 
	 * @param cacheLoader - the cache loader
	 */
	public ItemCacheImpl(final ItemCacheLoader<T> cacheLoader) {
		this(cacheLoader, 600);
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
	 * @see ItemCache#isReloadNeeded() 
	 */
	public boolean isReloadNeeded() {
		if (item == null) {
			return true;
		}

		return System.currentTimeMillis() - lastUpdated > timeBetweenReload;
	}

	public void invalidate() {
		item = null;
	}
}