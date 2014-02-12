package org.adligo.i.adi.shared;


public class SimpleClock implements I_Invoker {
	public static final SimpleClock INSTANCE = new SimpleClock();
	
	private SimpleClock() {}
	
	public Object invoke(Object valueObject) {
		long time = System.currentTimeMillis();
		return new Long(time);
	}

}
