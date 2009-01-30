package org.adligo.i.adi.client;

public class SynchronizedInvokerProxy implements I_CheckedInvoker {
	private I_CheckedInvoker delegate = null;
	
	public SynchronizedInvokerProxy(I_CheckedInvoker p) {
		if (p == null) {
			throw new NullPointerException("SynchronizedInvokerProxy needs a non null delegate!");
		}
		delegate = p;
	}
	
	public Object invoke(Object valueObject) throws InvocationException {
		synchronized (this) {
			return delegate.invoke(valueObject);
		}
	}

}
