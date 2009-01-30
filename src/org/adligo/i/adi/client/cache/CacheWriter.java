package org.adligo.i.adi.client.cache;

import org.adligo.i.adi.client.I_Invoker;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class CacheWriter implements I_Invoker {

	public void invoke(Object valueObject, AsyncCallback callback) {
		CacheEditToken token = (CacheEditToken) valueObject;
		synchronized (this) {
			Cache.items.put(token.getName(), token.getValue());
		}
		
	}

	public boolean isLocal() {
		return true;
	}
}
