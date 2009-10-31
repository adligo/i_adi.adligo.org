package org.adligo.i.adi.client;


public class SimpleClock implements I_Invoker {

	public Object invoke(Object valueObject) {
		return new Long(System.currentTimeMillis());
	}

}
