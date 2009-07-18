package org.adligo.i.adi.client;


public class CacheWriter implements I_Invoker {

	protected CacheWriter() {}
	
	public Object invoke(Object valueObject) {
		if (valueObject instanceof CacheEditToken) {
			CacheEditToken token = (CacheEditToken) valueObject;
			if (token.getName() != null) {
				synchronized (this) {
					Cache.items.put(token.getName(), token.getValue());
				}
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		} else {
			RuntimeException e = new RuntimeException(
					this.getClass().getName() + " takes a " +
					CacheEditToken.class.getName() + 
					" and you passed it a " + valueObject);
			throw e;
		}
		
	}

}
