package org.adligo.i.adi.client;

import org.adligo.i.util.client.I_Map;
import org.adligo.i.util.client.MapFactory;

/**
 * DO NOT USE ON THE SERVER!
 * 
 * this is a simplified JCache 
 * for jme and gwt (actual javascript portion), if your running on a server
 * you want JCache
 * http://java-source.net/open-source/cache-solutions/jcache
 * 
 * however you may want to still use the Registry (GRegistry) 
 * and invokers (I_GInvokers) to wrap it in case you want to replace it in unit tests.
 * 
 * note edits to the maps in this class should be synchronized on this class
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
	protected static final I_Map items = MapFactory.create();
	/**
	 * this contains the times that the cache was edited for each key
	 * <String>,<Long>
	 */
	protected static final I_Map itemsEditTimes = MapFactory.create();
	
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
