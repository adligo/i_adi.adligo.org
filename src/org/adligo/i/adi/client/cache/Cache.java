package org.adligo.i.adi.client.cache;


import org.adligo.i.adi.client.I_Handler;
import org.adligo.i.util.client.I_Map;
import org.adligo.i.util.client.MapFactory;

public class Cache {
	/**
	 * The key should use a pathlike structure
	 *  /package/name
	 *  
	 *  The system should define a list of items in a constant file
	 *  
	 *  your platform should be initalized before this class is loaded
	 *  in j2se (j2ee) use a static block
	 *  the way log4j apache commons logging initalizes
	 */
	protected static final I_Map items = MapFactory.create();
	
	private Cache() {};
}
