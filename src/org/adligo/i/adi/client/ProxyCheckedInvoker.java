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
			InvocationException e = new InvocationException("Proxy isn't initalized yet for " + name + 
					" please add one to your Adi Registry (or did you want a non-checked invoker ?) !");
			throw e;
		} else {
			return delegate.invoke(valueObject);
		}
	}
	
	static void clearPreInitInvokers() {
		if (Registry.quite_test_log.isErrorEnabled()) {
			Exception x = new Exception();
			x.fillInStackTrace();
			Registry.quite_test_log.error(" calling clearPreInitInvokers ok for tests only" , x);
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

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProxyCheckedInvoker other = (ProxyCheckedInvoker) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}


}
