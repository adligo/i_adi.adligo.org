package org.adligo.i.adi.client;

import org.adligo.i.log.client.Log;
import org.adligo.i.log.client.LogFactory;


public class CacheReader implements I_Invoker {
	
	protected CacheReader() {}
	
	public Object invoke(Object key) {
		return Cache.items.get(key);
	}

}
