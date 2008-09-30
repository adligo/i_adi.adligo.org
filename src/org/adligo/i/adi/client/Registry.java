package org.adligo.i.adi.client;

import org.adligo.i.util.client.CollectionFactory;
import org.adligo.i.util.client.I_Collection;
import org.adligo.i.util.client.I_Map;
import org.adligo.i.util.client.MapFactory;

import org.adligo.i.log.client.Log;
import org.adligo.i.log.client.LogFactory;


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
	private final Log log = LogFactory.getLog(Registry.class);
	

	/**
	 * <String,I_Invoker> 
	 * CLDC 2.0
	 */
	volatile private I_Map methods = MapFactory.create();
	/*
	 * <String,I_CheckedInvoker>
	 */
	volatile private I_Map checkedMethods = MapFactory.create();
	volatile private boolean servedAllRequests = true;
	volatile private boolean servedAllInvokers = true;
	volatile private boolean servedAllCheckedInvokers = true;
	/**
	 * 
	 */
	volatile private I_Collection missingInvokers = CollectionFactory.create();
	/**
	 * 
	 */
	volatile private I_Collection missingCheckedInvokers = CollectionFactory.create();
	
	protected Registry() { 
		populateInvokers(methods);
		populateCheckedInvokers(checkedMethods);
	}
	/**
	 * override this method
	 * @param p
	 */
	protected void populateInvokers(I_Map p ) {
		
	}
	/**
	 * override this method
	 * @param p
	 */
	protected void populateCheckedInvokers(I_Map p) {
		
	}
	
	/**
	 * this will return the I_Registry instance
	 * which may be setup using jndi or java system
	 * peoperties see Registry Creator
	 * @return
	 */
	public static I_Registry getInstance() {
		return AdiPlatform.getRegistry();
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
			missingCheckedInvokers.add(p);
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
	public I_Collection getMissingInvokers() {
		return this.missingInvokers;
	}
	public I_Collection getMissingCheckedInvokers() {
		return this.missingCheckedInvokers;
	}
	
	protected void changed() {
		AdiPlatform.changed(this);
	}
}
