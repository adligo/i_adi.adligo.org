package org.adligo.i.adi.client;

public class SynchronizedCheckedInvokerProxy implements I_Invoker {
	private I_Invoker delegate = null;
	
	public SynchronizedCheckedInvokerProxy(I_Invoker p) {
		if (p == null) {
			throw new NullPointerException("SynchronizedInvokerProxy needs a non null delegate!");
		}
		delegate = p;
	}
	
	public Object invoke(Object valueObject) {
		synchronized (this) {
			return delegate.invoke(valueObject);
		}
	}

}
