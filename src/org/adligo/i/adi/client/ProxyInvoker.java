package org.adligo.i.adi.client;

import org.adligo.i.log.client.Log;
import org.adligo.i.log.client.LogFactory;

public class ProxyInvoker implements I_Invoker {
	private static final Log log = LogFactory.getLog(ProxyInvoker.class);
	private String name;
	private I_Invoker delegate;

	public ProxyInvoker(String name) {
		if (name == null) {
			Exception e = new NullPointerException();
			e.fillInStackTrace();
			log.error("Null ProxyInvoker Name!", e);
		}
		this.name = name;
	}
	
	public ProxyInvoker(String name, I_Invoker p) {
		this(name);
		delegate = p;
	}
	
	protected String getName() {
		return name;
	}
	
	public synchronized void setDelegate(I_Invoker p) {
		delegate = p;
	}
	public I_Invoker getDelegate() {
		return delegate;
	}
	
	
	public Object invoke(Object valueObject) {
		if (delegate == null) {
			Exception e = new Exception();
			e.fillInStackTrace();
			log.error("Proxy isn't initalized yet!", e);
			return null;
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
