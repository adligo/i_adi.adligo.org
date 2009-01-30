package org.adligo.i.adi.client;

import org.adligo.i.log.client.Log;
import org.adligo.i.log.client.LogFactory;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * this class is used to have deferred initalization in the same way that
 * i_log works so that you always get a object back from the 
 * Registry.getInvoker method
 * 
 * @author scott
 *
 */
public class ProxyInvoker implements I_Invoker {
	private static final Log log = LogFactory.getLog(ProxyInvoker.class);
	private String name;
	private I_Invoker delegate;

	public ProxyInvoker(String name) {
		if (name == null) {
			Exception e = new NullPointerException();
			log.error("Null ProxyInvoker Name!", e);
		}
		this.name = name;
	}
	
	public ProxyInvoker(String name, I_Invoker p) {
		this(name);
		delegate = p;
	}
	
	public String getName() {
		return name;
	}
	
	public synchronized void setDelegate(I_Invoker p) {
		if (log.isDebugEnabled()) {
			log.debug("setting invoker " + p + " for ProxyInvoker " + name);
		}
		delegate = p;
	}
	
	public I_HandlerAsync getDelegate() {
		return delegate;
	}
	
	
	public void invoke(Object valueObject, AsyncCallback callback) {
		if (delegate == null) {
			Exception e = new Exception("Proxy for " + this.name + 
					" isn't initalized yet!");
			log.error("Proxy isn't initalized yet!", e);
			callback.onFailure(e);
		} else {
			delegate.invoke(valueObject, callback);
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ProxyInvoker [name=");
		sb.append(name);
		sb.append(",delegate=");
		sb.append(delegate);
		sb.append("]");
		return sb.toString();
		
	}

	public boolean isLocal() {
		if (delegate == null) {
			return false;
		}
		return delegate.isLocal();
	}
}
