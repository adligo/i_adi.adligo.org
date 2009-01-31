package org.adligo.i.adi.client;


public class CacheReader implements I_Invoker {

	protected CacheReader() {}
	
	public Object invoke(Object key) {
		return Cache.items.get(key);
	}

}
