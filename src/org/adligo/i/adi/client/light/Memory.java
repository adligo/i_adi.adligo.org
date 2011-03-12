package org.adligo.i.adi.client.light;

import org.adligo.i.util.client.I_Map;
import org.adligo.i.util.client.MapFactory;

/**
 * 
 * This is a similar concept to the Cache class in this package
 * but pertains to memory items that should not get removed automatically (CacheRemover)
 * by a cleanup thread.
 * 
 * This implementation would be effectively used on 'Clients'(gwt in the browser, jme, jse applets, command line programs exc)
 * to store objects that should be shared (like Cached object) 
 * but are required for long term successful operation
 * a good example would be a  WebSocket connection
 * or Authenticated User 
 *  
 * @author scott
 *
 */
public class Memory {
	/**
	 * The key should use a pathlike structure
	 *  /package/name
	 *  
	 *  The system should define a list of items in a constants class
	 *  <String>,<MemoryValue>
	 *  
	 */
	protected static final I_Map items = MapFactory.create();

	private Memory() {};
	
	/**
	 * for the CacheTest only do not use (use the Registry api)
	 * @param key
	 * @return
	 */
	public static Object getItem(String key) {
		return items.get(key);
	}
}
