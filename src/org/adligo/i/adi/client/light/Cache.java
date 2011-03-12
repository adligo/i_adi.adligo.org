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
public final class Cache {

	/**
	 * The key should use a pathlike structure
	 *  /package/name
	 *  
	 *  The system should define a list of items in a constants class
	 *  <String>,<Object>
	 *  
	 */
	static final I_Map items = MapFactory.create();

	private Cache() {};
	
}
