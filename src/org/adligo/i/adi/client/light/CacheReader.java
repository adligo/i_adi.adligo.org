package org.adligo.i.adi.client.light;

import org.adligo.i.adi.client.I_Invoker;



public class CacheReader implements I_Invoker {
	public static final CacheReader INSTANCE = new CacheReader();
	
	protected CacheReader() {}
	
	public Object invoke(Object key) {
		return Cache.items.get(key);
	}

}
