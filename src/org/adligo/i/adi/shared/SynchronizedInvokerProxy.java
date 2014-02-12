package org.adligo.i.adi.shared;

public class SynchronizedInvokerProxy implements I_Invoker {
	private I_Invoker delegate = null;
	
	public SynchronizedInvokerProxy(I_Invoker p) {
		if (p == null) {
			throw new NullPointerException("SynchronizedInvokerProxy needs a non null delegate!");
		}
		delegate = p;
	}
	
	public Object invoke(Object valueObject)  {
		synchronized (this) {
			return delegate.invoke(valueObject);
		}
	}

}
