package org.adligo.i.adi;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Collections;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * this is a continer or Registry for 
 * providing wrappers to stateless apis of 
 * subsystems so
 * that your application can dynamicly configure 
 * its structure. 
 * 
 * 
 * 
 * @author scott
 *
 */
public class Registry implements I_Registry {
	public static final String IMPLEMTAION_CLASS = "org.adligo.i.adi.impl";
	
	private static final Log log = LogFactory.getLog(Registry.class);
	private static I_Registry instance = null;
	

	
	private Map <String,I_Invoker> methods = new HashMap();
	private Map <String,I_CheckedInvoker> checkedMethods = new HashMap();
	private boolean servedAllRequests = true;
	private boolean servedAllInvokers = true;
	private boolean servedAllCheckedInvokers = true;
	private Set missingInvokers = Collections.synchronizedSet(new HashSet());
	private Set missingCheckedInvokers = Collections.synchronizedSet(new HashSet());
	
	protected Registry() { 
		populateInvokers(methods);
		populateCheckedInvokers(checkedMethods);
	}
	/**
	 * override this method
	 * @param p
	 */
	protected void populateInvokers(Map <String,I_Invoker> p ) {
		
	}
	/**
	 * override this method
	 * @param p
	 */
	protected void populateCheckedInvokers(Map <String,I_CheckedInvoker> p) {
		
	}
	
	/**
	 * this will return the I_Registry instance
	 * which may be setup using jndi or java system
	 * peoperties see Registry Creator
	 * @return
	 */
	public static I_Registry getInstance() {
		return RegistryCreator.instance;
	}
	
	

	/**
	 * @todo implement this so that it can be looked up through
	 * container configuration, so others can create there own impl
	 * if they need to for some reason
	 * 
	 * @return
	 */
	private static I_Registry obtainFromContext() {
		return null;
	}


	/**
	 * @see I_Registry#getCheckedInvoker(String)
	 */
	public I_CheckedInvoker getCheckedInvoker(String p) {
		I_CheckedInvoker toRet = checkedMethods.get(p);
		if (log.isDebugEnabled()) {
			log.debug("obtained " + toRet + " for request " + p);
		}
		if (toRet == null) {
			servedAllRequests = false;
			servedAllCheckedInvokers = false;
			missingCheckedInvokers.add(p);
			throw new NullPointerException("Unable to loacte CheckedInvoker " + p);
		}
		return toRet;
	}


	/**
	 * @see I_Registry#getInvoker(String)
	 */
	public I_Invoker getInvoker(String p) {
		I_Invoker toRet =  methods.get(p);
		if (log.isDebugEnabled()) {
			log.debug("obtained " + toRet + " for request " + p);
		}
		if (toRet == null) {
			servedAllRequests = false;
			servedAllInvokers = false;
			missingInvokers.add(p);
			throw new NullPointerException("Unable to loacte Invoker " + p);
		}
		return toRet;
	}

	public boolean servedAll() {
		return servedAllRequests;
	}
	public boolean servedAllInvokers() {
		return servedAllInvokers;
	}
	public boolean servedAllCheckedInvokers() {
		return servedAllCheckedInvokers;
	}
	public Set <String> getMissingInvokers() {
		return this.missingInvokers;
	}
	public Set <String> getMissingCheckedInvokers() {
		return this.missingCheckedInvokers;
	}
}
