package org.adligo.i.adi.shared.light;

import org.adligo.i.adi.shared.I_Invoker;
import org.adligo.i.adi.shared.InvokerNames;
import org.adligo.i.adi.shared.Registry;
import org.adligo.i.adi.shared.models.CacheValue;
import org.adligo.i.adi.shared.models.CacheWriterToken;
import org.adligo.i.log.shared.Log;
import org.adligo.i.log.shared.LogFactory;


public class CacheWriter implements I_Invoker {
	private static final Log log = LogFactory.getLog(CacheWriter.class);
	private static I_Invoker CLOCK = Registry.getInvoker(InvokerNames.CLOCK);

	public static I_Invoker getCLOCK() {
		return CLOCK;
	}

	static final CacheWriter INSTANCE = new CacheWriter();
	
	private CacheWriter() {}
	
	public Object invoke(Object valueObject) {
		try  {
			CacheWriterToken token = (CacheWriterToken) valueObject;
			return invoke(token);
		} catch (ClassCastException x) {
			throw new IllegalArgumentException(
					this.getClass().getName() + " takes a " +
					CacheWriterToken.class.getName() + 
					" and you passed it a " + valueObject);
		}
		
	}
 
	/**
	 * this is synchronized for JME only
	 * GWT removes all synchrnization (its single threaded)
	 * 
	 * @param token
	 * @return
	 */
	protected synchronized Boolean invoke(CacheWriterToken token) {
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
					throw new IllegalArgumentException("token type " + policy +
							" not currently supported");
			}
		} else {
			return Boolean.FALSE;
		}
	}

	protected void set(CacheWriterToken token) {
		Long time = (Long) CLOCK.invoke(null);
		CacheValue cv = new CacheValue(token.getName(), 
				time.longValue(), token.getValue());
		Cache.items.put(token.getName(), cv);
		
		if (log.isDebugEnabled()) {
			log.debug("cache is now " + token.getName() + " value " +
					Cache.items.get(token.getName()));
		}
	}


}
