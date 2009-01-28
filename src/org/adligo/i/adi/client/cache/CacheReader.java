package org.adligo.i.adi.client.cache;

import org.adligo.i.adi.client.I_Invoker;
import org.adligo.i.adi.client.I_InvokerAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * this is added as a I_InvokerAsync so that you can 
 * have the endpoint of a proxy be the cache
 * 
 * @author scott
 *
 */
public class CacheReader implements I_InvokerAsync {

	public void invoke(Object key, AsyncCallback callback) {
		Object value = Cache.items.get(key);
		callback.onSuccess(value);
	}

}
