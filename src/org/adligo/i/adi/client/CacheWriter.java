package org.adligo.i.adi.client;


public class CacheWriter implements I_Invoker {

	protected CacheWriter() {}
	
	public Object invoke(Object valueObject) {
		CacheEditToken token = (CacheEditToken) valueObject;
		if (token.getName() != null) {
			synchronized (this) {
				Cache.items.put(token.getName(), token.getValue());
			}
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
		
	}

}
