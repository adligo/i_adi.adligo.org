package org.adligo.i.adi.client;

import org.adligo.i.adi.client.models.CacheRemoverToken;
import org.adligo.i.util.client.I_Iterator;

public class CacheRemover implements I_Invoker {
	protected static final CacheRemover INSTANCE = new CacheRemover();
	
	protected CacheRemover() {}
	
	public Object invoke(Object valueObject) {
		CacheRemoverToken token = (CacheRemoverToken) valueObject;
		return invoke(token);
	}

	protected Boolean invoke(CacheRemoverToken token) {
		switch (token.getType()) {
			case CacheRemoverToken.REMOVE_LIST_TYPE:
					I_Iterator it = token.getKeys();
					synchronized (Cache.class) {
						while (it.hasNext()) {
							String key = (String) it.next();
							Cache.items.remove(key);
							Cache.itemsEditTimes.remove(key);
						}
					}
				break;
			case CacheRemoverToken.SWEEP_ALL_TYPE:
				I_Iterator itKeys = Cache.itemsEditTimes.keys();
				String key = (String) itKeys.next();
				Long time = (Long) Cache.itemsEditTimes.get(key);
				if (time != null) {
					long timeLong = time.longValue();
					if (token.getStaleDate() <= timeLong) {
						synchronized (Cache.class) {
							Cache.items.remove(key);
							Cache.itemsEditTimes.remove(key);
						}
					}
				}
				break;
			default:
				throw new RuntimeException("token type " + token.getType() + " not currently supported");
		}
		return Boolean.TRUE;
	}

}
