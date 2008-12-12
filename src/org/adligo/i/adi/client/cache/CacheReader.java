package org.adligo.i.adi.client.cache;

import org.adligo.i.adi.client.I_Invoker;

public class CacheReader implements I_Invoker {

	public Object invoke(Object key) {
		return Cache.items.get(key);
	}

}
