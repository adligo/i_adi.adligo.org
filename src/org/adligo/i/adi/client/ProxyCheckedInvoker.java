package org.adligo.i.adi.client;

import org.adligo.i.log.client.Log;
import org.adligo.i.log.client.LogFactory;

public class ProxyCheckedInvoker implements I_CheckedInvoker {
	private static final Log log = LogFactory.getLog(ProxyCheckedInvoker.class);
	private String name;
	private I_CheckedInvoker delegate;

	public ProxyCheckedInvoker(String name) {
		if (name == null) {
			Exception e = new NullPointerException();
			log.error("Null ProxyInvoker Name!", e);
		}
		this.name = name;
	}
	
	public ProxyCheckedInvoker(String name, I_CheckedInvoker p ) {
		this(name);
		delegate = p;
	}
	
	protected String getName() {
		return name;
	}
	
	public synchronized void setDelegate(I_CheckedInvoker p) {
		delegate = p;
	}
	public I_CheckedInvoker getDelegate() {
		return delegate;
	}
	
	public Object invoke(Object valueObject) throws InvocationException {
		if (delegate == null) {
			InvocationException e = new InvocationException("Proxy isn't initalized yet!");
			throw e;
		} else {
			return delegate.invoke(valueObject);
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
}
