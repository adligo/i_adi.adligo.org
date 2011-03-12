package org.adligo.i.adi.client.heavy;

import org.adligo.i.adi.client.I_Invoker;
import org.adligo.i.adi.client.models.CacheRemoverToken;
import org.adligo.i.adi.client.models.CacheValue;
import org.adligo.i.util.client.I_Iterator;

public class HeavyCacheRemover implements I_Invoker {
	public static final HeavyCacheRemover INSTANCE = new HeavyCacheRemover();
	
	protected HeavyCacheRemover() {}
	
	public Object invoke(Object valueObject) {
		CacheRemoverToken token = (CacheRemoverToken) valueObject;
		return invoke(token);
	}

	protected Boolean invoke(CacheRemoverToken token) {
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
						I_Iterator subKeyIt = HeavyCache.timeIndex.subKeys(key);
						while (subKeyIt.hasNext()) {
							String toRemoveKey = getTotalToRemoveKey(key,subKeyIt);
							CacheValue toRemove = (CacheValue) HeavyCache.timeIndex.get(toRemoveKey);
							if (toRemove != null) {
								HeavyCache.items.remove(toRemove.getFullPath());
								HeavyCache.timeIndex.remove(toRemove.getTimeCrunchString());
							}
						}
					} else if (keyMin == reqMin) {
						I_Iterator subKeyIt = HeavyCache.timeIndex.subKeys(key);
						while (subKeyIt.hasNext()) {
							String toRemoveKey = getTotalToRemoveKey(key,subKeyIt);
							CacheValue toRemove = (CacheValue) HeavyCache.timeIndex.get(toRemoveKey);
							if (toRemove != null) {
								if (toRemove.getPutTime() <= staleTime) {
									HeavyCache.items.remove(toRemove.getFullPath());
									HeavyCache.timeIndex.remove(toRemove.getTimeCrunchString());
								}
							}
						}
					}
				}
				break;
			default:
				throw new RuntimeException("token type " + token.getType() + " not currently supported");
		}
		return Boolean.TRUE;
	}

	private String getTotalToRemoveKey(String key, I_Iterator subKeyIt) {
		String toRemoveKey = (String) subKeyIt.next();
		StringBuffer sb = new StringBuffer();
		sb.append(key);
		sb.append(toRemoveKey);
		return sb.toString();
	}

}
