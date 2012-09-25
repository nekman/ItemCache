package se.labs.itemcache;

/**
 * @author ne
 * 
 */
public interface ItemCacheLoader<T> {

	/**
	 * 
	 * @return Performs a reload and returns the result.
	 */
	T reload();
}