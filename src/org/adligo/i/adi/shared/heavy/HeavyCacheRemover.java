package org.adligo.i.adi.shared.heavy;

import org.adligo.i.adi.shared.I_Invoker;
import org.adligo.i.adi.shared.models.CacheRemoverToken;
import org.adligo.i.adi.shared.models.CacheValue;
import org.adligo.i.util.shared.I_Iterator;

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
							if (checkRemoveByTime(staleTime, toRemove)) {
								HeavyCache.timeIndex.remove(toRemove.getFullPath());
							}
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

	/**
	 * @param staleTime
	 * @param toRemove
	 * @return true if this method removed it
	 *    or if it was already removed
	 */
	private boolean checkRemoveByTime(long staleTime, CacheValue toRemove) {
		if (toRemove != null) {
			String fullPath = toRemove.getFullPath();
			CacheValue val = (CacheValue) HeavyCache.items.get(fullPath);
			if (val == null) {
				//it was already removed
				return true;
			} else {
				//another thread may have written to this
				// reference name address since it's initial insert
				if (val.getPutTime() <= staleTime) {
					HeavyCache.items.remove(toRemove.getFullPath());
					return true;
				}
			}
		}
		return false;
	}


}
