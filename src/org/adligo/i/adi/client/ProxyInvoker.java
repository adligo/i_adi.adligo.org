package org.adligo.i.adi.client;

import org.adligo.i.log.client.Log;
import org.adligo.i.log.client.LogFactory;
import org.adligo.i.util.client.ArrayCollection;
import org.adligo.i.util.client.I_Iterator;

public class ProxyInvoker implements I_Invoker {
	private static final Log log = LogFactory.getLog(ProxyInvoker.class);
	private static final ArrayCollection preInitInvokers = new ArrayCollection();

	
	public static ProxyInvoker getInstance(String name) {
		return getInstance(name, null);
	}
	
	public static synchronized ProxyInvoker getInstance(
			String name, I_Invoker delegate) {
		
		if (log.isDebugEnabled()) {
			log.debug("Entering Named Singleton Factory for " + name);
		}
		ProxyInvoker newPi = new ProxyInvoker(name, delegate);
		ProxyInvoker stdPi = StandardInvokers.get(name);
		if (stdPi != null) {
			newPi = stdPi;
		}
		ProxyInvoker oldPi = (ProxyInvoker) preInitInvokers.get(newPi);
		
		if (log.isDebugEnabled()) {
			log.debug("new is " + newPi + " old is " + oldPi);
		}
		if (oldPi == null) {
			preInitInvokers.add(newPi);
			return newPi;
		}
		return oldPi;
	}
	
	public static I_Iterator getPreInitInvokers() {
		return preInitInvokers.getIterator();
	}
	
	static void clearPreInitInvokers() {
		if (Registry.quite_test_log.isErrorEnabled()) {
			Exception x = new Exception();
			x.fillInStackTrace();
			Registry.quite_test_log.error(" calling clearPreInitInvokers ok for tests only" , x);
		}
		preInitInvokers.clear();
	}
	
	/**
	 * start instance code
	 */
	private String name;
	private I_Invoker delegate;

	ProxyInvoker(String name) {
		if (name == null) {
			Exception e = new NullPointerException();
			log.error("Null ProxyInvoker Name!", e);
		}
		this.name = name;
	}
	
	ProxyInvoker(String name, I_Invoker p) {
		this(name);
		delegate = p;
	}
	
	protected String getName() {
		return name;
	}
	
	public synchronized void setDelegate(I_Invoker p) {
		if (log.isDebugEnabled()) {
			log.debug("getting invoker " + p + " for ProxyInvoker " + name);
		}
		delegate = p;
	}
	public I_Invoker getDelegate() {
		return delegate;
	}
	
	
	public Object invoke(Object valueObject) {
		if (delegate == null) {
			RuntimeException e = new RuntimeException("Proxy isn't initalized yet for " + name + 
					" please add one to your Adi Registry (or did you want a checked invoker ?) !");
			log.error(e.getMessage(), e);
			throw e;
		} else {
			try {
				return delegate.invoke(valueObject);
			} catch (RuntimeException x) {
				log.error(x.getMessage(), x);
				throw x;
			}
		}
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

	/**
	 * note delegate is omitted for the named singleton pattern!
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * note delegate is omitted for the named singleton pattern!
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProxyInvoker other = (ProxyInvoker) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
