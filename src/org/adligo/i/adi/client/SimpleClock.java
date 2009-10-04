package org.adligo.i.adi.client;


public class SimpleClock implements I_Clock {

	public long currentTimeMillis() {
		return System.currentTimeMillis();
	}

}
