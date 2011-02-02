package org.adligo.i.adi.client;

import org.adligo.i.util.client.I_Map;
import org.adligo.i.util.client.MapFactory;

/**
 * DO NOT USE ON THE Server!
 * 
 * This is a similar concept to the Cache class in this package
 * but pertains to memory items that should not get removed automaticly (CacheRemover)
 * by a cleanup thread.
 * 
 * For a server side impl use something like
 * http://tyrex.sourceforge.net/naming.html
 * 
 * So a server might have a JDBC Connection Factory stored in long term RAM
 * that would only be removed by undeploying the connection factory descriptor (xml file or something).
 * 
 * This implementation would be effectively used on 'Clients'(gwt in the browser, jme, jse applets, command line programs exc)
 * to store objects that should be shared (like Cached object) 
 * but are required for long term successful operation
 * a good example would be a authenticated Jaas Subject (User object), database connection (or pool factory),
 * WebSocket connection, exc
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
	 *  <String>,<Object>
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
