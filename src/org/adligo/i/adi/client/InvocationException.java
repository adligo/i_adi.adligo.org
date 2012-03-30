package org.adligo.i.adi.client;

/**
 * if you want your method to actually throw up a checked
 * exception that the client should deal with
 * subclassed from Exception so as to 
 * keep from catching RuntimeExceptions
 * 
 * subclass this if your implementaion needs more detail
 * 
 * The general rule of thumb in the java api seems to
 * be to throw a Checked exception (like this class)
 * when some sort of IO issue occurs.  Lots of developers
 * consider checked exceptions a failed experiment that
 * occured in java (as no other languages have them).
 *    I think they were a success, however I think they are 
 * often mis used.  For instance several IO type classes 
 * throw IOExceptions when you close resources, how is a 
 * programmer suppose to catch and handle that, close it again?
 * ie
 * http://docs.oracle.com/javase/1.4.2/docs/api/java/io/OutputStreamWriter.html#close()
 * so think carfully before you decide to use checked exceptions.
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
	
	public InvocationException(String p, Throwable initCause) {
		super(p);
		super.initCause(initCause);
		super.setStackTrace(initCause.getStackTrace());
	}
}
