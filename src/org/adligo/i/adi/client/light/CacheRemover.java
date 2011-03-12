package org.adligo.i.adi.client.light;

import org.adligo.i.adi.client.I_Invoker;
import org.adligo.i.adi.client.models.CacheRemoverToken;
import org.adligo.i.adi.client.models.CacheValue;
import org.adligo.i.util.client.I_Iterator;

public final class CacheRemover implements I_Invoker {
	static final CacheRemover INSTANCE = new CacheRemover();
	
	private CacheRemover() {}
	
	public Object invoke(Object valueObject) {
		CacheRemoverToken token = (CacheRemoverToken) valueObject;
		return invoke(token);
	}

	protected Integer invoke(CacheRemoverToken token) {
		switch (token.getType()) {
			case CacheRemoverToken.REMOVE_LIST_TYPE:
					I_Iterator it = token.getKeys();
					while (it.hasNext()) {
						String key = (String) it.next();
						Cache.items.remove(key);
					}
				break;
			case CacheRemoverToken.SWEEP_ALL_TYPE:
				I_Iterator itVals = Cache.items.getValuesIterator();
				long staleDate = token.getStaleDate();
				while (itVals.hasNext()) {
					CacheValue val = (CacheValue) itVals.next(); 
					long putTime = val.getPutTime();
					if (staleDate <= putTime) {
						String key = val.getFullPath();
						Cache.items.remove(key);
					}
				}
				
				break;
			case CacheRemoverToken.GET_SIZE_TYPE:
					return new Integer(Cache.items.size());
			case CacheRemoverToken.GET_TIME_INDEX_SIZE_TYPE:
				return new Integer(Cache.items.size());
			default:
				throw new RuntimeException("token type " + token.getType() + " not currently supported");
		}
		return new Integer(1);
	}

}
