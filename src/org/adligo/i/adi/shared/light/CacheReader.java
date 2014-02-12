package org.adligo.i.adi.shared.light;

import org.adligo.i.adi.shared.I_Invoker;
import org.adligo.i.adi.shared.models.CacheValue;



public final class CacheReader implements I_Invoker {
	static final CacheReader INSTANCE = new CacheReader();
	
	protected CacheReader() {}
	
	public Object invoke(Object key) {
		CacheValue cv = (CacheValue) Cache.items.get(key);
		if (cv == null) {
			return null;
		}
		return cv.getValue();
	}

}
