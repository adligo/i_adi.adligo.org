package org.adligo.i.adi.client;

/**
 * this is a interface to allow testing of time
 * which System.currentTimeMillis will NOT ALLOW
 * 
 * @author scott
 *
 */
public interface I_Clock {
	public long currentTimeMillis();
}
