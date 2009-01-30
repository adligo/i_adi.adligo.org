package org.adligo.i.adi.client.cache;

import org.adligo.i.adi.client.I_Invoker;

public class CacheWriter implements I_Invoker {

	public Object invoke(Object valueObject) {
		CacheEditToken token = (CacheEditToken) valueObject;
		if (token.getName() != null) {
			synchronized (this) {
				Cache.items.put(token.getName(), token.getValue());
			}
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
		
	}

}
