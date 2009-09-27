package org.adligo.i.adi.client;

import org.adligo.i.adi.client.models.CacheKey;
import org.adligo.i.adi.client.models.CacheWriterToken;


public class CacheWriter implements I_Invoker {

	protected CacheWriter() {}
	
	public Object invoke(Object valueObject) {
		if (valueObject instanceof CacheWriterToken) {
			CacheWriterToken token = (CacheWriterToken) valueObject;
			if (token.getName() != null) {
				synchronized (this) {
					Cache.items.put(new CacheKey(token.getName()), token.getValue());
				}
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		} else {
			RuntimeException e = new RuntimeException(
					this.getClass().getName() + " takes a " +
					CacheWriterToken.class.getName() + 
					" and you passed it a " + valueObject);
			throw e;
		}
		
	}

}
