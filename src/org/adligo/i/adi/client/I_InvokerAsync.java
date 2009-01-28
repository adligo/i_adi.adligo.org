package org.adligo.i.adi.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;

public interface I_InvokerAsync {
	public void invoke(Object valueObject, AsyncCallback callback);
}
