package org.adligo.i.adi.client;


public class SimpleClock implements I_Invoker {
	protected static final SimpleClock INSTANCE = new SimpleClock();
	
	private SimpleClock() {}
	
	public Object invoke(Object valueObject) {
		return new Long(System.currentTimeMillis());
	}

}
