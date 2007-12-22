package org.adligo.i.adi;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
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
	public static final String IMPLEMTAION_CLASS = "org.adligo.i.adi.impl";
	
	private static final Log log = LogFactory.getLog(Registry.class);
	private static I_Registry instance = null;
	private static Object doubleFactoryBlock = new Object();
	
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
		I_Registry toRet = instance;
		if (toRet == null) {
			synchronized (doubleFactoryBlock) {
				if (toRet == null) {
					instance = createInstance();
					toRet = instance;
				}
			}
		}
		return toRet;
	}
	private static synchronized I_Registry createInstance() {
		if (log.isDebugEnabled()) {
			log.debug("entering");
		}
		if (instance != null) {
			return instance;
		}
		I_Registry toRet = null;
		if (!init) {
			if (log.isDebugEnabled()) {
				log.debug("attempting init");
			}
			
			Exception e = null;
			try {
				InitialContext context = new InitialContext();
				toRet = (I_Registry) context.lookup(IMPLEMTAION_CLASS);
				
			} catch (Exception x) {
				log.error(x.getMessage(), x);
				e = x;
			}
			if (log.isDebugEnabled()) {
				log.debug("found " + toRet + " from jndi for name " +
						IMPLEMTAION_CLASS);
			}
			if (toRet == null) {
				try {
					String clazzName = System.getProperty(IMPLEMTAION_CLASS);
					toRet = createRegistryFromClassName(toRet, clazzName);
				} catch (Exception x) {
					log.error(x.getMessage(), x);
					e = x;
				}
			}
			if (toRet == null) {
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
		return toRet;
	}
	
	/**
	 * read a properties file when jndi isn't available
	 * @return
	 * @throws Exception
	 */
	private static String getClassNameFromPropertyFile() throws Exception {
		Properties props  = new Properties();
		File file = new File("adi.properties");
		FileInputStream in = new FileInputStream(file);
		in.close();
		return props.getProperty(IMPLEMTAION_CLASS);
	}
	
	/**
	 * Do a class.ForName and create a instance
	 * @param toRet
	 * @param clazzName
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private static I_Registry createRegistryFromClassName(I_Registry toRet,
			String clazzName) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		if (clazzName != null) {
			
			if (log.isDebugEnabled()) {
				log.debug("attempting instaniate " + clazzName);
			}
			Class clazz = Class.forName(clazzName);
			if (log.isDebugEnabled()) {
				log.debug("got clazz " + clazz);
			}
			toRet = (I_Registry) clazz.newInstance();
			if (log.isDebugEnabled()) {
				log.debug("got instance " + toRet);
			}
		}
		return toRet;
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
