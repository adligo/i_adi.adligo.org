package org.adligo.i.adi.client.heavy;

import org.adligo.i.adi.client.I_Invoker;
import org.adligo.i.adi.client.models.CacheRemoverToken;
import org.adligo.i.adi.client.models.CacheValue;
import org.adligo.i.util.client.I_Iterator;

public class HeavyCacheRemover implements I_Invoker {
	static final HeavyCacheRemover INSTANCE = new HeavyCacheRemover();
	
	private HeavyCacheRemover() {}
	
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
						HeavyCache.items.remove(key);
					}
				break;
			case CacheRemoverToken.SWEEP_ALL_TYPE:
				long staleTime = token.getStaleDate();
				CacheValue cv = new CacheValue("", staleTime,"");
				long reqMin = cv.getTopTimeCrunch();
				I_Iterator itKeys = HeavyCache.timeIndex.topKeys();
				while (itKeys.hasNext()) {
					String key = (String) itKeys.next();
					long keyMin = cv.getTopTimeFromCrunchString(key);
					
					if (keyMin < reqMin) {
						I_Iterator subValueIt = HeavyCache.timeIndex.subValues(key);
						HeavyCache.timeIndex.removeChildMap(key);
						while (subValueIt.hasNext()) {
							CacheValue toRemove = (CacheValue) subValueIt.next();
							checkRemoveByTime(staleTime, toRemove);
						}
					} else if (keyMin == reqMin) {
						I_Iterator subValueIt = HeavyCache.timeIndex.subValues(key);
						while (subValueIt.hasNext()) {
							CacheValue toRemove = (CacheValue) subValueIt.next();
							checkRemoveByTime(staleTime, toRemove);
						}
					}
				}
				break;
			case CacheRemoverToken.GET_SIZE_TYPE:
				return new Integer(HeavyCache.items.size());
			case CacheRemoverToken.GET_TIME_INDEX_SIZE_TYPE:
				return new Integer(HeavyCache.timeIndex.size());
			default:
				throw new RuntimeException("token type " + token.getType() + " not currently supported");
		}
		return new Integer(1);
	}

	private void checkRemoveByTime(long staleTime, CacheValue toRemove) {
		if (toRemove != null) {
			String fullPath = toRemove.getFullPath();
			CacheValue val = (CacheValue) HeavyCache.items.get(fullPath);
			if (val == null) {
				//it was already removed
			} else {
				//another thread may have written to this
				// reference name address since it's initial insert
				if (val.getPutTime() <= staleTime) {
					HeavyCache.items.remove(toRemove.getFullPath());
				}
			}
		}
	}


}
