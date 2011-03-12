package org.adligo.i.adi.client.heavy;

import org.adligo.i.adi.client.models.CacheValue;
import org.adligo.i.adi.client.models.ReferenceDomain;
import org.adligo.i.util.client.HashCollection;
import org.adligo.i.util.client.I_Map;
import org.adligo.i.util.client.MapFactory;

/**
 * This is ready for server usage.
 * 
 * this is a simplified RAM only JCache 
 * for jme and gwt (actual javascript portion), if your running on a server
 * you can use this impl (put a bunch of jars in your server/lib (i_log, i_util, jse_util, adi)
 * 
 * if your running on GAE you want JCache
 * http://java-source.net/open-source/cache-solutions/jcache
 * adligo doesn't provide a JCache Invoker
 * 
 * 
 * @author scott
 *
 */
public final class HeavyCache {

	/**
	 * The key should use a pathlike structure
	 *  /package/name
	 *  
	 *  The system should define a list of items in a constants class
	 *  <String>,<CacheValue>
	 *  
	 */
	 static final ReferenceDomain items = new ReferenceDomain();
	/**
	 * holds the CacheValue's index by the time they were put into the cache,
	 * see getTimeCrunchString in CacheValue
	 * 
	 */
	protected static final ReferenceDomain timeIndex = new ReferenceDomain();
	
	private HeavyCache() {};
	
}
