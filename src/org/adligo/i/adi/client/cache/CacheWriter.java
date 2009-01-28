package org.adligo.i.adi.client.cache;

import org.adligo.i.adi.client.I_Invoker;
import org.adligo.i.adi.client.I_InvokerAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class CacheWriter implements I_InvokerAsync {

	public void invoke(Object valueObject, AsyncCallback callback) {
		CacheEditToken token = (CacheEditToken) valueObject;
		synchronized (this) {
			Cache.items.put(token.getName(), token.getValue());
		}
		
	}

}
