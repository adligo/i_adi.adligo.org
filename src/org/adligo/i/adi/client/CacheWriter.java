package org.adligo.i.adi.client;

import org.adligo.i.adi.client.models.CacheWriterToken;
import org.adligo.i.log.client.Log;
import org.adligo.i.log.client.LogFactory;


public class CacheWriter implements I_Invoker {
	private static final Log log = LogFactory.getLog(CacheWriter.class);
	
	
	protected CacheWriter() {}
	
	public Object invoke(Object valueObject) {
		if (valueObject instanceof CacheWriterToken) {
			CacheWriterToken token = (CacheWriterToken) valueObject;
			if (token.getName() != null) {
				synchronized (this) {
					Cache.items.put(token.getName(), token.getValue());
					Cache.itemsEditTimes.put(token.getName(), new Long(System.currentTimeMillis()));
				}
				if (log.isDebugEnabled()) {
					log.debug("cache is now " + token.getName() + " value " +
							Cache.items.get(token.getName()));
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
