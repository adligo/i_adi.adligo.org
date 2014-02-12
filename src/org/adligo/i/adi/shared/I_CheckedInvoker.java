package org.adligo.i.adi.shared;

/**
 * a interface to invoke a of a method of a 
 * stateless threadsafe implementation,
 * which may throw a checked exception
 * 
 * @author scott
 *
 */
public interface I_CheckedInvoker {
	/**
	 * 
	 * @param a valueObject could be a Integer, String
	 *          I_TemplateParams, List exc
	 * @return
	 * @throws InvocationException
	 */
	public Object invoke(Object valueObject) throws InvocationException;
}
