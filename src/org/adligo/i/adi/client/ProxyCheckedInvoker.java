package org.adligo.i.adi.client;

import org.adligo.i.log.client.Log;
import org.adligo.i.log.client.LogFactory;
import org.adligo.i.util.client.ArrayCollection;
import org.adligo.i.util.client.I_Iterator;

public class ProxyCheckedInvoker implements I_CheckedInvoker {
	private static final Log log = LogFactory.getLog(ProxyCheckedInvoker.class);
	private static final ArrayCollection preInitInvokers = new ArrayCollection();
	
	public static ProxyCheckedInvoker getInstance(String name) {
		return getInstance(name, null);
	}
	
	public static synchronized ProxyCheckedInvoker getInstance(
			String name, I_CheckedInvoker delegate) {
			
		ProxyCheckedInvoker newPi = new ProxyCheckedInvoker(name, delegate);
		ProxyCheckedInvoker oldPi = (ProxyCheckedInvoker) preInitInvokers.get(newPi);
		if (oldPi == null) {
			preInitInvokers.add(newPi);
			return newPi;
		}
		return oldPi;
	}
	
	public static I_Iterator getPreInitInvokers() {
		return preInitInvokers.getIterator();
	}
	
	/**
	 * start instance code
	 */
	private String name;
	private I_CheckedInvoker delegate;

	ProxyCheckedInvoker(String name) {
		if (name == null) {
			Exception e = new NullPointerException();
			log.error("Null ProxyInvoker Name!", e);
		}
		this.name = name;
	}
	
	ProxyCheckedInvoker(String name, I_CheckedInvoker p ) {
		this(name);
		delegate = p;
	}
	
	protected String getName() {
		return name;
	}
	
	public synchronized void setDelegate(I_CheckedInvoker p) {
		if (log.isDebugEnabled()) {
			log.debug(super.toString() + " getting invoker " + p + " for ProxyInvoker " + name);
		}
		delegate = p;
	}
	public I_CheckedInvoker getDelegate() {
		return delegate;
	}
	
	public Object invoke(Object valueObject) throws InvocationException {
		if (delegate == null) {
			InvocationException e = new InvocationException(name + " Proxy isn't initalized yet!");
			throw e;
		} else {
			return delegate.invoke(valueObject);
		}
	}
	
	static void clearPreInitInvokers() {
		if (log.isErrorEnabled()) {
			Exception x = new Exception();
			x.fillInStackTrace();
			log.error(" calling clearPreInitInvokers ok for tests only" , x);
		}
		preInitInvokers.clear();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(super.toString());
		sb.append(" [name=");
		sb.append(name);
		sb.append(",delegate=");
		sb.append(delegate);
		sb.append("]");
		return sb.toString();
		
	}
}
