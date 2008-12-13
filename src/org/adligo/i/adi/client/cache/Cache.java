package org.adligo.i.adi.client.cache;


import org.adligo.i.adi.client.I_Invoker;
import org.adligo.i.util.client.I_Map;
import org.adligo.i.util.client.MapFactory;

public class Cache {
	/**
	 * these are the traditional constants of the cache reader and writer
	 */
	public static final String CACHE_READER = "org.adligo.i.adi.client.cache.reader";
	public static final String CACHE_WRITER = "org.adligo.i.adi.client.cache.writer";
	
	/**
	 * The key should use a pathlike structure
	 *  /package/name
	 *  
	 *  The system should define a list of items in a constant file
	 *  
	 */
	protected static final I_Map items = MapFactory.create();
	
	private Cache() {};
}
