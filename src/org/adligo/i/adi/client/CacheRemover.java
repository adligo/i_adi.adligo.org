package org.adligo.i.adi.client;

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
			default:
					throw new RuntimeException(token.getType() + " not yet supported");
		}
		return Boolean.TRUE;
	}

}
