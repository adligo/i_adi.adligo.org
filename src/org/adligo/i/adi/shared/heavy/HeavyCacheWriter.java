package org.adligo.i.adi.shared.heavy;

import org.adligo.i.adi.shared.I_Invoker;
import org.adligo.i.adi.shared.InvokerNames;
import org.adligo.i.adi.shared.Registry;
import org.adligo.i.adi.shared.models.CacheValue;
import org.adligo.i.adi.shared.models.CacheWriterToken;
import org.adligo.i.log.shared.Log;
import org.adligo.i.log.shared.LogFactory;


public class HeavyCacheWriter implements I_Invoker {
	private static final Log log = LogFactory.getLog(HeavyCacheWriter.class);
	private static I_Invoker CLOCK = Registry.getInvoker(InvokerNames.CLOCK);
	static final HeavyCacheWriter INSTANCE = new HeavyCacheWriter();
	
	private HeavyCacheWriter() {}
	
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

	protected Boolean invoke(CacheWriterToken token) {
		if (token.getName() != null) {
			short policy = token.getSetPolicy();
			switch (policy) {
				case CacheWriterToken.SET_ALWAYS:
					set(token);
						return Boolean.TRUE;
				case CacheWriterToken.ADD_ONLY_IF_NOT_PRESENT:
					if (HeavyCache.items.get(token.getName()) == null) {
						set(token);
							return Boolean.TRUE;
					} else  {
						return Boolean.FALSE;
					}
				case CacheWriterToken.REPLACE_ONLY_IF_PRESENT:
					if (HeavyCache.items.get(token.getName()) != null) {
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
		
		CacheValue cv = new CacheValue(token.getName(), time.longValue(), token.getValue());
		
		HeavyCache.items.put(token.getName(), cv);
		HeavyCache.timeIndex.put(cv.getTimeCrunchString(), cv);
		
		if (log.isDebugEnabled()) {
			log.debug("cache is now " + token.getName() + " value " +
					HeavyCache.items.get(token.getName()));
		}
	}


	public static I_Invoker getCLOCK() {
		return CLOCK;
	}


}
