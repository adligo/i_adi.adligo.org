package org.adligo.i.adi.client.cache;

import org.adligo.i.adi.client.I_Invoker;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * this is added as a I_InvokerAsync so that you can 
 * have the endpoint of a proxy be the cache
 * 
 * @author scott
 *
 */
public class CacheReader implements I_Invoker {

	public void invoke(Object key, AsyncCallback callback) {
		Object value = Cache.items.get(key);
		callback.onSuccess(value);
	}

	public boolean isLocal() {
		return true;
	}

}
