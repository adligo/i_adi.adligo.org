package org.adligo.i.adi;

import java.util.Vector;
import java.util.Hashtable;

import org.adligo.i.log.Log;
import org.adligo.i.log.LogFactory;


/**
 * this is a container or Registry for 
 * providing wrappers to stateless apis of 
 * subsystems so
 * that your application can dynamicly configure 
 * its structure. 
 * 
 * It is intended to be portable between CLDC (phones pdas exc),
 * GWT (ajax), and full blown java apps (servlets and applets).
 * 
 * The CLDC part makes us yank the newer collection (generic) code and go back
 * to Vectors and Hashtables :(
 * 
 * @author scott
 *
 */
public class Registry implements I_Registry {
	public static final String IMPLEMTAION_CLASS = "org.adligo.i.adi.impl";
	/**
	 * obtained with the same logic as the Registry is obtained
	 */
	public static final String LOG_IMPLEMTAION_CLASS = "org.adligo.i.adi.log_impl";
	
	private final Log log = LogFactory.getLog(Registry.class);
	

	/**
	 * <String,I_Invoker> 
	 * CLDC 2.0
	 */
	volatile private Hashtable methods = new Hashtable();
	/*
	 * <String,I_CheckedInvoker>
	 */
	volatile private Hashtable checkedMethods = new Hashtable();
	volatile private boolean servedAllRequests = true;
	volatile private boolean servedAllInvokers = true;
	volatile private boolean servedAllCheckedInvokers = true;
	/**
	 * 
	 */
	volatile private Vector missingInvokers = new Vector();
	/**
	 * 
	 */
	volatile private Vector missingCheckedInvokers = new Vector();
	
	protected Registry() { 
		populateInvokers(methods);
		populateCheckedInvokers(checkedMethods);
	}
	/**
	 * override this method
	 * @param p
	 */
	protected void populateInvokers(Hashtable p ) {
		
	}
	/**
	 * override this method
	 * @param p
	 */
	protected void populateCheckedInvokers(Hashtable p) {
		
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
	 * @see I_Registry#getCheckedInvoker(String)
	 */
	public I_CheckedInvoker getCheckedInvoker(String p) {
		
		I_CheckedInvoker toRet = (I_CheckedInvoker) checkedMethods.get(p);
		if (log.isDebugEnabled()) {
			log.debug("obtained " + toRet + " for request " + p);
		}
		if (toRet == null) {
			servedAllRequests = false;
			servedAllCheckedInvokers = false;
			missingCheckedInvokers.addElement(p);
			throw new NullPointerException("Unable to loacte CheckedInvoker " + p);
		}
		return toRet;
	}


	/**
	 * @see I_Registry#getInvoker(String)
	 */
	public I_Invoker getInvoker(String p) {
		I_Invoker toRet = (I_Invoker) methods.get(p);
		if (log.isDebugEnabled()) {
			log.debug("obtained " + toRet + " for request " + p);
		}
		if (toRet == null) {
			servedAllRequests = false;
			servedAllInvokers = false;
			missingInvokers.addElement(p);
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
	public Vector getMissingInvokers() {
		return this.missingInvokers;
	}
	public Vector getMissingCheckedInvokers() {
		return this.missingCheckedInvokers;
	}
	
}
