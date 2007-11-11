package org.adligo.i.adi;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Collections;

import javax.naming.InitialContext;

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
	private static final Log log = LogFactory.getLog(Registry.class);
	private static I_Registry instance = null;
	public static final String IMPLEMTAION_CLASS = "org.adligo.i.adsi.impl";
	public static final String JNDI_IMPLEMTAION = "org.adligo.i.adsi.instance";
	
	/**
	 * keep track of if we initalized or not
	 * if initialization failed we don't want a stack trace 
	 * for each call to the singleton, one is enough
	 */
	private static boolean init = false;
	
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
	
	public static I_Registry getInstance() {
		if (log.isDebugEnabled()) {
			log.debug("entering");
		}
		if (!init) {
			synchronized (Registry.class) {
				if (!init) {
					if (log.isDebugEnabled()) {
						log.debug("attempting init");
					}
					Exception e = null;
					try {
						InitialContext context = new InitialContext();
						instance = (I_Registry) context.lookup(IMPLEMTAION_CLASS);
					} catch (Exception x) {
						log.error(x.getMessage(), x);
						e = x;
					}
					
					if (instance == null) {
						try {
							String clazzName = System.getProperty(IMPLEMTAION_CLASS);
							if (clazzName != null) {
								
								if (log.isDebugEnabled()) {
									log.debug("attempting instaniate " + clazzName);
								}
								Class clazz = Class.forName(clazzName);
								if (log.isDebugEnabled()) {
									log.debug("got clazz " + clazz);
								}
								instance = (I_Registry) clazz.newInstance();
								if (log.isDebugEnabled()) {
									log.debug("got instance " + instance);
								}
							}
						} catch (Exception x) {
							log.error(x.getMessage(), x);
							e = x;
						}
					}
					if (instance == null) {
						//prevent multiple calls since,
						// the system is basicly down at this point
						RuntimeException ruin = null;
						if (e != null) {
							ruin = new RuntimeException(e);
						} else {
							ruin = new RuntimeException("Could not locate a implementation, try ... \n" +
									" setting this up through jndi or System.setProperty(Registry.IMPLEMTAION_CLASS, \n " +
									" \t\t\"org.adligo.examples.i.adsi.ExampleMethodRegistry\"); ");
						}
						
						throw new RuntimeException(e);
					}
					init = true;
				}
			}
		}
		return instance;
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
