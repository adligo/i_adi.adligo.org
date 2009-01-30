package org.adligo.i.adi.client;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * a wrapper interface to invoke a of a method of a 
 * stateless threadsafe implementation
 * 
 * @author scott
 *
 */
public interface I_Handler extends RemoteService {
	/**
	 * 
	 * @param a valueObject could be a Integer, String
	 *          I_TemplateParams, List exc
	 * @return
	 */
	public Object invoke(Object valueObject);
}
