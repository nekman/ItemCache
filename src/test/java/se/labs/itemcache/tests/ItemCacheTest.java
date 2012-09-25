package se.labs.itemcache.tests;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import se.labs.itemcache.ItemCache;
import se.labs.itemcache.ItemCacheImpl;
import se.labs.itemcache.ItemCacheLoader;

public class ItemCacheTest {

	@Test
	public void shouldReloadWhenExpired() throws Exception {
		@SuppressWarnings("unchecked")
		ItemCacheLoader<String> loader = mock(ItemCacheLoader.class);
		when(loader.reload()).thenReturn("1");

		ItemCache<String> stringCache = new ItemCacheImpl<String>(loader, 1);
		assertThat(stringCache.getItem(), is("1"));

		// Next time the cache reloads, return "2".
		when(loader.reload()).thenReturn("2");
		// Sleep two seconds, to make sure that the cache gets reloaded.
		Thread.sleep(2000);

		assertThat(stringCache.getItem(), is("2"));
	}

	@Test
	public void shouldReloadWhenInvalidated() throws Exception {
		@SuppressWarnings("unchecked")
		ItemCacheLoader<Integer> loader = mock(ItemCacheLoader.class);
		when(loader.reload()).thenReturn(1);

		ItemCache<Integer> intCache = new ItemCacheImpl<Integer>(loader);
		assertThat(intCache.getItem(), is(1));

		// Next time the cache reloads, return 2.
		when(loader.reload()).thenReturn(2);
		// Call invalidate, to make sure that the cache gets reloaded.
		intCache.invalidate();

		assertThat(intCache.getItem(), is(2));
	}
}
