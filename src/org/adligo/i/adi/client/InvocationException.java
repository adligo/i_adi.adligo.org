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
import java.lang.Throwable;

public class InvocationException extends Exception {

}
