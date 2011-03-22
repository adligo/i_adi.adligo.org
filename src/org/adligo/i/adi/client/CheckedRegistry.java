package org.adligo.i.adi.client;

import org.adligo.i.log.client.Log;
import org.adligo.i.log.client.LogFactory;
import org.adligo.i.util.client.I_Iterator;
import org.adligo.i.util.client.I_Map;
import org.adligo.i.util.client.MapFactory;
import org.adligo.i.util.client.ThrowableHelperFactory;

public class CheckedRegistry {
	private static final Log log = LogFactory.getLog(CheckedRegistry.class);
	
	/*
	 * <String,I_CheckedInvoker>
	 */
	private static I_Map checkedMethods = null;
	
	protected CheckedRegistry() {
		
	}
	
	static void init() {
		checkedMethods = MapFactory.create();
		
		I_Iterator it = ProxyCheckedInvoker.getPreInitInvokers();
		while (it.hasNext()) {
			ProxyCheckedInvoker pi = (ProxyCheckedInvoker) it.next();
			checkedMethods.put(pi.getName(), pi);
		}
	}
	
	static void uninit() {
		checkedMethods = null;
	}
	

	/**
	 * dynamic locator method to discover your implementations
	 * at run time
	 * 
	 * @param name 
	 * Each usage of this sort of lookup 
	 * will very, and a naming convention should be established
	 * but generally the goal is to keep things simple
	 * so in the adligo code names will look like
	 * 
	 * getPersons
	 * savePersons
	 * findSocks
	 * openJar 
	 * exc...
	 * 
	 * @return
	 */
	I_CheckedInvoker getCheckedInvoker(String p) {
		I_CheckedInvoker toRet = null;
		if (checkedMethods == null) {
			toRet = ProxyCheckedInvoker.getInstance(p);
		} else {
			toRet = (I_CheckedInvoker) checkedMethods.get(p);
			if (toRet == null) {
				toRet = ProxyCheckedInvoker.getInstance(p);
				checkedMethods.put(p, toRet);
			} 
		}
		// not sure why this log message doesn't work from GWT???
		if (log.isDebugEnabled()) {
			log.debug("Returning " + toRet + " for key " + p);
			if (log.isTraceEnabled()) {
				Exception trace = new Exception("tracing lookup location");
				ThrowableHelperFactory.fillInStackTrace(trace);
				log.trace(trace.getMessage(), trace);
			}
		}
		return toRet;
	}
	
	/**
	 * new api for initalization
	 */
	void addCheckedInvokers(I_Map p) {
		if (checkedMethods == null) {
			init();
		}
		I_Iterator it = p.getKeysIterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			addCheckedInvoker(key, (I_CheckedInvoker) p.get(key));
		}
	}
	
	void addCheckedInvoker(String key, I_CheckedInvoker invoker) {
		if (checkedMethods == null) {
			init();
		}
		ProxyCheckedInvoker pi = (ProxyCheckedInvoker) checkedMethods.get(key);
		if (pi != null) {
			if (pi.getDelegate() == null) {
				pi.setDelegate(invoker);
				if (log.isInfoEnabled()) {
					log.info("addCheckedInvoker " + key + " is now " + checkedMethods.get(key));
				}
			} else {
				if (log.isWarnEnabled()) {
					log.warn("checked invoker with name " + key + " was NOT replaced when calling addCheckedInvoker");
				}
			}
		} else {
			checkedMethods.put(key, new ProxyCheckedInvoker(key, invoker));
			if (log.isInfoEnabled()) {
				log.info("addCheckedInvoker " + key + " is now " + checkedMethods.get(key));
			}
		}
	}
	
	void replaceCheckedInvokerDelegates(I_Map p ) {
		if (log.isInfoEnabled()) {
			log.info("entering replaceCheckedInvokerDelegates...");
		}
		if (checkedMethods == null) {
			init();
		}
		I_Iterator it = p.getKeysIterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			I_CheckedInvoker value = (I_CheckedInvoker) p.get(key); 
			addOrReplaceCheckedInvoker(key, value);
		}
		if (log.isInfoEnabled()) {
			log.info("exiting replaceInvokerDelegates...");
		}
	}

	void addOrReplaceCheckedInvoker(String key, I_CheckedInvoker value) {
		ProxyCheckedInvoker pi = (ProxyCheckedInvoker) checkedMethods.get(key);
		if (pi == null) {
				pi = new ProxyCheckedInvoker(key);
				pi.setDelegate(value);
				checkedMethods.put(key, pi);
		} else {
			pi.setDelegate(value);
		}
		if (log.isInfoEnabled()) {
			log.info("replaceCheckedInvokerDelegates " + key + " is now " + checkedMethods.get(key));
		}
	}
	
	static void clear() {
		if (checkedMethods != null) {
			checkedMethods.clear();
		}
		ProxyCheckedInvoker.clearPreInitInvokers();
	}
	
	boolean lockCheckedInvoker(String name) {
		if (checkedMethods != null) {
			ProxyCheckedInvoker invoker = (ProxyCheckedInvoker) checkedMethods.get(name);
			if (invoker != null) {
				invoker.lock();
				return true;
			}
		}
		return false;
	}
	
	
	void debug() {
		if (checkedMethods != null) {
			I_Iterator it2 = checkedMethods.getKeysIterator();
			while (it2.hasNext()) {
				Object obj = it2.next();
				log.debug(obj);
			}
		} else {
			log.debug(" are null");
		}
		log.debug("\n\n");
	}
}
