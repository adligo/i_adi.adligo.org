package org.adligo.i.adi.client;

/**
 * if you want your method to actually throw up a checked
 * exception that the client should deal with
 * subclassed from Exception so as to 
 * keep from catching RuntimeExceptions
 * 
 * subclass this if your implementaion needs more detail
 * 
 * @author scott
 *
 */

public class InvocationException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8086030960203444535L;

	public InvocationException() {}
	
	public InvocationException(String p) {
		super(p);
	}
}
