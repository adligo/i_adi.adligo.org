package org.adligo.i.adi.client.light;

import org.adligo.i.util.client.I_Map;
import org.adligo.i.util.client.MapFactory;

/**
 * 
 * this is a simplified Cache 
 * for jme and gwt (actual javascript portion)
 * 
 * @author scott
 *
 */
public class Cache {

	/**
	 * The key should use a pathlike structure
	 *  /package/name
	 *  
	 *  The system should define a list of items in a constants class
	 *  <String>,<Object>
	 *  
	 */
	protected static final I_Map items = MapFactory.createSync();
	/**
	 * this contains the times that the cache was edited for each key
	 * <String>,<Long>
	 */
	protected static final I_Map itemsEditTimes = MapFactory.createSync();
	
	private Cache() {};
	
	/**
	 * for the CacheTest only do not use (use the Registry api)
	 * @param key
	 * @return
	 */
	public static Object getItem(String key) {
		return items.get(key);
	}

	/**
	 * for the CacheTest only do not use (use the Registry api)
	 * @param key
	 * @return
	 */
	public static Long getTime(String key) {
		return (Long) itemsEditTimes.get(key);
	}
	
}