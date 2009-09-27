package org.adligo.i.adi.client;

import org.adligo.i.adi.client.models.CacheKey;
import org.adligo.i.adi.client.models.CacheRemoverToken;
import org.adligo.i.util.client.I_Iterator;

public class CacheRemover implements I_Invoker {

	public Object invoke(Object valueObject) {
		CacheRemoverToken token = (CacheRemoverToken) valueObject;
		switch (token.getType()) {
			case CacheRemoverToken.REMOVE_LIST_TYPE:
					I_Iterator it = token.getKeys();
					while (it.hasNext()) {
						Cache.items.remove(it.next());
					}
				break;
			case CacheRemoverToken.SWEEP_ALL_TYPE:
					// stop the world and collect
					I_Iterator it2 = Cache.items.getIterator();
					while (it2.hasNext()) {
						CacheKey key = (CacheKey) it2.next();
						if (key.shouldEvict()) {
							Cache.items.remove(key);
						}
					}
				break;
		}
		return Boolean.TRUE;
	}

}
