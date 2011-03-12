package org.adligo.i.adi.client;

/**
 * a exception that show that a proxy has been locked
 * and is no longer mutable
 *   If you get this exception somwhere in a JUnit test
 *   you probably should remove the calls to lock 
 *   Proxys into a location where they only locked for
 *   your production deployment.
 *   
 * @author scott
 *
 */
public class ProxyLockedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProxyLockedException(String message) {
		super(message);
	}
}
