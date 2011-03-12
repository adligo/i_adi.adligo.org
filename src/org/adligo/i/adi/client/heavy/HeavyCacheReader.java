package org.adligo.i.adi.client.heavy;

import org.adligo.i.adi.client.I_Invoker;
import org.adligo.i.adi.client.models.CacheValue;



public class HeavyCacheReader implements I_Invoker {
	public static final HeavyCacheReader INSTANCE = new HeavyCacheReader();
	
	protected HeavyCacheReader() {}
	
	public Object invoke(Object key) {
		CacheValue cv = (CacheValue) HeavyCache.items.get(key);
		if (cv == null) {
			return null;
		}
		return cv.getValue();
	}

}
