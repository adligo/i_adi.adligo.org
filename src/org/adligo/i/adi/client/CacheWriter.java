package org.adligo.i.adi.client;

import org.adligo.i.adi.client.models.CacheWriterToken;
import org.adligo.i.log.client.Log;
import org.adligo.i.log.client.LogFactory;


public class CacheWriter implements I_Invoker {
	private static final Log log = LogFactory.getLog(CacheWriter.class);
	private static final I_Invoker CLOCK = Registry.getInvoker(InvokerNames.CLOCK);
	protected static final CacheWriter INSTANCE = new CacheWriter();
	
	private CacheWriter() {}
	
	public Object invoke(Object valueObject) {
		if (valueObject instanceof CacheWriterToken) {
			CacheWriterToken token = (CacheWriterToken) valueObject;
			return invoke(token);
		} else {
			RuntimeException e = new RuntimeException(
					this.getClass().getName() + " takes a " +
					CacheWriterToken.class.getName() + 
					" and you passed it a " + valueObject);
			throw e;
		}
		
	}

	protected Boolean invoke(CacheWriterToken token) {
		if (token.getName() != null) {
			short policy = token.getSetPolicy();
			switch (policy) {
				case CacheWriterToken.SET_ALWAYS:
					set(token);
						return Boolean.TRUE;
				case CacheWriterToken.ADD_ONLY_IF_NOT_PRESENT:
					if (Cache.items.get(token.getName()) == null) {
						set(token);
							return Boolean.TRUE;
					} else  {
						return Boolean.FALSE;
					}
				case CacheWriterToken.REPLACE_ONLY_IF_PRESENT:
					if (Cache.items.get(token.getName()) != null) {
						set(token);
							return Boolean.TRUE;
					} else  {
						return Boolean.FALSE;
					}
				default:
					throw new RuntimeException("token type " + policy +
							" not currently supported");
			}
		} else {
			return Boolean.FALSE;
		}
	}

	protected void set(CacheWriterToken token) {
		synchronized (Cache.class) {
			Cache.items.put(token.getName(), token.getValue());
			Cache.itemsEditTimes.put(token.getName(), (Long) CLOCK.invoke(null));
		}
		if (log.isDebugEnabled()) {
			log.debug("cache is now " + token.getName() + " value " +
					Cache.items.get(token.getName()));
		}
	}

}
